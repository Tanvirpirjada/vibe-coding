package com.vibecoding.aiservice.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;

    private final Logger logger= LoggerFactory.getLogger(RagService.class);

    @Autowired
    public RagService(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDocuments() {
        logger.info("Starting document loading from classpath:docs/*.txt");

        try {
            PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

            Resource[] resources =
                    resolver.getResources("classpath:docs/*.txt");

            logger.info("Found {} .txt files in docs folder", resources.length);

            List<Document> documents = new ArrayList<>();

            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                logger.info("Processing file: {}", fileName);

                String content;
                try (InputStream is = resource.getInputStream()) {
                    content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    logger.info("Read {} characters from {}", content.length(), fileName);
                }

                Document doc = new Document(content);
                doc.getMetadata().put("source", fileName);
                documents.add(doc);
            }

            if (documents.isEmpty()) {
                logger.warn("No .txt files found in classpath:docs/ — RAG will have no context");
                return;
            }

            // 🔥 IMPORTANT PART: split documents before embedding
            TokenTextSplitter splitter =
                    new TokenTextSplitter();


            List<Document> splitDocuments = splitter.split(documents);

            vectorStore.add(splitDocuments);

            logger.info(
                    "Successfully added {} chunks (from {} files) to Redis vector store",
                    splitDocuments.size(),
                    documents.size()
            );

        } catch (Exception e) {
            logger.error("Failed to load documents", e);
        }
    }

    public String getRelevantContext(String query, int topK) {
        SearchRequest request=new SearchRequest();
        request.builder().query(query).topK(topK);
        List<Document> results = vectorStore.similaritySearch(request);
        return results.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n---\n\n"));
    }

}

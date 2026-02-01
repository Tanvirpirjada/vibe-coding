# Vibe Coding – AI-Powered Coding & Knowledge Assistant

A smart backend service that answers natural language questions with accurate, context-aware responses using **Groq LLM**, **Spring AI**, **Redis** (caching + vector store), and **local Ollama embeddings** for RAG.

Built as a showcase project to demonstrate:

- Real-time LLM integration
- Semantic caching & performance optimization
- Retrieval-Augmented Generation (RAG) with your own documents
- Secure configuration & production-ready patterns

---

## Features (Current – Fully Working)

- Natural language prompt → AI-generated code, explanations, recipes, fixes
- **Exact prompt caching** in Redis → repeat questions return instantly (no extra Groq calls)
- Automatic cache expiration (TTL)
- **RAG (Retrieval-Augmented Generation)**:
  - Your own documents/notes/recipes/code loaded from `src/main/resources/docs/*.txt`
  - Embedded locally using **Ollama** (`nomic-embed-text`) — 100% free & private
  - Stored as vectors in **Redis**
  - On cache miss: semantically searches your documents → adds relevant context
  - Groq generates better, more accurate answers grounded in your knowledge
- Secure config (API keys never committed)

---

## Tech Stack

- Java 17 / 21
- Spring Boot 3.x
- Spring AI (Groq chat + Ollama embeddings)
- Groq LLM (`llama-3.3-70b-versatile`)
- Ollama (local embedding model: `nomic-embed-text`)
- Redis (caching + vector store for RAG)
- Docker (Redis container)
- Maven

---

## Quick Start

### Prerequisites

- Java 17 or 21
- Maven
- Docker (for Redis)
- Free Groq API key → [Get one here](https://console.groq.com/keys)
- Ollama installed locally → [Download here](https://ollama.com/download)

After install, run in a separate terminal:

```bash
ollama pull nomic-embed-text
ollama serve

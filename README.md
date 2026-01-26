# Vibe Coding – AI-Powered Coding Assistant

A backend service that lets you generate code, explanations, bug fixes, and more using natural language prompts — powered by **Groq LLM**, **Spring AI**, and **Redis caching**.

Built as a showcase project to demonstrate modern Java backend skills: AI integration, caching, secure configuration, and production-ready patterns.

## Features (Current)

- Natural language prompt → AI-generated code/response
- Responses cached in **Redis** → repeated prompts return instantly (no extra Groq calls)
- Automatic cache expiration (TTL)
- Secure config handling (API key never committed to Git)

## Tech Stack

- Java 21
- Spring Boot 3.x
- Spring AI (OpenAI-compatible client for Groq)
- Groq LLM (fast inference with models like `llama-3.3-70b-versatile`)
- Redis (caching)
- Docker (for Redis)
- Maven

## Quick Start

### Prerequisites

- Java 21 (or 17)
- Maven
- Docker (for Redis)
- Free Groq API key → [Get one here](https://console.groq.com/keys)

### Run Locally

1. **Start Redis**

   ```bash
   docker run -d --name redis-vibe -p 6379:6379 redis:7

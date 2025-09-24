# Apache Camel Template: Queue → REST API with Retry & DLQ

This template consumes messages from **ActiveMQ**, calls a **REST API**, and retries failed messages with exponential backoff. After 3 failed attempts, the message is moved to a **Dead Letter Queue**.

## 🚀 Quick Start
```bash
docker-compose up -d
mvn spring-boot:run

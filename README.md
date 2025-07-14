# ISO20022 Orchestrator & SWIFT→MX Translator

A microservices-based system for processing SWIFT MT messages and converting them to ISO 20022 MX format.

## Architecture

```
┌─────────────┐   ┌─────────────────┐   ┌──────────────────┐   ┌──────────────────┐   ┌──────────────────┐
│             │   │                 │   │                  │   │                  │   │                  │
│  Ingest     │   │  Validation    │   │  Transform      │   │  Router          │   │  Settlement      │
│  Service    │──▶│  Service       │──▶│  Service        │──▶│  Service         │──▶│  API (Mock)      │
│  (8080)     │   │  (8081)        │   │  (8082)         │   │  (8083)          │   │                  │
└─────────────┘   └─────────────────┘   └──────────────────┘   └──────────────────┘   └──────────────────┘
       ▲                    │                     │                     │
       │                    ▼                     ▼                     │
       │             ┌─────────────┐      ┌─────────────┐              │
       │             │   Kafka     │      │   CouchDB   │              │
       └─────────────┤  (mt.raw,   │      │  (Audit Log)│◀─────────────┘
                     │   mt.valid, │      │             │
                     │   mt.invalid)│      └─────────────┘
                     └─────────────┘
```

## Prerequisites

- Docker and Docker Compose
- Java 17
- Node.js 16+ (for UI development)
- Maven 3.8+

## Quick Start

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd swift-mx-orchestrator
   ```

2. Start all services:
   ```bash
   docker-compose up -d
   ```

3. Access the services:
   - UI: http://localhost:3000
   - Ingest Service: http://localhost:8080
   - CouchDB: http://localhost:5984/_utils/ (admin/password)
   - ActiveMQ Console: http://localhost:8161/admin/ (admin/admin)

## Project Structure

```
swift-mx-orchestrator/
├── docker-compose.yml         # Docker Compose configuration
├── ingest-service/            # File upload and initial processing
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── validation-service/        # MT message validation
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── transform-service/         # MT to MX transformation
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── router-service/            # Message routing
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── ui/                        # React frontend
│   ├── public/
│   ├── src/
│   └── package.json
└── README.md
```

## Development

### Building the Project

```bash
# Build all services
mvn clean install

# Build and run a specific service
cd ingest-service
mvn spring-boot:run
```

### Testing

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify -Pintegration-test
```

### API Documentation

API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Deployment

### Production Build

```bash
# Build Docker images
docker-compose build

# Start services in production mode
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

### Kubernetes (Optional)

For Kubernetes deployment, see the `k8s/` directory for sample manifests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

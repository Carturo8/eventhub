# EventHub

EventHub is a RESTful API for managing events and venues, built with Spring Boot and following Clean Architecture principles.

## Features

- **Event Management**: Create, read, update, and delete events with details like category, dates, and venue
- **Venue Management**: Manage event venues with capacity and location information
- **User Authentication**: JWT-based authentication with user registration and login
- **Search & Filter**: Advanced search and filtering capabilities for events and venues
- **Pagination**: Efficient pagination support for large datasets
- **Security**: Role-based access control with Spring Security
- **Observability**: Integrated monitoring with Prometheus and OpenTelemetry

## Tech Stack

- **Framework**: Spring Boot 3.5.7
- **Language**: Java 21
- **Database**: PostgreSQL
- **Migration**: Flyway
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI/Swagger
- **Containerization**: Docker & Docker Compose
- **Monitoring**: Micrometer, Prometheus, OpenTelemetry
- **Testing**: JUnit 5, Testcontainers

## Architecture

The project follows **Hexagonal Architecture (Ports and Adapters)** with clear separation of concerns:

- **Domain Layer**: Core business logic and entities
- **Application Layer**: Use cases and queries
- **Infrastructure Layer**: Adapters for web, database, and external services

## Prerequisites

- Java 21 or higher
- Maven 3.9+
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL 13+ (if running locally without Docker)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Carturo8/eventhub.git
cd eventhub
```

### Configuration

Create an `application-local.yml` file based on the example:

```bash
cp src/main/resources/application-local-example.yml src/main/resources/application-local.yml
```

Set the required environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/eventhub
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_secret_key
```

### Running with Docker Compose

The easiest way to run the application:

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`

### Running Locally

1. Start PostgreSQL database
2. Build the project:

```bash
./mvnw clean package
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

## API Documentation

Once the application is running, access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

### Main Endpoints

#### Authentication

- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and get JWT token

#### Events

- `GET /api/events` - List all events (with pagination and filters)
- `GET /api/events/{id}` - Get event by ID
- `POST /api/events` - Create a new event (requires authentication)
- `PUT /api/events/{id}` - Update an event (requires authentication)
- `DELETE /api/events/{id}` - Delete an event (requires authentication)

#### Venues

- `GET /api/venues` - List all venues (with pagination and filters)
- `GET /api/venues/{id}` - Get venue by ID
- `POST /api/venues` - Create a new venue (requires authentication)
- `PUT /api/venues/{id}` - Update a venue (requires authentication)
- `DELETE /api/venues/{id}` - Delete a venue (requires authentication)

## Development

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package -DskipTests
```

## Monitoring

The application exposes actuator endpoints for monitoring:

- Health: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus: `http://localhost:8080/actuator/prometheus`

## Project Structure

```
src/main/java/com/carturo/eventhub/
├── application/          # Application layer (use cases, queries)
│   ├── query/           # Query handlers
│   ├── usecase/         # Command handlers
│   └── exception/       # Application exceptions
├── domain/              # Domain layer
│   ├── model/          # Domain entities
│   └── ports/          # Interfaces (in/out)
└── infrastructure/      # Infrastructure layer
    ├── adapters/       # Web, database adapters
    ├── config/         # Configuration
    └── security/       # Security configuration
```

## License

This project is available for educational and personal use.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

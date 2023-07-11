# CoopVote

CoopVote is a mobile solution for managing and associate in voting sessions within cooperative. It provides a REST API that allows users to create agendas, open voting sessions, receive member votes, count votes using Kafka, and retrieve the results of the vote. This application is built with Spring Boot, Kafka, MongoDB, and Docker.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features

- Create, retrieve agendas.
- Open voting sessions on agendas with customizable duration.
- Receive member votes for agendas (limited to 'Yes' or 'No').
- Count votes and provide the result using Kafka.
- Persist agendas, members, and votes in MongoDB.
- Follows SOLID principles for clean and maintainable code.

## Requirements

- Java 11 or higher
- Spring Boot
- Kafka
- MongoDB
- Docker

## Installation

1. Clone the repository: `git clone https://github.com/rsurfings/coopvote.git`
2. Navigate to the project directory: `cd coopvote`
3. Build the project: `mvn clean install`

## Usage

1. Start the required infrastructure components (Kafka and MongoDB) using Docker: `docker-compose up -d`
2. Run the application: `mvn spring-boot:run`
3. The application will start on `http://localhost:8080`.

## API Endpoints

The following REST API endpoints are available:

## Configuration

The application can be configured using the following properties in the `application.yml` file:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/coopvote
  kafka:
    bootstrap-servers: localhost:9092
    # Add any additional Kafka configuration properties as needed to Modify the properties in the application.yml file to match your environment and configuration requirements.
```

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, please submit a pull request or open an issue in the GitHub repository.

## License

This project is licensed under the [MIT License](LICENSE).

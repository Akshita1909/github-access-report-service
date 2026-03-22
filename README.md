# github-access-report-service
Spring Boot service to generate user-repository access reports using GitHub API
# GitHub Access Report Service

## Overview

This project is a Spring Boot application that integrates with the GitHub REST API to generate a report showing which users have access to which repositories within a given organization.

The service retrieves organization data from GitHub, processes it, and exposes an API endpoint that returns the access report in JSON format.

---

## Features

* Authentication with GitHub using a Personal Access Token
* Retrieval of repositories for a given organization
* Retrieval of organization members
* Aggregation of data to map users to repositories
* REST API endpoint to fetch the access report
* Reactive and scalable implementation using Spring WebFlux

---

## Tech Stack

* Java
* Spring Boot
* Spring WebFlux
* GitHub REST API
* Maven

---

## Authentication Configuration

The application uses a GitHub Personal Access Token to authenticate API requests and avoid rate limits.

Steps:

1. Generate a token from GitHub: https://github.com/settings/tokens
2. Add the token to the following file:

src/main/resources/application.properties

```properties
github.token=YOUR_GITHUB_TOKEN
server.port=8081
```

---

## How to Run the Project

### Prerequisites

* Java 17 or higher
* Maven

### Steps

```bash
git clone <your-repository-link>
cd github-access-service
mvn clean install
mvn spring-boot:run
```

---

## API Endpoint

### Get Access Report

GET /org/{org}/access-report

Example:

http://localhost:8081/org/github/access-report

---

## Sample Response

```json
{
  "user1": ["repo1", "repo2"],
  "user2": ["repo3"]
}
```

---

## Design and Implementation

### Architecture

The project follows a layered architecture:

controller → service → client → model

* Controller: Handles incoming HTTP requests
* Service: Contains business logic and aggregation
* Client: Handles communication with GitHub API
* Model: Represents data structures

---

### Data Flow

1. Fetch repositories of the organization
2. Fetch organization members
3. Map users to repositories
4. Return the aggregated result

---

## Scalability Considerations

* Uses Spring WebFlux for non-blocking, asynchronous processing
* Suitable for organizations with large numbers of repositories and users
* Avoids unnecessary sequential API calls

---

## Assumptions and Design Decisions

The GitHub API endpoint for repository collaborators requires elevated permissions and is often restricted.

To ensure the application works reliably, organization members are used as a proxy for repository access.

This design demonstrates the required aggregation logic and can be extended to use collaborator-level data when proper permissions are available.

---

## Error Handling

The application includes basic error handling for:

* API failures
* Rate limiting
* Unauthorized access


## Future Improvements

* Use collaborator API with proper authentication
* Include role-based access (admin, write, read)
* Add pagination support
* Integrate API documentation using Swagger
* Deploy as a cloud service

---

## Conclusion

This project demonstrates integration with external APIs, data aggregation, and scalable backend design using reactive programming.


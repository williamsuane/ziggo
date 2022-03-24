![build](https://img.shields.io/github/workflow/status/williamsuane/ziggo/Java%20CI%20with%20Maven)

# Ziggo Test

## Pre-requisites

1. Docker

## Image Setup

1. In the terminal go to the folder `ziggo-assignment` and run the command `./mvnw clean package jib:dockerBuild`

## Running

**With docker**

1. In the terminal go to the same root level as `ziggo-assignment` and run the command `docker-compose up`

**Locally**

1. In the terminal go to the same root level as `ziggo-assignment` and run the
   command `./mvnw clean compile spring-boot:run`

## Testing

### Option 1

1. Access [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and execute the
   requests.
    1. To find valid emails, check [https://reqres.in/api/users](https://reqres.in/api/users)
2. Try the requests

### Option 2

1. Import the postman collection from `ziggo-assignment/postman` and execute the requests

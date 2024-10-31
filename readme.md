# Set Up the Project Using Spring Initializr

Configured the project via `https://start.spring.io` and downloaded the zip file.

## Dependencies

- Spring Web (for REST API)
- Spring Data JPA (for database interaction)
- H2 Database (for testing; you may replace this with MySQL, PostgreSQL, etc., if required).

## Run the App

- Open the terminal and navigate to the project directory of the zip file downloaded from the link above.
- Run `mvn spring-boot:run` to start the application.

## API Tests

Using tools like curl or Postman to test the endpoints:

1. POST `/api/urls` - Create a new short URL. Parameters:
    - `originalUrl` (required): the URL to be shortened
    - `customId` (optional): custom short URL ID
    - `ttl` (optional): time-to-live in seconds

```curl -X POST "http://localhost:8080/api/urls?originalUrl=https://abat.com&ttl=3600"```

2. GET `/api/urls/{id}` - Redirects to the original URL if it exists and has not expired.

```curl -X GET "http://localhost:8080/api/urls/{id}"```

3. DELETE `/api/urls/{id}` - Deletes a short URL by ID.

```curl -X DELETE "http://localhost:8080/api/urls/{id}"```

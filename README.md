# Flicker Backend

REST API for **Flicker**, a small movie/series streaming platform. Built with Spring Boot and MySQL, it powers both the public site (browse, search, watch, comment) and the admin dashboard (manage titles and accounts). Posters and video files live on Cloudinary; this API stores only their URLs.

## Tech Stack

- Java + Spring Boot (Spring Web, Spring Data JPA)
- MySQL 8 with Hibernate (`ddl-auto: update`)
- JWT-based authentication (Bearer tokens)
- Lombok + SLF4J logging
- Maven

## Project Structure

```
com.api.movie
├── controller   # REST controllers: UserController, MovieController, CommentController
├── service      # Business logic: UserService, MovieService, CommentService
├── entity       # JPA entities: User, Movie, Comment
├── dto          # Request DTOs, e.g. LoginRequestDto
└── repository   # Spring Data JPA repositories
```

## Getting Started

### Prerequisites

- JDK 17+ (adjust to the version in your `pom.xml`)
- Maven 3.8+
- MySQL 8 running locally on port 3306

### Configuration

Edit `src/main/resources/application.yml`. **Do not commit real credentials.** Use environment variables instead:

```yaml
server:
  port: 8080

spring:
  application:
    name: movie-streaming-site

  datasource:
    url: jdbc:mysql://localhost:3306/moviesdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

logging:
  level:
    org.springframework.web: INFO
```

Then export your secrets before running:

```bash
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

The `moviesdb` database and its tables are created automatically on first start.

### Run

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

## API Reference

Base path: `/api`. Authenticated requests send `Authorization: Bearer <token>`.

### Users — `/api/users`

| Method | Path | Description |
|--------|------|-------------|
| POST | `/signup` | Register a user. Body: `{ name, email, password, role }` |
| POST | `/login` | Log in. Body: `{ email, password }`. Returns a JWT as a plain string |
| GET | `/` | List all users |
| GET | `/{id}` | Get a user by id |
| PUT | `/{id}` | Update a user |
| DELETE | `/{id}` | Delete a user |

### Movies — `/api/movies`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/` | List all movies |
| GET | `/{id}` | Get a movie by id |
| GET | `/search?name=` | Search movies by name |
| POST | `/` | Create a movie |
| PUT | `/{id}` | Update a movie |
| DELETE | `/{id}` | Delete a movie |

Movie fields used by the frontend: `id`, `name`, `year`, `country`, `genre`, `type`, `image`, `url`.

### Comments — `/api/comments`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/movie/{movieId}` | Comments for a movie |
| GET | `/user/{userId}` | Comments by a user |
| POST | `/` | Add a comment. Body: `{ userId, movieId, comment, date }` |
| PUT | `/{id}` | Update a comment |
| DELETE | `/{id}` | Delete a comment |

### Example

```bash
# Log in
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"secret"}'

# Fetch movies with the returned token
curl http://localhost:8080/api/movies \
  -H "Authorization: Bearer <token>"
```

## Frontend

This API is consumed by two static single-page apps (public site and admin dashboard). Set `API_BASE` at the top of each JS file to this server's address, and enable CORS for the origin that serves them.

## Security Notes / TODO

- Move all secrets (DB password, JWT signing key) out of source control into environment variables.
- Enforce role checks server-side: the class-level `@PreAuthorize("hasRole('ADMIN')")` on `UserController` is currently commented out, so admin-only operations (list/delete users, create/update/delete movies) should be restricted in the security config.
- Restrict `DELETE`/`PUT` on comments and `PUT` on users to the resource owner.
- Hash passwords (e.g. BCrypt) and avoid returning password fields in `User` responses; consider using response DTOs.
- Add a "get current user" endpoint (e.g. `/api/users/me`) so clients don't need to fetch the full user list.
- Add pagination for `GET /api/movies` and `GET /api/comments/movie/{id}` as data grows.
- Turn off `show-sql` and `DEBUG` logging in production.

## License

Add your license of choice here.

# JWT Authentication with Spring Boot

This repository demonstrates a secure JWT authentication implementation using Spring Boot and Spring Security.

## Features

- User registration and login
- JWT-based authentication
- Protected endpoints
- Role-based authorization
- Exception handling

## How to Use

### Setup
1. Clone the repository
2. Configure your database settings in `application.yaml`
3. Run the application: `./mvnw spring-boot:run`

### API Endpoints

#### 1. User Registration
```
POST http://localhost:8001/api/user/register
```
Request Body:
```json
{
    "email": "email@email.com",
    "password": "Password@2025",
    "fullName": "Your Name"
}
```

#### 2. User Login
```
POST http://localhost:8001/api/user/login
```
Request Body:
```json
{
    "email": "email@email.com",
    "password": "Password@2025"
}
```
Response:
```json
{
    "result": "eyJhbGciOiJIUzUxMiJ9...", // JWT token
    "message": "Success",
    "status": 200
}
```

#### 3. Accessing Protected Endpoints
```
GET http://localhost:8001/api/home/hello
```
Headers:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```
Response:
```
Hello World
```

## Security Implementation

- Uses Spring Security with JWT for stateless authentication
- Token expiration after 1 hour
- Password encryption with BCrypt
- Input validation
- Protected routes with proper authorization

## Technologies Used

- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- JPA/Hibernate
- RESTful API design principles

## Next Steps for Enhancement

- Refresh token functionality
- Role-based access control
- Token blacklisting
- Advanced error handling
- API documentation with Swagger

Feel free to contribute or report issues!

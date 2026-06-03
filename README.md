# TodoList Backend API

A robust and secure RESTful API for managing tasks and users, built with Spring Boot and MongoDB. This application provides JWT-based authentication, task management with status tracking, and user-specific data isolation.

## Features

- **User Authentication**: Secure JWT-based authentication system
- **Task Management**: Create, read, update, and delete tasks
- **Status Tracking**: Track task status (PENDING, IN_PROGRESS, COMPLETED)
- **Deadline Management**: Set and filter tasks by deadline
- **User Isolation**: Each user can only access their own tasks
- **MongoDB Integration**: Reactive MongoDB for scalable data storage
- **Spring Security**: Comprehensive security configuration
- **RESTful API**: Clean and intuitive API endpoints

## Tech Stack

- **Java 17**
- **Spring Boot 3.5.14**
- **Spring Data MongoDB (Reactive)**
- **Spring Security**
- **JWT (JSON Web Tokens)** - jjwt 0.12.5
- **Lombok** - For reducing boilerplate code
- **Maven** - Build and dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB Atlas or local MongoDB instance
- Git

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/TMB2003/Todo-Backend.git
   cd Todo-Backend
   ```

2. **Configure environment variables**
   
   Create a `.env` file in the root directory with your configuration:
   ```bash
   cp .env.example .env
   ```
   
   Edit `.env` and add your actual values:
   ```env
   MONGODB_URI=mongodb+srv://your-username:your-password@cluster.mongodb.net/database?appName=Cluster0
   JWT_SECRET=your-secret-key-here
   ```

3. **Build the project**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or run the JAR file:
   ```bash
   java -jar target/TodoList-0.0.1-SNAPSHOT.jar
   ```

The application will start on `http://localhost:3000`

## API Endpoints

### Authentication

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "string",
  "password": "string"
}
```

### Task Management

All task endpoints require JWT authentication in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Create Task
```http
POST /task
Content-Type: application/json

{
  "name": "Task name",
  "description": "Task description",
  "deadLine": "2024-12-31",
  "status": "PENDING"
}
```

#### Get All Tasks
```http
GET /task
```

Optional query parameters:
- `status`: Filter by status (PENDING, IN_PROGRESS, COMPLETED)
- `deadline`: Filter by deadline (YYYY-MM-DD format)

Example:
```http
GET /task?status=PENDING&deadline=2024-12-31
```

#### Update Task
```http
PUT /task/{id}
Content-Type: application/json

{
  "name": "Updated task name",
  "description": "Updated description",
  "deadLine": "2024-12-31",
  "status": "IN_PROGRESS"
}
```

#### Change Task Status
```http
PATCH /task/status/{id}
```

#### Delete Task
```http
DELETE /task/{id}
```

## Project Structure

```
Todo-Backend/
├── src/
│   ├── main/
│   │   ├── java/com/learning/TodoList/
│   │   │   ├── config/          # Security configuration
│   │   │   ├── controllers/     # REST controllers
│   │   │   ├── entities/        # Data models
│   │   │   ├── enums/           # Enumerations
│   │   │   ├── filters/         # JWT filter
│   │   │   ├── repositories/    # MongoDB repositories
│   │   │   ├── services/        # Business logic
│   │   │   └── utils/           # Utility classes
│   │   └── resources/
│   │       └── application.yml  # Application configuration
│   └── test/                    # Test cases
├── .env.example                 # Environment variables template
├── .gitignore                   # Git ignore rules
├── pom.xml                      # Maven dependencies
└── README.md                    # This file
```

## Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `MONGODB_URI` | MongoDB connection string | Yes |
| `JWT_SECRET` | Secret key for JWT token generation | Yes |

## Security Features

- **Password Encryption**: Passwords are securely encrypted before storage
- **JWT Authentication**: Stateless authentication using JWT tokens
- **User Isolation**: Users can only access their own data
- **CORS Configuration**: Configurable Cross-Origin Resource Sharing
- **Input Validation**: Request validation for all endpoints

## Development

### Running Tests
```bash
./mvnw test
```

### Code Style
This project uses Lombok to reduce boilerplate code. Ensure you have the Lombok plugin installed in your IDE.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Author

**Taha**

## Acknowledgments

- Spring Boot team for the excellent framework
- MongoDB for the robust database solution
- The open-source community

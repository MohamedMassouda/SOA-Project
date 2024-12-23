# E-Commerce Website

This repository contains the projects for an e-commerce website, including a backend developed with Spring and a frontend designed to interact with the backend. Below is an overview of each component and instructions for setting up and running the projects.

## Project Overview

### 1. Backend
- **Framework**: Spring Boot
- **Database**: MySQL
- **Description**: The backend provides RESTful APIs for managing products, orders, users, and other e-commerce functionalities.
- **Key Features**:
  - User authentication and authorization
  - Product catalog management
  - Order placement and tracking
  - Integration with MySQL database

### 2. Frontend
- **Framework**: React
- **Description**: The frontend provides a user-friendly interface for customers to browse products, manage their cart, and place orders. It communicates with the backend via API endpoints.
- **Key Features**:
  - Responsive design for various devices
  - Seamless integration with backend APIs
  - User authentication and session management

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Version 17 or higher
2. **Node.js and npm**: For frontend 
3. **MySQL Database**: Ensure MySQL is installed and running
4. **Git**: To clone the repository

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/MohamedMassouda/SOA-Project
   cd SOA-Project/Ecommerce
   ```

2. Configure the database:
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce?createDatabaseIfNotExist=true
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. Run the application (or run it using intellij):
   ```bash
   ./mvnw spring-boot:run
   ```

4. The backend should now be running on `http://localhost:8080`.

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the frontend:
   ```bash
   npm run dev
   ```

4. The frontend should now be accessible on `http://localhost:5173`.

## Usage
1. Ensure MySQL is running and the backend is operational.
2. Start the frontend and access the application via your browser.
3. Use the interface to browse products, add items to the cart, and place orders.

## Technologies Used
### Backend
- Spring Boot
- MySQL
- Maven

### Frontend
- React
- Axios (or another library for API calls)


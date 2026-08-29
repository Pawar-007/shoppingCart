# 🛒 ShopCart Backend

A secure and scalable **RESTful E-Commerce Backend** developed using **Java and Spring Boot**. ShopCart provides APIs for user authentication, product management, shopping cart, addresses, order processing, inventory management and admin operations.

The backend is designed using a **layered architecture** and follows REST API principles. Authentication and authorization are implemented using **JWT and Spring Security**.

---

## 🚀 Features

### 👤 Customer Features

* User registration and login
* JWT-based authentication
* Role-based authorization
* View and update user profile
* Browse products
* Search products
* Filter products by category, brand and price range
* View product details
* Add products to cart
* Update cart item quantity
* Remove products from cart
* Clear cart
* Manage multiple delivery addresses
* Place orders for selected cart items
* Check product stock before ordering
* View order details
* View order history
* View active orders
* Cancel orders

### 👨‍💼 Admin Features

* Admin authentication and authorization
* Manage products
* Manage product images
* Manage categories
* Manage brands
* View all users
* View all orders
* Update order status
* View total users
* View total orders
* View total products

---

## 🛠️ Tech Stack

### Backend

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JWT**
* **Spring Data JPA**
* **Hibernate**
* **REST APIs**
* **Maven**

### Database

* **MySQL**
* **Aiven MySQL** for cloud database hosting

### Tools

* **Postman** – API testing
* **Git**
* **GitHub**
* **Docker**

---

## 🏗️ Project Architecture

The application follows a layered architecture:

```text
                    Client
                      │
                      ▼
              REST Controllers
                      │
                      ▼
                Service Layer
                      │
                      ▼
              Repository Layer
                      │
                      ▼
               Spring Data JPA
                      │
                      ▼
                  MySQL
```

### Layers

**Controller Layer**

Handles HTTP requests and returns HTTP responses.

**Service Layer**

Contains the application's business logic such as cart processing, stock validation and order creation.

**Repository Layer**

Handles database operations using Spring Data JPA.

**Entity Layer**

Contains JPA entities mapped to database tables.

**DTO Layer**

Used to transfer data between the client and backend while keeping API models separate from database entities.

---

## 🔐 Authentication & Authorization

ShopCart uses **JWT-based authentication with Spring Security**.

### Authentication Flow

```text
User Login
    │
    ▼
Spring Security
    │
    ▼
Validate Credentials
    │
    ▼
Generate JWT
    │
    ▼
Send Token to Client
    │
    ▼
Client sends JWT with requests
    │
    ▼
JWT Validation
    │
    ▼
Access Protected API
```

The client sends the JWT token using the Authorization header:

```http
Authorization: Bearer <JWT_TOKEN>
```

The JWT contains information such as:

* User ID
* Email
* Role

The backend extracts the authenticated user's ID from the token instead of trusting a user ID supplied by the frontend.

---

## 👥 Roles

ShopCart has two roles:

```text
CUSTOMER
ADMIN
```

### CUSTOMER

Customers can:

* Browse products
* Manage cart
* Manage addresses
* Place orders
* View orders
* Cancel eligible orders

### ADMIN

Admins can:

* Manage products
* Manage categories
* Manage brands
* View users
* View orders
* Update order status
* View application statistics

---

## 🗃️ Main Entities

The backend contains the following major entities:

```text
User
 │
 ├── Cart
 │     └── CartItem
 │
 ├── Address
 │
 └── Order
       └── OrderItem

Product
 │
 ├── Category
 │
 ├── Brand
 │
 └── ProductImage
```

### Main Relationships

```text
User
 ├── 1 : 1 → Cart
 ├── 1 : N → Address
 └── 1 : N → Order

Cart
 └── 1 : N → CartItem

Product
 ├── N : 1 → Category
 ├── N : 1 → Brand
 └── 1 : N → ProductImage

Order
 ├── N : 1 → User
 ├── N : 1 → Address
 └── 1 : N → OrderItem
```

---

## 🛍️ Product Management

Each product contains information such as:

* Product ID
* Product name
* Description
* Price
* Stock quantity
* Category
* Brand
* Product images
* Created date

Product prices are handled using `BigDecimal` to maintain accurate monetary calculations.

---

## 🛒 Cart Flow

When a customer adds a product to the cart:

```text
Customer
   │
   ▼
Add Product
   │
   ▼
Validate Product
   │
   ▼
Check Existing CartItem
   │
   ├── Exists → Update Quantity
   │
   └── Not Exists → Create CartItem
```

The backend calculates the cart total based on:

```text
Product Price × Quantity
```

---

## 📦 Place Order Flow

The order processing flow is:

```text
Authenticated User
        │
        ▼
Extract User ID from JWT
        │
        ▼
Get User Cart
        │
        ▼
Validate Selected Cart Items
        │
        ▼
Check Product Stock
        │
        ▼
Validate Delivery Address
        │
        ▼
Create Order
        │
        ▼
Create Order Items
        │
        ▼
Calculate Total Amount
        │
        ▼
Update Product Stock
        │
        ▼
Remove Ordered Items from Cart
```

This ensures that an order is not created when the requested product quantity exceeds available stock.

---

## 📋 Order Status

Orders can have the following statuses:

```text
PENDING
PROCESSING
SHIPPED
DELIVERED
CANCELLED
```

The admin can update the order status during the order lifecycle.

---

## 🏠 Address Management

Customers can save multiple delivery addresses.

An address contains:

* Full name
* Phone number
* Address line 1
* Address line 2
* City
* State
* Country
* Pincode
* Address type
* Default address status

Before using an address for an order, the backend verifies that the address belongs to the authenticated user.

---

# 🔗 REST API Endpoints

Base URL:

```text
/api
```

---

## 🔐 Authentication APIs

### Register

```http
POST /auth/register
```

### Login

```http
POST /auth/login
```

---

## 👤 User APIs

### Get Profile

```http
GET /users/profile
```

Requires authentication.

---

## 🛍️ Product APIs

Typical product operations include:

```http
GET    /products
GET    /products/{productId}
POST   /products
PUT    /products/{productId}
DELETE /products/{productId}
```

Product search and filtering APIs are also supported.

---

## 🛒 Cart APIs

```http
POST   /cart
GET    /cart
PUT    /cart/{productId}
DELETE /cart/{productId}
DELETE /cart/clear
```

Authentication is required for cart operations.

---

## 🏠 Address APIs

```http
POST   /addresses
GET    /addresses
GET    /addresses/{addressId}
PUT    /addresses/{addressId}
DELETE /addresses/{addressId}
```

---

## 📦 Order APIs

### Place Order

```http
POST /orders/place
```

### Get Order

```http
GET /orders/{orderId}
```

### Get User Orders

```http
GET /orders
```

### Get Active Orders

```http
GET /orders/active
```

### Cancel Order

```http
PUT /orders/{orderId}/cancel
```

---

## 👨‍💼 Admin APIs

Admin APIs provide functionality for:

```text
User Management
Order Management
Product Management
Category Management
Brand Management
Application Statistics
```

Example operations include:

```http
GET /admin/users
GET /admin/orders
PUT /admin/orders/{orderId}/status
GET /admin/users/count
GET /admin/orders/count
GET /admin/products/count
```

---

# 🧪 API Testing

The REST APIs can be tested using **Postman**.

For protected APIs, include:

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

Example login flow:

```text
1. Register user
2. Login
3. Receive JWT token
4. Add token to Authorization header
5. Call protected APIs
```

---

# ⚙️ Configuration

Create your application configuration with the required database and JWT settings.

Example:

```properties
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/shopcart
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

For production, sensitive values such as:

* Database username
* Database password
* JWT secret

should be provided using environment variables rather than committed to GitHub.

---

# ▶️ Running the Project Locally

### 1. Clone the repository

```bash
git clone <YOUR_REPOSITORY_URL>
```

### 2. Open the project

Open the project in:

```text
Eclipse / IntelliJ IDEA
```

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE shopcart;
```

Configure the database credentials in your application configuration.

### 4. Build the project

Using Maven:

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The backend will run on:

```text
http://localhost:8080
```

---

# 🌐 Deployment

The application can be deployed using:

```text
Frontend  → Vercel
Backend   → Render
Database  → Aiven MySQL
```

The frontend communicates with the deployed Spring Boot REST APIs.

---

# 🔒 Security Considerations

The application implements several security practices:

* JWT-based authentication
* Role-based authorization
* Password authentication through Spring Security
* Protected REST endpoints
* User identification from JWT
* Ownership validation for cart, address and order resources
* Admin-only operations

Sensitive configuration values should be stored as environment variables in production.

---

# 🧩 Challenges & Solutions

### 1. JWT User Identification

Instead of accepting `userId` from the frontend for protected operations, the backend extracts it from the JWT token.

### 2. Cart Item Validation

The backend verifies that cart items belong to the authenticated user's cart before processing them.

### 3. Stock Validation

Before placing an order, the backend checks whether sufficient stock is available.

### 4. Entity Relationships

Relationships between User, Cart, CartItem, Product, Order and OrderItem were carefully mapped using JPA annotations.

### 5. Foreign Key Constraints

While managing product deletion, dependent records such as cart items and order items need to be considered because of database foreign key constraints.

---

# 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── com.shopcart
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── entity
    │       ├── dto
    │       ├── security
    │       ├── exception
    │       └── config
    │
    └── resources
        └── application.properties
```

---

# 📌 Future Enhancements

Some possible future improvements:

* Payment gateway integration
* Product reviews and ratings
* Wishlist functionality
* Email notifications
* Order tracking
* Product recommendations
* Pagination and advanced filtering
* Redis caching
* Centralized exception handling
* Automated testing
* CI/CD pipeline

---

# 👨‍💻 Author

**Bhushan Pawar**

B.Tech – Information Technology

### Technologies

```text
Java
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
MySQL
REST APIs
React.js
JavaScript
Git
GitHub
Docker
```

---

## ⭐ Project Highlights

ShopCart demonstrates practical implementation of:

* Full-stack application development
* REST API development
* JWT authentication
* Spring Security
* Role-based authorization
* JPA entity relationships
* Database design
* Cart and order management
* Inventory/stock validation
* DTO-based API design
* Frontend-backend integration
* Cloud deployment

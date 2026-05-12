# ⚡ Flash Cash

> A fictional payment application inspired by PayPal — Spring Boot 3 / Spring Security 6 / Thymeleaf / MySQL

---

## 📋 Overview

Flash Cash is a fictional web payment application that allows users to register, log in, add contacts, and send money to each other with automatic transaction fees applied.

Project built as part of the **DWWM (Web and Mobile Web Developer) — Level 5** certification.

---

## 🚀 Features

| Feature | Description |
|---|---|
| Sign Up | Create an account with first name, last name, email and password |
| Sign In | Email/password authentication via Spring Security |
| Profile | View personal information and current balance |
| Add Funds | Add fictional funds to your account |
| Contacts | Add other users as contacts by email |
| Transfer | Send money to a contact with a 0.5% fee applied |
| History | View all sent and received transfers |
| Logout | Secure logout via Spring Security |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.0.4 |
| Security | Spring Security 6, BCrypt |
| Persistence | Spring Data JPA, Hibernate 6, MySQL 8 |
| Frontend | Thymeleaf, Bootstrap 5, Custom CSS |
| Build | Maven |
| Recommended IDE | IntelliJ IDEA |

---

## 🗂️ Project Structure

```
src/
└── main/
    ├── java/org/ximenia/flashcash/
    │   ├── config/
    │   │   ├── MvcConfig.java              # View controller configuration
    │   │   └── SecurityConfig.java         # Spring Security configuration
    │   ├── controller/
    │   │   ├── UserController.java         # Signup, signin, profile, add-funds
    │   │   ├── ContactController.java      # Add and list contacts
    │   │   └── TransferController.java     # Transfers and history
    │   ├── model/
    │   │   ├── User.java                   # User entity
    │   │   ├── UserAccount.java            # Fictional bank account entity
    │   │   ├── Link.java                   # Link entity between two users
    │   │   └── Transfer.java               # Money transfer entity
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   ├── UserAccountRepository.java
    │   │   ├── LinkRepository.java
    │   │   └── TransferRepository.java
    │   └── service/
    │       ├── AuthenticationService.java  # UserDetailsService for Spring Security
    │       ├── SessionService.java         # Retrieve the currently logged-in user
    │       ├── UserService.java            # Registration, profile, password
    │       ├── LinkService.java            # Contact management
    │       ├── TransferService.java        # Transfer logic (@Transactional)
    │       └── form/
    │           ├── SignUpForm.java
    │           ├── SignInForm.java
    │           ├── AddContactForm.java
    │           ├── TransferForm.java
    │          
    └── resources/
        ├── templates/
        │   ├── signin.html
        │   ├── signup.html
        │   ├── profil.html
        │   ├── contact.html
        │   ├── transfer.html
        │   
        ├── static/
        │   ├── signin.css
        │   ├── profil.css
        │   ├── bootstrap.min.css
        │   └── images/
        └── application.properties
```

---

## 🗄️ Data Model

```
USERS (id, first_name, last_name, email, password, account_id)
   │
   └──< USER_ACCOUNT (account_id, amount, iban)

LINK (id, user1_id, user2_id)
   FK user1_id → USERS
   FK user2_id → USERS

TRANSFER (id, date, sender_id, receiver_id, amount_before_fee, amount_after_fee)
   FK sender_id   → USERS
   FK receiver_id → USERS
```

---

## ⚙️ Installation & Setup

### Prerequisites

- Java 17+
- Maven
- MySQL 8+

### 1. Clone the repository

```bash
git clone https://github.com/XImenia851/Flash-Cash-Project.git
cd Flash-Cash-Project
```

### 2. Create the database

```sql
CREATE DATABASE flashcash;
```

### 3. Configure `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/flashcash?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The application will be available at: **http://localhost:8080**

---

## 💡 Business Logic — Transfer

```
Amount entered by the user  : 100.00 €
Fee (0.5%)                  :   0.50 €
Amount debited from sender  : 100.00 €
Amount credited to receiver :  99.50 €
```

The transfer is wrapped in a `@Transactional` method: if either operation (debit or credit) fails, everything is automatically rolled back.

### Checks before transfer
- The recipient must exist in the database
- The recipient must be in the sender's contact list
- The sender must have sufficient balance

---

## 🔐 Security

- Passwords hashed with **BCrypt**
- **CSRF protection** enabled (tokens on all POST forms)
- Public routes: `/signin`, `/signup`, `/images/**`, `/signin.css`, `/bootstrap.min.css`
- All other routes require authentication
- Secure logout via Spring Security POST

---

## 📸 Pages

| Route | Description |
|---|---|
| `/signin` | Login page |
| `/signup` | Registration page |
| `/profil` | User dashboard |
| `/contact` | Contact management |
| `/transfer` | Send money + transfer history |

---

## 👩‍💻 Author

**Ximenia** — DWWM Level 5 Certification  
Project supervised in my formation

---

## 📄 License

Educational project — fictional use only. No real transactions are performed.

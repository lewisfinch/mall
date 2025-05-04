
## 📦 Project Overview

This project is an **e-commerce system** that allows users to register and log into their accounts, browse products, and filter items by category and price. Users can add specific items and quantities to their shopping cart and place orders directly from the cart.

Upon purchasing, the system automatically deducts the corresponding amount from the user's account balance (each account is initialized with a default balance upon registration) and reduces the product inventory in the database. Thread-safe mechanisms are used to ensure **transaction rollback** in case of insufficient balance or stock, maintaining **data consistency**.

After placing an order, users have **7 days** to request a refund from the order interface. Upon refund, the user's balance and the item's stock are both restored to their original states.

## 🛠️ Technology Stack

* **Backend:** Java 18, Spring Boot
* **Frontend:** Java GUI
* **Database:** MySQL, with MyBatis for ORM and query mapping
* **Other Tools:**

  * **Lombok** for simplifying entity class creation
  * **Maven** for project management

## 🚀 How to Run the Project
1. **Import the project** into IntelliJ IDEA
2. **Set SDK to Java 18** in Project Structure and mark the `frontend` folder as `Sources` in Modules
3. **Configure database connection** in `src/main/resources/application.yaml` (set your MySQL username, password, and port)
4. **Run `mall.sql`** in MySQL to create tables and import sample data
5. Open `pom.xml` and click **Maven → Generate Sources and Reload Project**
6. **Run the frontend** by executing `App` in `src/frontend`
7. **Start the backend** by running `MallApplication` with Spring Boot
8. Once both frontend and backend are running, **register and log in** to begin using the system

## 🔍 Technical Highlights
### `UserService`

* Utilizes **Interceptor** for login validation to prevent unauthorized access
* Stores `UserID` in **ThreadLocal** after login for easy access across services
* Applies **Hash + Salt** to securely encrypt passwords during registration and login
* Ensures balance deduction only when `balance >= amount`, using **MySQL atomic operations** for optimistic locking in concurrent environments

### `ItemService`

* Ensures stock deduction only when `stock >= quantity`, using **optimistic locking** with MySQL to prevent overselling under concurrency

### `OrderService`

* Uses `@Transactional` to ensure **atomicity** during order creation and refund processes involving multiple SQL updates
* In case of an exception, **transactions are rolled back** to maintain data integrity


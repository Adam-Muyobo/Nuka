# **Nuka** 🚀

**A smart, tax-compliant Point of Sale (POS) system for businesses in Zambia**

![Nuka] ![default](https://github.com/user-attachments/assets/99e1afcc-5ba1-47a6-8f1c-38a8cb0f4ffd)


## **📌 Overview**
Nuka is a **modern and efficient Point of Sale system** designed for **small businesses and enterprises in Zambia**. It simplifies **sales tracking, tax compliance, and financial reporting** by integrating with the **ZRA Smart Invoicing API**. 

> **This project is developed as a final year Computer Science project.**

### **✨ Key Features**
- 📷 **Barcode Scanning** (via phone camera)
- 💳 **Multi-Payment Support** (Cash, Mobile Money, Bank Transfers, Cards)
- 📊 **Real-time Sales Analytics & Reporting**
- 🔗 **ZRA Smart Invoicing API Integration** for automated tax compliance
- 🌐 **Cross-platform Compatibility** (Flutter for frontend, Spring Boot for backend)
- ☁️ **Offline Mode with Cloud Sync**
- 🛠 **Scalable & Customizable** for different business needs

---
## **🛠 Tech Stack**

| Component   | Technology |
|-------------|-----------|
| **Frontend** | Flutter (Dart) |
| **Backend**  | Spring Boot (Java) |
| **Database** | MySQL |
| **Containerization** | Docker |

---
## **🚀 Getting Started**

### **📌 Prerequisites**
Ensure you have the following installed:
- [Flutter SDK](https://flutter.dev/docs/get-started/install)
- [Java 17+](https://adoptopenjdk.net/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/downloads/)
- [Docker](https://www.docker.com/get-started)

### **📥 Clone the Repository**
```sh
git clone https://github.com/YOUR-USERNAME/Nuka.git
cd Nuka
```

---
## **📂 Project Structure**
```
Nuka/
│── backend/            # Spring Boot application
│── frontend/           # Flutter application
│── database/           # MySQL scripts
│── docker/             # Docker setup files
│── README.md           # Documentation
```

---
## **🛠 Backend Setup (Spring Boot + MySQL)**

### **1️⃣ Configure Database**
Ensure MySQL is running and create a database:
```sql
CREATE DATABASE nuka;
```
Update `application.properties` in `backend/src/main/resources/`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nuka
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **2️⃣ Run Backend Application**
```sh
cd backend
mvn spring-boot:run
```
The backend will run on **http://localhost:8080**

---
## **📱 Frontend Setup (Flutter)**

### **1️⃣ Install Dependencies**
```sh
cd frontend
flutter pub get
```

### **2️⃣ Run the Application**
```sh
flutter run
```
The app will launch on your connected device/emulator.

---
## **🐳 Docker Setup**
To run the entire system in Docker, use:
```sh
docker-compose up --build
```
This will spin up **MySQL, Spring Boot, and Flutter** in containers.

---
## **📡 API Endpoints** (Sample)
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/products` | Fetch all products |
| `POST` | `/api/sales` | Create a new sale |
| `GET` | `/api/reports` | Get sales reports |

---
## **📌 Contributing**
We welcome contributions! To contribute:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m "Add new feature"`)
4. Push to the branch (`git push origin feature-name`)
5. Open a Pull Request 🚀

---
## **📞 Contact**
For any inquiries or support, reach out via:
- 📧 Personal Email: muyoboadam@gmail.com
- 📧 School Email: 202104060@ub.ac.bw

Happy Coding! 🎉

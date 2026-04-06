# 🎓 Student Management System

A full-stack web application to manage student records — built with **Java Spring Boot**, **Hibernate (JPA)**, **MySQL**, and **Thymeleaf**.

---

## 🚀 Features

- ✅ View all students in a clean dashboard
- ✅ Add new students with a form
- ✅ Edit existing student details
- ✅ Delete students
- ✅ Search students by name
- ✅ Department badge display
- ✅ Responsive UI

---

## 🛠️ Tech Stack

| Layer      | Technology                     |
|------------|-------------------------------|
| Backend    | Java 17, Spring Boot 3.2      |
| ORM        | Hibernate / Spring Data JPA   |
| Database   | MySQL                         |
| Frontend   | HTML5, CSS3, Thymeleaf        |
| Build Tool | Maven                         |

---

## 📁 Project Structure

```
student-management/
├── src/main/java/com/madhu/student/
│   ├── StudentManagementApplication.java   ← Main class
│   ├── controller/StudentController.java   ← Handles HTTP requests
│   ├── model/Student.java                  ← Entity/DB table
│   ├── repository/StudentRepository.java   ← DB queries
│   └── service/StudentService.java         ← Business logic
├── src/main/resources/
│   ├── templates/
│   │   ├── index.html                      ← Dashboard
│   │   ├── add-student.html                ← Add form
│   │   └── edit-student.html               ← Edit form
│   ├── static/css/style.css
│   ├── static/js/script.js
│   └── application.properties             ← DB config
└── pom.xml
```

---

## ⚙️ How to Run

### Prerequisites
- Java 17+
- MySQL installed and running
- Maven

### Steps

1. **Clone the repo**
```bash
git clone https://github.com/YOUR_USERNAME/student-management.git
cd student-management
```

2. **Configure MySQL**

Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

3. **Run the app**
```bash
mvn spring-boot:run
```

4. **Open in browser**
```
http://localhost:8080
```

The database and tables are created automatically by Hibernate.

---

## 📸 Screenshots

> Dashboard showing list of students with Add, Edit, Delete, and Search features.

---

## 👤 Author

**Madhu A L**  
Java Full Stack Developer  
📧 madhulokesh1117@gmail.com  
🔗 [linkedin.com/in/madhual](https://linkedin.com/in/madhual)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

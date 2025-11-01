# 👤 Usuarios-Zabat

**CRUD de Usuarios y Roles con Spring Boot + Oracle Autonomous Database (Wallet)**

---

## 🚀 Descripción

Microservicio desarrollado en **Java Spring Boot** que permite gestionar usuarios y roles dentro de un sistema.  
Se conecta a una base de datos **Oracle Cloud Autonomous Database** mediante un **Wallet**,  
utilizando **Spring Data JPA (Hibernate)** para la persistencia de datos.

Este proyecto forma parte de la experiencia **“Programando nuestro BackEnd”**, correspondiente al caso  
**Gestión de Laboratorios y Resultados de Análisis Clínicos**, implementando el **microservicio de usuarios**.

---

## 🧩 Funcionalidades (CRUD)

| Método   | Endpoint                         | Descripción                                   |
| :------- | :-------------------------------- | :-------------------------------------------- |
| `GET`    | `/users`                          | Lista todos los usuarios                      |
| `GET`    | `/users/{id}`                     | Obtiene un usuario por su ID                  |
| `POST`   | `/users`                          | Crea un nuevo usuario                         |
| `PUT`    | `/users/{id}`                     | Actualiza los datos de un usuario existente   |
| `DELETE` | `/users/{id}`                     | Elimina un usuario                            |
| `GET`    | `/roles`                          | Lista todos los roles disponibles             |
| `POST`   | `/users/{id}/roles/{roleName}`    | Asigna un rol existente a un usuario específico |

---

## 🧱 Estructura del proyecto


src/main/java/com/zabat/
├── controller/
│ ├── UserController.java # Controlador REST para usuarios
│ └── RoleController.java # Controlador REST para roles
├── entity/
│ ├── User.java # Entidad JPA para la tabla USERS
│ └── Role.java # Entidad JPA para la tabla ROLES
├── repository/
│ ├── UserRepository.java # Repositorio CRUD de usuarios
│ └── RoleRepository.java # Repositorio CRUD de roles
└── UsuariosZabatApplication.java # Clase principal de arranque


---

## 🗄️ Entidades

### Tabla `USERS`

| Campo           | Tipo      | Descripción                  |
| :--------------- | :-------- | :---------------------------- |
| `ID`             | NUMBER    | Identificador único del usuario |
| `USERNAME`       | VARCHAR2  | Nombre de usuario único       |
| `EMAIL`          | VARCHAR2  | Correo electrónico único      |
| `PASSWORD_HASH`  | VARCHAR2  | Contraseña cifrada (BCrypt)   |
| `ENABLED`        | NUMBER(1) | Estado (1 = activo, 0 = inactivo) |
| `CREATED_AT`     | TIMESTAMP | Fecha de creación             |

### Tabla `ROLES`

| Campo        | Tipo      | Descripción                   |
| :------------ | :-------- | :----------------------------- |
| `ID`          | NUMBER    | Identificador único del rol    |
| `NAME`        | VARCHAR2  | Nombre del rol (`ADMIN`, `LAB_TECH`, `CLIENT`) |
| `DESCRIPTION` | VARCHAR2  | Descripción del rol            |

### Tabla `USER_ROLES`

| Campo      | Tipo   | Descripción                           |
| :---------- | :----- | :------------------------------------ |
| `USER_ID`   | NUMBER | Relación con la tabla USERS           |
| `ROLE_ID`   | NUMBER | Relación con la tabla ROLES           |

---

## ⚙️ Tecnologías utilizadas

- Java 17  
- Spring Boot 3.5.7  
- Spring Data JPA (Hibernate)  
- Oracle JDBC (ojdbc11)  
- Maven  
- HikariCP (pool de conexiones)  
- Lombok  

---

## 🌐 Configuración de conexión (`application.properties`)

```properties
spring.datasource.url=jdbc:oracle:thin:@lkuafzuwzts282m1_low?TNS_ADMIN=C:/Wallet_Libros/Wallet_LKUAFZUWZTS282M1
spring.datasource.username=ADMIN
spring.datasource.password=Elprueba1234!
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.connection-timeout=30000

logging.level.com.zaxxer.hikari=DEBUG
logging.level.oracle.jdbc=DEBUG

server.port=8082



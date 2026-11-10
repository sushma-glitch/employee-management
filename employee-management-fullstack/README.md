# Employee Management Full Stack — based on your requested tools

**Backend:** Java 17, Spring Boot, Maven, MySQL, Redis, JUnit 5, Mockito  
**Frontend:** Angular 20, TypeScript, HTML/CSS, HttpClient, Reactive Forms  
**Tools:** VS Code for Angular, IntelliJ IDEA or VS Code for Java, Postman for API testing. No Docker.

## Install/use the tools you listed
- VS Code: https://code.visualstudio.com/download
- Temurin JDK 17: https://adoptium.net/temurin/releases/?version=17
- Maven: https://maven.apache.org/download.cgi
- Node.js LTS: https://nodejs.org/en/download
- MySQL Community Installer (Server): https://dev.mysql.com/downloads/installer/
- WSL Ubuntu: https://learn.microsoft.com/windows/wsl/install
- Postman: https://www.postman.com/downloads/
MySQL Workbench and Ubuntu Desktop are optional/not required for this project.

## 1. MySQL
Start the MySQL service, then connect using your preferred MySQL client and run:
```sql
CREATE DATABASE IF NOT EXISTS employee_management;
```
Set the backend's actual MySQL credentials in PowerShell before starting:
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
```
Defaults in `application.properties` are `root` / `change_me`; replace them with your local password. Never commit a real password.

## 2. Redis in Ubuntu WSL
Open PowerShell:
```powershell
wsl.exe -d Ubuntu
```
In Ubuntu:
```bash
sudo apt update
sudo apt install redis-server -y
sudo service redis-server start
redis-cli ping
```
Expected output: `PONG`. Keep Redis running. The app expects Redis on `localhost:6379`.

## 3. Backend
Open the `backend` folder in IntelliJ IDEA or VS Code. Set Java/JDK to 17 and reload/import `pom.xml` as a Maven project.
From PowerShell in the `backend` folder:
```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
mvn clean test
mvn spring-boot:run
```
API base URL: `http://localhost:8080/api/employees`

## 4. Angular frontend in VS Code
Open the `frontend` folder in VS Code. In the VS Code terminal:
```powershell
npm install
npm start
```
Angular prints its local URL, normally `http://localhost:4200`. Open it in Chrome.
The Angular dev server requires network access on first run to download npm packages.

## 5. Features
- Create, list, filter by department, edit and delete employees.
- Angular form validation and visible API errors.
- Spring Boot Controller -> Service -> Repository architecture.
- MySQL persists employee records.
- Redis caches GET-by-ID results for 10 minutes; create/update/delete clear employee cache.
- JUnit 5 and Mockito unit tests.
- Log file: `backend/logs/employee-management.log`.

## 6. Test APIs with Postman
Base URL: `http://localhost:8080/api/employees`

| Method | URL | Action |
|---|---|---|
| GET | `/api/employees` | List |
| GET | `/api/employees/1` | Get by ID |
| GET | `/api/employees?department=Engineering` | Filter |
| POST | `/api/employees` | Create |
| PUT | `/api/employees/1` | Update |
| DELETE | `/api/employees/1` | Delete |

POST body (Body -> raw -> JSON):
```json
{"firstName":"Asha","lastName":"Sharma","email":"asha@example.com","department":"Engineering","salary":85000}
```

## Troubleshooting
- MySQL login issue: set `DB_USERNAME` and `DB_PASSWORD` to match your server.
- Redis refused: in Ubuntu run `sudo service redis-server start` then `redis-cli ping`.
- Maven errors: confirm `mvn -version` and Java 17; reload Maven project.
- Angular dependency errors: confirm `node -v` and `npm -v`, then retry `npm install`.

# Samaira Diet Planner

This project runs locally with:
- Angular frontend
- Java Spring Boot backend
- MySQL database
- Excel import and export support
- PDF download from dashboard

## Project Structure
- [CLAUDE.md](CLAUDE.md)
- [backend](backend)
- [frontend](frontend)
- [database/schema.sql](database/schema.sql)

## Prerequisites
1. Java 17 or higher
2. Maven 3.9 or higher
3. Node.js 18 or higher
4. npm 9 or higher
5. MySQL 8.x running locally

## Run Sequence

### Step 1: Start MySQL
Make sure MySQL is up before starting backend.

If you use Homebrew on macOS, you can run:
## Command for database
  Step1 : brew services start mysql
  Step2 : mysql -u root / mysql -u root -p( for password prompt)
  Step3 : SHOW DATABASES;
  Step4 : USE samaira_diet;
  Step5 : SHOW TABLES;
  Step6 : DESCRIBE users;
  Step7 : select * from users;

### Step 2: Create Database Schema
From project root:
mysql -u root -p < database/schema.sql

If your MySQL root user has no password locally, use:
mysql -u root < database/schema.sql

### Step 3: Check Backend DB Credentials
Backend DB config is in [backend/src/main/resources/application.properties](backend/src/main/resources/application.properties).

Default values currently expected by app:
- username: root
- password: root
- database: samaira_diet

If your local MySQL password is different, edit the password value in that file.

### Step 4: Start Backend
From project root:
cd backend
mvn spring-boot:run

Backend should be available at:
http://localhost:8080

### Step 5: Install Frontend Dependencies
Open a new terminal from project root:
cd frontend
npm install

### Step 6: Start Frontend
In frontend folder:
npm start

Frontend should be available at:
http://localhost:4200

### Step 7: Use the App
1. Open http://localhost:4200
2. Register a new user
3. Login
4. Open dashboard
5. Validate diet chart sections
6. Download PDF
7. Download Excel
8. Import Excel with columns exactly as:
Section, MealType, Item, SortOrder

## Useful Commands (Quick List)
From project root:
- Start backend:
  cd backend
  mvn spring-boot:run

- Start frontend:
  cd frontend
  npm install
  npm start

- Build backend jar (without tests):
  cd backend
  mvn -DskipTests package

- Build frontend:
  cd frontend
  npm run build

## Common Issues and Fast Fixes
1. Backend fails to start with database connection error
- Confirm MySQL is running
- Confirm username and password in [backend/src/main/resources/application.properties](backend/src/main/resources/application.properties)
- Re-run schema command

2. Frontend shows CORS or 401 errors
- Confirm backend is running on port 8080
- Login again so token is stored
- Confirm frontend is running on port 4200

3. Excel import fails
- Ensure file is xlsx format
- Ensure first sheet columns are:
  Section, MealType, Item, SortOrder
- Ensure enum values are valid examples:
  MORNING, AFTERNOON, EVENING, NIGHT
  BREAKFAST, LUNCH, DINNER, SNACK, DRINK

4. PDF button does nothing
- Check browser console for blocked popup or script errors
- Refresh dashboard and retry

## How We Will Work on Fixes
If you hit any issue, send me:
1. Exact command you ran
2. Full terminal error output
3. Which step number failed in this README
4. Optional screenshot

I will patch the code and give you the next exact command sequence.

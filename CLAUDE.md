# Samaira Diet Planner - CLAUDE Plan and Implementation Log

## Goals
- Build a local-only diet planner with morning-to-night sections.
- Frontend: Angular + HTML + CSS + JavaScript.
- Backend: Java Spring Boot API.
- Database: MySQL open source.
- Excel in v1: import and export support.
- PDF in v1: frontend download from visible chart.
- Exclude CI/CD and deployment in v1.

## Current V1 Scope
- Multi-user authentication (register/login) with JWT.
- Authenticated dashboard showing Samaira's diet chart.
- Fixed seeded diet chart as the starter template.
- Excel upload (.xlsx import) to replace current chart rows.
- Excel download (.xlsx export) for current chart rows.
- PDF download from rendered dashboard chart.

## Implemented Structure

### Backend
- Spring Boot app with security, auth, diet APIs, and Excel APIs.
- API endpoints:
	- `POST /api/auth/register`
	- `POST /api/auth/login`
	- `GET /api/auth/me`
	- `GET /api/diet-plan`
	- `GET /api/excel/export`
	- `POST /api/excel/import` (multipart form-data with `file`)
- MySQL entities:
	- `users`
	- `diet_plan_entries`
- Startup seeding includes the expected sample entries.

### Frontend
- Angular standalone app with routes:
	- `/login`
	- `/register`
	- `/dashboard` (auth-protected)
- Dashboard features:
	- Render grouped diet chart sections.
	- Download Diet Chart as PDF.
	- Download Excel.
	- Import Excel.

### Database
- `database/schema.sql` added for manual DB setup.
- `database/sample-diet-plan.csv` added as a quick sample reference.

## Local Run Plan

### 1. MySQL
1. Create database and tables with `database/schema.sql`.
2. Use/update credentials in `backend/src/main/resources/application.properties`.

### 2. Backend
1. Open terminal in `backend`.
2. Run `mvn spring-boot:run`.
3. API base URL: `http://localhost:8080/api`.

### 3. Frontend
1. Open terminal in `frontend`.
2. Run `npm install`.
3. Run `npm start`.
4. App URL: `http://localhost:4200`.

## Initial Test Checklist
1. Register a new user.
2. Login and open dashboard.
3. Verify Morning/Afternoon/Evening/Night sections render.
4. Click Download Diet Chart as PDF and verify output opens.
5. Click Download Excel and verify `.xlsx` downloads.
6. Upload a valid `.xlsx` with columns: `Section, MealType, Item, SortOrder`.
7. Confirm dashboard reloads with imported values.

## V1 Non-Goals
- No CI/CD pipelines.
- No cloud deployment artifacts.

## Next Review Gates
1. End-to-end run confirmation on your machine.
2. API and UI polish from your review comments.
3. Add tests (backend integration + frontend component-level smoke).
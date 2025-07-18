<div align="center">
    <img src="./Frontend/public/favicon.svg" height="80px"></img>
</div>

# LGBTMythOrFact 🏳️‍🌈

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=oracle&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18+-20232A?logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-38B2AC?logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![PostgreSQL](http://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)](https://www.postgresql.com/)
[![Vite](https://img.shields.io/badge/Vite-646CFF?logo=vite&logoColor=white)](https://vitejs.dev/)

---

An interactive full-stack quiz game designed to educate and challenge players' knowledge about LGBTQ+ topics. Test your understanding, learn new facts, and compete with others on the leaderboard!

## 🎮 Features

- **Interactive Quiz Game**: Answer true/false questions about LGBTQ+ topics
- **Multiple Difficulty Levels**: Easy, Hard, or Expert
- **User Authentication**: JWT-based secure authentication system and email confirmation
- **Leaderboard**: Compete with other players globally
- **Responsive Design**: Play on desktop, tablet, or mobile
- **Real-time Scoring**: Track your score and streaks
- **Progress Tracking**: See your highest scores and improvement

## 🛠️ Tech Stack

### Frontend
- **Framework**: React 18 + TypeScript
- **Styling**: Tailwind CSS with custom design system
- **UI Components**: Radix UI + shadcn/ui
- **Build Tool**: Vite
- **State Management**: React Context API
- **Icons**: Lucide React
- **Routing**: React Router DOM

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Security**: JWT Authentication
- **Database**: MySQL (development) / PostgreSQL (production)
- **Documentation**: OpenAPI 3 / Swagger UI
- **Build Tool**: Maven

## 🗄️ Database Schema

<div align="center">
    <img width="85%" src="https://github.com/user-attachments/assets/1a0354c4-4f58-4a7e-835f-f8ffdfea77c3" alt="Database Schema Diagram">
</div>

### Environment Configuration

| Environment | Database | Version |
|-------------|----------|---------|
| Development | MySQL | 8.x |
| Production | PostgreSQL | Supabase |
| Testing | H2 | 2.3.x |

### Schema Overview

#### Users Table
Manages user accounts and authentication data.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | int8 | PRIMARY KEY, AUTO INCREMENT | Unique user identifier |
| `username` | varchar | UNIQUE, NOT NULL | Login username |
| `email` | varchar | UNIQUE, NOT NULL | User email address |
| `password` | varchar | NOT NULL | Encrypted password hash |
| `created_at` | timestamp | DEFAULT NOW() | Account creation date |
| `highest_score` | int4 | DEFAULT 0 | User's best game score |
| `role` | varchar | DEFAULT 'USER' | User role (USER, ADMIN) |
| `email_verified` | bool | DEFAULT false | Email verification status |
| `confirmation_token` | varchar | NULLABLE | Email verification token |
| `token_creation_date` | timestamp | NULLABLE | Token creation timestamp |
| `reset_password_token` | varchar | NULLABLE | Password reset token |
| `reset_password_token_expires` | timestamp | NULLABLE | Token expiration time |

#### Statements Table
Contains quiz questions with educational content.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | int8 | PRIMARY KEY, AUTO INCREMENT | Unique statement identifier |
| `statement` | text | NOT NULL | Quiz question content |
| `is_fact` | bool | NOT NULL | True for facts, false for myths |
| `explanation` | text | NOT NULL | Educational explanation |
| `difficulty` | int4 | CHECK (1-3) | Difficulty level (1=Easy, 2=Hard, 3=Expert) |
| `category` | varchar | NOT NULL | Topic category |

#### Game History Table
Records individual game sessions and performance metrics.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | int8 | PRIMARY KEY, AUTO INCREMENT | Unique game session identifier |
| `user_id` | int8 | FOREIGN KEY, NOT NULL | References users.id |
| `score` | int4 | NOT NULL | Final game score |
| `played_at` | timestamp | DEFAULT NOW() | Game session timestamp |

#### Flyway Schema History Table
Migration tracking managed by Flyway framework.

| Column | Type | Description |
|--------|------|-------------|
| `installed_rank` | int4 | Migration execution sequence |
| `version` | varchar | Migration version identifier |
| `description` | varchar | Migration description |
| `type` | varchar | Migration type (SQL, JAVA) |
| `script` | varchar | Migration script filename |
| `checksum` | int4 | Script integrity checksum |
| `installed_by` | varchar | Migration executor |
| `installed_on` | timestamp | Migration execution time |
| `execution_time` | int4 | Execution duration (milliseconds) |
| `success` | bool | Migration success status |

### Relationships

```
users (1) ----< game_history (N)
  |
  └── One user can have multiple game sessions
```

### Database Setup

#### Local Development
```sql
CREATE DATABASE lgbt-game 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

#### Production
Production database runs on Supabase with:
- Automatic backups
- Real-time synchronization
- Built-in authentication
- Row-level security

### Migration Management

Database migrations are handled by Flyway with database-specific versioned scripts:

#### MySQL Migrations (`/Backend/src/main/resources/db/migration/mysql/`)
- `V1__Initial_schema.sql` - Base schema creation
- `V2__Add_role_to_user_table.sql` - User role implementation
- `V3__Make_role_not_null.sql` - Role field constraints
- `V4__Add_email_verified_to_user_table.sql` - Email verification field
- `V5__Add_confirmation_fields_to_user_table.sql` - Confirmation token system
- `V6__Add_password_reset_fields_to_user_table.sql` - Password reset functionality
- `V7__insert_initial_data.sql` - Initial data seeding

#### PostgreSQL Migrations (`/Backend/src/main/resources/db/migration/postgresql/`)
- `V1__Initial_schema.sql` - Base schema creation
- `V2__Add_role_to_user_table.sql` - User role implementation
- `V3__Make_role_not_null.sql` - Role field constraints
- `V4__Add_email_verified_to_user_table.sql` - Email verification field
- `V5__Add_confirmation_fields_to_user_table.sql` - Confirmation token system
- `V6__Add_password_reset_fields_to_user_table.sql` - Password reset functionality

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Node.js** (v16 or higher)
- **Maven** (3.6.x or newer)
- **MySQL Server** (for local development)
- **Git**

### Installation

1. **Clone the repository:**
```bash
git clone git@github.com:Veras-D/MythOrFactLGBTQIA.git
cd MythOrFactLGBTQIA
```

2. **Backend Setup:**
```bash
cd Backend

# Create MySQL database
mysql -u root -p
CREATE DATABASE lgbt-game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Configure database connection
# Edit src/main/resources/application-dev.properties
# Update spring.datasource.username and spring.datasource.password

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

3. **Frontend Setup:**
```bash
cd Frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

4. **Access the application:**
   - Frontend: `http://localhost:5173`
   - Backend API: `http://localhost:8080`
   - API Documentation: `http://localhost:8080/swagger-ui/index.html`

#### Run with docker
For simple test you can use:
```bash
npm run nginx:dev
```
This comand will build a docker compose without any previous setup, please have sure to configure your `.env` appropriately. You can access the application in `http://localhost`.
Look at `docker-compose.dev.yml` and `packaje.json` for more details.

## 📁 Project Structure
```
.
├── Backend/                    # Spring Boot API
│   ├── src/main/java/com/veras/mythOrFactLGBT/
│   │   ├── config/            # Security, OpenAPI & Environment configuration
│   │   ├── controller/        # REST API endpoints
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── model/            # JPA Entity models
│   │   ├── repository/       # Data access layer
│   │   ├── security/         # JWT authentication & filters
│   │   └── service/          # Business logic implementation
│   ├── src/main/resources/   # Configuration files & database migrations
│   │   ├── db/migration/     # Flyway database migrations
│   │   │   ├── mysql/        # MySQL-specific migrations
│   │   │   └── postgresql/   # PostgreSQL-specific migrations
│   │   └── application*.properties
│   ├── src/test/             # Unit & integration tests
│   ├── Dockerfile            # Production container configuration
│   ├── Dockerfile.dev        # Development container configuration
│   └── pom.xml              # Maven dependencies
├── Frontend/                  # React TypeScript application
│   ├── src/
│   │   ├── components/       # Reusable UI components
│   │   ├── contexts/         # React contexts (Auth, etc.)
│   │   ├── hooks/           # Custom React hooks
│   │   ├── lib/             # Utility functions & API client
│   │   └── pages/           # Page components & routing
│   ├── public/              # Static assets
│   ├── Dockerfile.dev       # Development container configuration
│   └── package.json         # NPM dependencies
├── .github/workflows/        # CI/CD pipeline configuration
├── nginx/                    # Reverse proxy configuration
├── docker-compose.dev.yml    # Development environment setup
├── turbo.json               # Monorepo build configuration
└── README.md
```

## 🎯 How to Play

1. **Sign Up/Login**: Create an account to track your progress
2. **Answer Questions**: Read statements and decide if they're Myth or Fact
3. **Track Your Score**: Monitor your current score and streak
4. **Check Leaderboard**: Compare your performance with other players

## 🔗 Pages

### Home
The main game interface where users can play the LGBT+ myth or fact quiz.

**Route:** `/`

| Interface | Screenshot |
|-----------|------------|
| **Game Page** | <img width="400" alt="Game Page" src="https://github.com/user-attachments/assets/e278261d-c30b-4690-9a00-3bed9cada371" /> |
| **Profile Modal** | <img width="400" alt="Profile Modal" src="https://github.com/user-attachments/assets/63a659d1-9362-4075-a39b-696fb0f5ef4a" /> |
| **Leaderboard Modal** | <img width="400" alt="Leaderboard Modal" src="https://github.com/user-attachments/assets/00a1caa5-0865-4d55-a1d6-73cfb7b398a0" /> |
| **Login Modal** | <img width="400" alt="Login Modal" src="https://github.com/user-attachments/assets/6fa67ff5-8cbd-48f7-a06a-427f2d511151" /> |
| **Sign Up Modal** | <img width="400" alt="Sign Up Modal" src="https://github.com/user-attachments/assets/af951985-5bb9-4d96-befc-67d40f276975" /> |

### Email Confirmation
Page for users to confirm their email address after registration.

**Route:** `/confirm-email`

| Interface | Screenshot |
|-----------|------------|
| **Website** | <img width="400" alt="Email Confirmation Page" src="https://github.com/user-attachments/assets/6ccb7ed9-7f96-4c3f-b7bc-e31808630ec9" /> |
| **Email Template** | <img width="400" alt="Email Confirmation Template" src="https://github.com/user-attachments/assets/4099c6b2-0c1d-4567-aaa0-5c3a037e33e7" /> |
| **Failure State** | <img width="400" alt="Email Confirmation Failure" src="https://github.com/user-attachments/assets/b38d5677-e770-43b8-9ca0-9a57cfacb668" /> |
| **Success State** | <img width="400" alt="Email Confirmation Success" src="https://github.com/user-attachments/assets/14192033-608f-441d-af2e-2709bb6b7fb6" /> |

### Forgot Password
Password recovery page where users can request a password reset link.

**Route:** `/forgot-password`

| Interface | Screenshot |
|-----------|------------|
| **Website** | <img width="400" alt="Forgot Password Page" src="https://github.com/user-attachments/assets/7519e6e1-60c7-4a89-8e56-f8c0f0720d13" /> |
| **Email Template** | <img width="400" alt="Password Reset Email" src="https://github.com/user-attachments/assets/421f5bd3-ffdd-48c8-ab40-4c023ad63141" /> |


### Reset Password
Page where users can set a new password using the reset token from email.

**Route:** `/reset-password`

| Interface | Screenshot |
|-----------|------------|
| **Website** | <img width="400" alt="Reset Password Page" src="https://github.com/user-attachments/assets/be391791-3c0e-4cc1-aa5a-789f334063b5" /> |
| **Invalid Token State** | <img width="400" alt="Invalid Token State" src="https://github.com/user-attachments/assets/a49090d4-79b9-459f-bbbc-b00de539faff" /> |
| **Sucess State** | <img width="400" alt="Sucess State" src="https://github.com/user-attachments/assets/b89d9709-2e6d-4c80-ab8e-9a415a9a6e76" /> |

### Statement Management
Administrative interface for managing quiz statements (ADMIN role only).

**Route:** `/admin/statements`

| Interface | Screenshot |
|-----------|------------|
| **Admin Panel** | <img width="400" alt="Statement Management Page" src="https://github.com/user-attachments/assets/c81d385f-9930-4b30-8001-733c6cad381f" /> |

### Not Found
404 error page displayed when users navigate to non-existent routes.

**Route:** `*` (catch-all route)

| Interface | Screenshot |
|-----------|------------|
| **404 Page** | <img width="400" alt="Not Found Page" src="https://github.com/user-attachments/assets/8fa73713-a653-446c-96cf-6c037773fb14" /> |

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration (public)
- `POST /api/auth/login` - User login and JWT token generation (public)
- `GET /api/auth/confirm` - Email confirmation via token (public)
- `POST /api/auth/forgot-password` - Request password reset link (public)
- `POST /api/auth/reset-password` - Reset password using token (public)

### Users & Leaderboard
- `GET /api/users/me` - Get current user profile (authenticated)
- `DELETE /api/users/me` - Delete authenticated user's account (authenticated)
- `GET /api/users/{id}` - Get user by ID (authenticated)
- `DELETE /api/users/{id}` - Delete user by ID (ADMIN only)
- `GET /api/users/leaderboard` - Get global top 10 leaderboard (public)

### Statements
- `GET /api/statements` - Get all quiz statements with optional filters (public)
  - Query params: `category`, `difficulty`
- `GET /api/statements/{id}` - Get specific statement by ID (public)
- `POST /api/statements` - Create new statement (ADMIN only)
- `PUT /api/statements/{id}` - Update existing statement (ADMIN only)
- `DELETE /api/statements/{id}` - Delete statement (ADMIN only)

### Game History
- `POST /api/gamehistory` - Record new game session (authenticated)
- `GET /api/gamehistory/user/me` - Get current user's game history (authenticated)
- `GET /api/gamehistory/user/{userId}` - Get game history for specific user (authenticated)
- `GET /api/gamehistory/leaderboard/user/{userId}` - Get score-ordered personal bests (authenticated)

### API Documentation
- **Swagger UI**: https://mythorfactlgbtqia.onrender.com/swagger-ui/index.html
- **OpenAPI Spec**: https://mythorfactlgbtqia.onrender.com/

#### Access Control
- **Public**: Authentication endpoints, statement reading, global leaderboard
- **Authenticated**: User profile, game history, user-specific data
- **ADMIN only**: User management, statement CRUD operations

## 🧪 Testing

### Backend Tests
```bash
cd Backend
mvn test
```

### Frontend Tests
```bash
cd Frontend
npm run lint
```

## 🔐 Environment Variables

### Backend (.env)
- SPRING_PROFILES_ACTIVE: Default=dev
- DB_DEV_USERNAME
- DB_DEV_PASSWORD
- DB_PROD_USERNAME
- DB_PROD_PASSWORD
- DB_STRING
- DB_DEV_STRING
- DB_DEV_DRIVER
- JWT_SECRET: Default=testing
- BACKEND_BASE_URL: Default=http://localhost:8080
- FRONTEND_BASE_URL: Default=http://localhost:5173
- EMAIL_SENDER
- APP_PASSWORD

### Frontend (.env)
- VITE_API_URL: Default=http://localhost:8080/api

## 🎨 Design System

The application features a comprehensive design system with:
- **Semantic color tokens**: Defined in `index.css` and `tailwind.config.ts`
- **Pride theme**: Rainbow gradients and LGBTQ+ inspired colors
- **Glassmorphism**: Modern glass-like UI elements
- **Responsive design**: Mobile-first approach
- **Accessibility**: Proper contrast and keyboard navigation

## 🔧 Development

### Backend Development
- **IDE**: Spring Tools Suite (STS) recommended
- **Profiles**:
  - `dev`: Local development with MySQL
  - `prod`: Production with PostgreSQL
- **Documentation**: Swagger UI available at `/swagger-ui/index.html`

### Frontend Development
- **Available Scripts**:
  - `npm run dev` - Start development server
  - `npm run build` - Build for production
  - `npm run preview` - Preview production build
  - `npm run lint` - Run ESLint

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Make your changes
4. Commit: `git commit -am 'Add new feature'`
5. Push: `git push origin feature/new-feature`
6. Create a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🏳️‍🌈 About

This game was created to promote LGBTQ+ education and awareness through interactive learning. It aims to dispel myths and provide accurate information about LGBTQ+ topics in an engaging, game-like format.

The project combines a robust Spring Boot backend with a modern React frontend to deliver a seamless, educational gaming experience that helps build understanding and acceptance of LGBTQ+ communities.

## ⚙️ Deploy 
> [!TIP]
> - **Frontend**: https://myth-or-fact-lgbtqia.vercel.app/ 
> - **Backend**: https://mythorfactlgbtqia.onrender.com/

## ☕ Support

[![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/verivi)

---

<div align="center">
  <p>© 2025 VERAS. All rights reserved.</p>
</div>

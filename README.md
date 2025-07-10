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
- **User Authentication**: JWT-based secure authentication system
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
    <img src="https://github.com/user-attachments/assets/2d21cecb-bfd4-4afe-9363-9c246fc66504"></img>
</div>

### Development & Production
- **Development**: MySQL 8.x
- **Production**: PostgreSQL (Supabase)
- **Test**: H2 2.3.x

### Tables Overview

#### Users Table
Stores user account information and authentication data.

| Column | Type | Description |
|--------|------|-------------|
| `id` | int8 | Primary key, auto-increment |
| `username` | varchar | Unique username for login |
| `email` | varchar | User email address |
| `password` | varchar | Encrypted password hash |
| `created_at` | timestamp | Account creation timestamp |
| `highest_score` | int4 | User's best game score |
| `role` | varchar | User role (USER, ADMIN) |

#### Statements Table
Contains quiz questions with answers and metadata.

| Column | Type | Description |
|--------|------|-------------|
| `id` | int8 | Primary key, auto-increment |
| `statement` | text | The quiz question/statement |
| `is_fact` | bool | True if statement is fact, false if myth |
| `explanation` | text | Educational explanation for the answer |
| `difficulty` | int4 | Difficulty level (1=Easy, 2=Hard, 3=Expert) |
| `category` | varchar | Topic category for organization |

#### Game History Table
Tracks individual game sessions and player performance.

| Column | Type | Description |
|--------|------|-------------|
| `id` | int8 | Primary key, auto-increment |
| `user_id` | int8 | Foreign key referencing users.id |
| `score` | int4 | Final score achieved in the game |
| `played_at` | timestamp | When the game session occurred |

#### Flyway Schema History Table
Migration tracking table managed by Flyway.

| Column | Type | Description |
|--------|------|-------------|
| `installed_rank` | int4 | Migration execution order |
| `version` | varchar | Migration version number |
| `description` | varchar | Migration description |
| `type` | varchar | Migration type (SQL, JAVA, etc.) |
| `script` | varchar | Migration script filename |
| `checksum` | int4 | Script checksum for validation |
| `installed_by` | varchar | User who executed migration |
| `installed_on` | timestamp | Migration execution timestamp |
| `execution_time` | int4 | Time taken to execute (ms) |
| `success` | bool | Whether migration succeeded |

### Relationships

- `game_history.user_id` → `users.id` (Many-to-One)
  - Each game session belongs to one user
  - Users can have multiple game sessions

### Database Setup

#### Local Development (MySQL)
```sql
CREATE DATABASE lgbt-game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Production (PostgreSQL)
The production database is hosted on Supabase with automatic backups and scaling.

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
CREATE DATABASE mythOrFactLGBT_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Configure database connection
# Edit src/main/resources/application-dev.properties
# Update spring.datasource.username and spring.datasource.password

# Install dependencies and run
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=dev
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
   - API Documentation: `http://localhost:8080/swagger-ui.html`

## 📁 Project Structure

```
.
├── Backend/                    # Spring Boot API
│   ├── src/main/java/com/veras/mythOrFactLGBT/
│   │   ├── config/            # Security & OpenAPI configuration
│   │   ├── controller/        # REST API endpoints
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── model/            # JPA Entity models
│   │   ├── repository/       # Data access layer
│   │   ├── security/         # JWT authentication
│   │   └── service/          # Business logic
│   ├── src/main/resources/   # Configuration files
│   └── src/test/             # Unit & integration tests
├── Frontend/                  # React application
│   ├── src/
│   │   ├── components/       # Reusable UI components
│   │   ├── contexts/         # React contexts
│   │   ├── data/            # Static quiz data
│   │   ├── hooks/           # Custom React hooks
│   │   ├── lib/             # Utility functions
│   │   └── pages/           # Page components
│   └── public/              # Static assets
├── Data/                     # Database schema & seed data
│   ├── schema.sql
│   └── data.sql
└── README.md
```

## 🎯 How to Play

1. **Sign Up/Login**: Create an account to track your progress
2. **Answer Questions**: Read statements and decide if they're Myth or Fact
3. **Track Your Score**: Monitor your current score and streak
4. **Check Leaderboard**: Compare your performance with other players

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Users
- `GET /api/users/me` - Get current user profile
- `GET /api/users/{id}` - Get user by ID

### Statements
- `GET /api/statements` - Get all quiz statements
- `POST /api/statements` - Create new statement
- `PUT /api/statements/{id}` - Update statement
- `DELETE /api/statements/{id}` - Delete statement

### Game History
- `GET /api/gamehistory` - Get user's game history
- `POST /api/gamehistory` - Record new game session

## 🧪 Testing

### Backend Tests
```bash
cd Backend
mvn test
```

### Frontend Tests
```bash
cd Frontend
npm run test
```

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
- **Documentation**: Swagger UI available at `/swagger-ui.html`

### Frontend Development
- **Available Scripts**:
  - `npm run dev` - Start development server
  - `npm run build` - Build for production
  - `npm run preview` - Preview production build
  - `npm run lint` - Run ESLint

### Adding New Questions

Edit `Frontend/src/data/statements.ts` and add new statement objects:
```typescript
{
  id: "unique-id",
  statement: "The question text",
  isTrue: boolean,
  category: "topic-category",
  difficulty: "easy" | "hard" | "expert",
  explanation: "Educational explanation"
}
```

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

## ☕ Support

[![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/verivi)

---

<div align="center">
  <p>© 2025 VERAS. All rights reserved.</p>
</div>

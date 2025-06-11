# LGBTMythOrFact 🏳️‍🌈

An interactive quiz game designed to educate and challenge players' knowledge about LGBTQ+ topics. Test your understanding, learn new facts, and compete with others on the leaderboard!

## 🎮 Features

- **Interactive Quiz Game**: Answer true/false questions about LGBTQ+ topics
- **Multiple Difficulty Levels**: Easy, Hard, or Expert
- **Category Selection**: Focus on specific areas of LGBTQ+ knowledge
- **User Authentication**: Sign up and track your progress
- **Leaderboard**: Compete with other players globally
- **Responsive Design**: Play on desktop, tablet, or mobile
- **Real-time Scoring**: Track your score and streaks
- **Progress Tracking**: See your highest scores and improvement

## 🚀 Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn

### Installation

1. Clone the repository:
```bash
git clone git@github.com:Veras-D/MythOrFactLGBTQIA.git
cd MythOrFactLGBTQIA
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Open your browser and navigate to `http://localhost:5173`

## 🛠️ Tech Stack

- **Frontend**: React 18 + TypeScript
- **Styling**: Tailwind CSS with custom design system
- **UI Components**: Radix UI + shadcn/ui
- **Build Tool**: Vite
- **Authentication**: Supabase Auth
- **State Management**: React Context API
- **Icons**: Lucide React
- **Routing**: React Router DOM

## 📁 Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── ui/             # shadcn/ui components
│   ├── AuthForm.tsx    # Authentication form
│   ├── Game.tsx        # Main game component
│   ├── Layout.tsx      # App layout with navigation
│   ├── Leaderboard.tsx # Leaderboard display
│   └── EmailConfirmation.tsx # Email verification
├── contexts/           # React contexts
│   └── AuthContext.tsx # Authentication state
├── data/              # Static data
│   └── statements.ts  # Quiz questions and answers
├── hooks/             # Custom React hooks
├── lib/               # Utility functions
├── pages/             # Page components
└── main.tsx           # App entry point
```

## 🎯 How to Play

1. **Sign Up/Login**: Create an account to track your progress
2. **Answer Questions**: Read statements and decide if they're Myth or Fact
3. **Track Your Score**: See your current score and streak
4. **Check Leaderboard**: Compare your performance with other players

## 🎨 Design System

The app uses a comprehensive design system with:
- **Semantic color tokens**: Defined in `index.css` and `tailwind.config.ts`
- **Pride theme**: Rainbow gradients and LGBTQ+ inspired colors
- **Glassmorphism**: Modern glass-like UI elements
- **Responsive design**: Mobile-first approach
- **Accessibility**: Proper contrast and keyboard navigation

## 🔧 Development

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

### Adding New Questions

1. Edit `src/data/statements.ts`
2. Add new statement objects with:
   - `id`: Unique identifier
   - `statement`: The question text
   - `isTrue`: Boolean for correct answer
   - `category`: Topic category
   - `difficulty`: easy | hard | expert
   - `explanation`: Educational explanation

### Customizing Design

- Modify `src/index.css` for global styles and color tokens
- Update `tailwind.config.ts` for theme configuration
- Components use semantic tokens, not direct colors


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

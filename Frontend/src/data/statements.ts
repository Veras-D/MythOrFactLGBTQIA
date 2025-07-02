export interface Statement {
  id: number;
  statement: string;
  explanation: string;
  difficulty: number;
  category: string;
  fact: boolean;
}

const API_BASE_URL = process.env.VITE_API_URL;
const API_URL = `${API_BASE_URL}/api/statements`;

export const fetchStatements = async (): Promise<Statement[]> => {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data: Statement[] = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching statements:", error);
    return [];
  }
};

export const getRandomStatement = async (usedStatements: number[] = []): Promise<Statement | null> => {
  const statements = await fetchStatements();
  const availableStatements = statements.filter(s => !usedStatements.includes(s.id));
  if (availableStatements.length === 0) return null;

  const randomIndex = Math.floor(Math.random() * availableStatements.length);
  return availableStatements[randomIndex];
};

export const getDifficultyColor = (difficulty: number): string => {
  switch (difficulty) {
    case 1: return "bg-green-500 hover:bg-green-600";
    case 2: return "bg-yellow-500 hover:bg-yellow-600";
    case 3: return "bg-orange-500 hover:bg-orange-600";
    case 4: return "bg-red-500 hover:bg-red-600";
    default: return "bg-gray-500 hover:bg-gray-600";
  }
};

export const getCategoryColor = (category: string): string => {
  switch (category.toLowerCase()) {
    case 'symbols': return "bg-purple-500 hover:bg-purple-600";
    case 'history': return "bg-blue-500 hover:bg-blue-600";
    case 'identity': return "bg-pink-500 hover:bg-pink-600";
    case 'legal': return "bg-indigo-500 hover:bg-indigo-600";
    case 'terminology': return "bg-teal-500 hover:bg-teal-600";
    case 'health': return "bg-emerald-500 hover:bg-emerald-600";
    case 'politics': return "bg-cyan-500 hover:bg-cyan-600";
    default: return "bg-gray-500 hover:bg-gray-600";
  }
};

export const getDifficultyLabel = (difficulty: number): string => {
  switch (difficulty) {
    case 1: return "Easy";
    case 2: return "Medium";
    case 3: return "Hard";
    case 4: return "Expert";
    default: return "Unknown";
  }
};

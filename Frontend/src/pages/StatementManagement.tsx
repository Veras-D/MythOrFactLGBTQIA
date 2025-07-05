import React, { useState, useEffect } from 'react';
import api from '../lib/api';
import { Statement } from '../components/Game';
import { Card } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Label } from '../components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../components/ui/select';
import { toast } from 'sonner';
import { Trash2, Edit, PlusCircle, Save, XCircle } from 'lucide-react';

const StatementManagement: React.FC = () => {
  const [statements, setStatements] = useState<Statement[]>([]);
  const [editingStatement, setEditingStatement] = useState<Statement | null>(null);
  const [newStatementData, setNewStatementData] = useState<Omit<Statement, 'id' | 'fact' | 'isFact'>>({
    statement: '',
    explanation: '',
    difficulty: 1,
    category: '',
  });
  const [isFact, setIsFact] = useState<boolean>(false);

  useEffect(() => {
    fetchStatements();
  }, []);

  const fetchStatements = async () => {
    try {
      const response = await api.get<Statement[]>('/statements');
      setStatements(response.data);
    } catch (error) {
      console.error("Failed to fetch statements:", error);
      toast.error("Failed to load statements.");
    }
  };

  const handleSaveStatement = async () => {
    try {
      if (editingStatement) {
        await api.put(`/statements/${editingStatement.id}`, { ...editingStatement, isFact: editingStatement.fact });
        toast.success('Statement updated successfully!');
      } else {
        await api.post('/statements', { ...newStatementData, isFact });
        toast.success('Statement created successfully!');
      }
      setEditingStatement(null);
      setNewStatementData({
        statement: '',
        explanation: '',
        difficulty: 1,
        category: '',
      });
      setIsFact(false);
      fetchStatements();
    } catch (error) {
      console.error("Failed to save statement:", error);
      toast.error('Failed to save statement.');
    }
  };

  const handleDeleteStatement = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this statement?')) {
      try {
        await api.delete(`/statements/${id}`);
        toast.success('Statement deleted successfully!');
        fetchStatements();
      } catch (error) {
        console.error("Failed to delete statement:", error);
        toast.error('Failed to delete statement.');
      }
    }
  };

  const handleEditClick = (statement: Statement) => {
    setEditingStatement(statement);
    setNewStatementData({
      statement: statement.statement,
      explanation: statement.explanation,
      difficulty: statement.difficulty,
      category: statement.category,
    });
    setIsFact(statement.fact);
  };

  const handleCancelEdit = () => {
    setEditingStatement(null);
    setNewStatementData({
      statement: '',
      explanation: '',
      difficulty: 1,
      category: '',
    });
    setIsFact(false);
  };

  return (
    <div className="min-h-screen light-bg p-4">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-4xl font-bold text-gray-900 mb-8 text-center">
          Statement Management
        </h1>

        {/* Add/Edit Statement Form */}
        <Card className="glass-card border-0 p-6 mb-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">
            {editingStatement ? 'Edit Statement' : 'Add New Statement'}
          </h2>
          <div className="grid grid-cols-1 gap-4">
            <div>
              <Label htmlFor="statement">Statement</Label>
              <Textarea
                id="statement"
                value={editingStatement ? editingStatement.statement : newStatementData.statement}
                onChange={(e) =>
                  editingStatement
                    ? setEditingStatement({ ...editingStatement, statement: e.target.value })
                    : setNewStatementData({ ...newStatementData, statement: e.target.value })
                }
                placeholder="Enter statement text" rows={3}
              />
            </div>
            <div>
              <Label htmlFor="explanation">Explanation</Label>
              <Textarea
                id="explanation"
                value={editingStatement ? editingStatement.explanation : newStatementData.explanation}
                onChange={(e) =>
                  editingStatement
                    ? setEditingStatement({ ...editingStatement, explanation: e.target.value })
                    : setNewStatementData({ ...newStatementData, explanation: e.target.value })
                }
                placeholder="Enter explanation" rows={3}
              />
            </div>
            <div>
              <Label htmlFor="difficulty">Difficulty</Label>
              <Select
                value={String(editingStatement ? editingStatement.difficulty : newStatementData.difficulty)}
                onValueChange={(value) =>
                  editingStatement
                    ? setEditingStatement({ ...editingStatement, difficulty: Number(value) })
                    : setNewStatementData({ ...newStatementData, difficulty: Number(value) })
                }
              >
                <SelectTrigger>
                  <SelectValue placeholder="Select Difficulty" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="1">1 (Easy)</SelectItem>
                  <SelectItem value="2">2 (Medium)</SelectItem>
                  <SelectItem value="3">3 (Hard)</SelectItem>
                  <SelectItem value="4">4 (Expert)</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <div>
              <Label htmlFor="category">Category</Label>
              <Input
                id="category"
                value={editingStatement ? editingStatement.category : newStatementData.category}
                onChange={(e) =>
                  editingStatement
                    ? setEditingStatement({ ...editingStatement, category: e.target.value })
                    : setNewStatementData({ ...newStatementData, category: e.target.value })
                }
                placeholder="Enter category (e.g., History, Identity)"
              />
            </div>
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                id="isFact"
                checked={editingStatement ? editingStatement.fact : isFact}
                onChange={(e) =>
                  editingStatement
                    ? setEditingStatement({ ...editingStatement, fact: e.target.checked })
                    : setIsFact(e.target.checked)
                }
                className="form-checkbox h-5 w-5 text-blue-600"
              />
              <Label htmlFor="isFact">Is Fact?</Label>
            </div>
            <div className="flex space-x-4 mt-4">
              <Button onClick={handleSaveStatement} className="pride-gradient flex-1">
                <Save className="w-4 h-4 mr-2" />
                {editingStatement ? 'Update Statement' : 'Add Statement'}
              </Button>
              {editingStatement && (
                <Button onClick={handleCancelEdit} variant="outline" className="flex-1">
                  <XCircle className="w-4 h-4 mr-2" />
                  Cancel
                </Button>
              )}
            </div>
          </div>
        </Card>

        {/* Statements List */}
        <Card className="glass-card border-0 p-6">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Existing Statements</h2>
          <div className="space-y-4">
            {statements.length === 0 ? (
              <p className="text-gray-600 text-center">No statements found.</p>
            ) : (
              statements.map((statement) => (
                <div
                  key={statement.id}
                  className="flex items-center justify-between p-4 bg-gray-50/70 rounded-lg shadow-sm"
                >
                  <div>
                    <p className="font-semibold text-gray-900">{statement.statement}</p>
                    <p className="text-sm text-gray-600">Category: {statement.category} | Difficulty: {statement.difficulty} | Fact: {statement.fact ? 'Yes' : 'No'}</p>
                  </div>
                  <div className="flex space-x-2">
                    <Button variant="outline" size="icon" onClick={() => handleEditClick(statement)}>
                      <Edit className="w-4 h-4" />
                    </Button>
                    <Button variant="outline" size="icon" onClick={() => handleDeleteStatement(statement.id)}>
                      <Trash2 className="w-4 h-4 text-red-500" />
                    </Button>
                  </div>
                </div>
              ))
            )}
          </div>
        </Card>
      </div>
    </div>
  );
};

export default StatementManagement;

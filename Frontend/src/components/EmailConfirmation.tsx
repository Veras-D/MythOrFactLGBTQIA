import React from 'react';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { Mail, ArrowLeft } from 'lucide-react';

interface EmailConfirmationProps {
  email: string;
  onBack: () => void;
}

const EmailConfirmation: React.FC<EmailConfirmationProps> = ({ email, onBack }) => {
  return (
    <div 
      className="fixed inset-0 z-50 flex items-center justify-center light-bg bg-black/50"
      onClick={onBack}
    >
      <Card 
        className="w-full max-w-md glass-card border-0 p-8 text-center mx-4"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="mb-6">
          <Mail className="w-16 h-16 text-blue-500 mx-auto mb-4 float" />
          <h2 className="text-2xl font-bold text-gray-900 mb-2">
            Confirme seu Email
          </h2>
          <p className="text-gray-700">
            Enviamos um link de confirmação para:
          </p>
          <p className="text-blue-600 font-semibold mt-2 break-all">
            {email}
          </p>
        </div>

        <div className="space-y-4 text-gray-600">
          <p className="text-sm">
            Clique no link do email para confirmar sua conta e fazer login.
          </p>
          <p className="text-sm">
            Não recebeu o email? Verifique sua caixa de spam.
          </p>
        </div>

        <div className="mt-6">
          <Button
            onClick={onBack}
            variant="outline"
            className="w-full font-semibold hover:bg-gray-50"
          >
            <ArrowLeft className="w-4 h-4 mr-2" />
            Voltar ao Login
          </Button>
        </div>
      </Card>
    </div>
  );
};

export default EmailConfirmation;

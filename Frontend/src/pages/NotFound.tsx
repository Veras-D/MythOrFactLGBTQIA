import { useLocation } from "react-router-dom";
import { useEffect } from "react";

const NotFound = () => {
  const location = useLocation();

  useEffect(() => {
    console.error(
      "404 Error: User attempted to access non-existent route:",
      location.pathname
    );
  }, [location.pathname]);

  return (
    <div className="min-h-screen flex items-center justify-center light-bg">
      <div className="text-center p-8 glass-card rounded-lg shadow-lg">
        <h1 className="text-6xl font-bold text-rainbow mb-4">404</h1>
        <p className="text-2xl text-gray-700 mb-8">Oops! Page not found</p>
        <a href="/" className="rainbow-border text-gray-700 font-semibold btn-hover px-8 py-3 rounded-lg text-lg">
          Return to Home
        </a>
      </div>
    </div>
  );
};

export default NotFound;
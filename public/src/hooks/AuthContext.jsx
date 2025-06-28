import React, { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [role, setRole] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');

    async function validateToken() {
      if (!token) {
        setIsAuthenticated(false);
        setRole(null);
        setIsLoading(false);
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/validate-token', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        });

        if (response.ok) {
          const decoded = parseJwt(token);
          setIsAuthenticated(true);
          setRole(decoded?.role || null);
        } else {
          localStorage.removeItem('token');
          setIsAuthenticated(false);
          setRole(null);
        }
      } catch (error) {
        console.error('Ошибка проверки токена:', error);
        localStorage.removeItem('token');
        setIsAuthenticated(false);
        setRole(null);
      }

      setIsLoading(false);
    }

    validateToken();
  }, []);

  const login = (token) => {
    localStorage.setItem('token', token);
    setIsAuthenticated(true);
    const decoded = parseJwt(token);
    setRole(decoded?.role || null);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isLoading, role, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);

function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = decodeURIComponent(atob(base64Url).split('').map(c =>
      '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    ).join(''));
    return JSON.parse(base64);
  } catch (e) {
    return null;
  }
}

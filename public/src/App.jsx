import React from 'react';
    import { Routes, Route, Navigate } from 'react-router-dom';
    import styled from 'styled-components';
    import HomePage from './pages/HomePage';
    import LoginPage from './pages/LoginPage';
    import RegisterPage from './pages/RegisterPage';
    import { useAuth } from './hooks/useAuth';

    const AppContainer = styled.div`
      font-family: sans-serif;
      background-color: #f0f0f0;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 20px;
    `;

    function App() {
      const { isLoggedIn } = useAuth();

      return (
        <AppContainer>
          <Routes>
            <Route path="/" element={isLoggedIn ? <HomePage /> : <Navigate to="/login" />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </AppContainer>
      );
    }

    export default App;

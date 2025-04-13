import React, { useState } from 'react';
    import styled from 'styled-components';
    import { useAuth } from '../hooks/useAuth';
    import { useNavigate } from 'react-router-dom';

    const LoginPageContainer = styled.div`
      background-color: #f5f5f5;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      width: 80%;
      max-width: 400px;
      margin: 20px auto;
      text-align: center;
    `;

    const Header = styled.h2`
      color: #333;
      margin-bottom: 20px;
    `;

    const Form = styled.form`
      display: flex;
      flex-direction: column;
      align-items: center;
    `;

    const Input = styled.input`
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 1rem;
    `;

    const Button = styled.button`
      background-color: #4caf50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      transition: background-color 0.2s ease;

      &:hover {
        background-color: #3e8e41;
      }
    `;

    const Link = styled.a`
      color: #1976d2;
      text-decoration: none;
      margin-top: 15px;

      &:hover {
        text-decoration: underline;
      }
    `;

    const ErrorMessage = styled.p`
      color: red;
      margin-top: 10px;
    `;

    function LoginPage() {
      const [username, setUsername] = useState('');
      const [password, setPassword] = useState('');
      const [error, setError] = useState('');
      const { login } = useAuth();
      const navigate = useNavigate();

      const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        // Simulate an API call
        try {
          // Replace with your actual API endpoint
          const response = await new Promise((resolve) => {
            setTimeout(() => {
              if (username === 'user' && password === 'password') {
                resolve({ token: 'mock-token' });
              } else {
                resolve({ error: 'Invalid credentials' });
              }
            }, 500); // Simulate network delay
          });

          if (response.token) {
            login(response.token);
            navigate('/');
          } else {
            setError(response.error || 'Login failed');
          }
        } catch (err) {
          setError('An unexpected error occurred.');
        }
      };

      return (
        <LoginPageContainer>
          <Header>Login</Header>
          <Form onSubmit={handleSubmit}>
            <Input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <Input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <Button type="submit">Login</Button>
            {error && <ErrorMessage>{error}</ErrorMessage>}
            <Link href="/register">Don't have an account? Register</Link>
          </Form>
        </LoginPageContainer>
      );
    }

    export default LoginPage;

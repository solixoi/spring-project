import React, { useState } from 'react';
    import { useNavigate } from 'react-router-dom';
    import { useAuth } from '../../hooks/AuthContext.jsx';
    import '../../styles/main_page/Login.css';

    function Login() {
      const [username, setUsername] = useState('');
      const [password, setPassword] = useState('');
      const [error, setError] = useState('');
      const navigate = useNavigate();
      const { login } = useAuth();

      const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
          const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
          });

          const data = await response.text();

          if (!response.ok) {
            throw new Error(data || 'Login failed');
          }

          login(data);
          navigate('/courses');
        } catch (err) {
          setError(err.message);
        }
      };

      return (
        <div className="login-container">
          <h2>Login</h2>
          {error && <p className="error-message">{error}</p>}
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit">Login</button>
          </form>
        </div>
      );
    }

    export default Login;
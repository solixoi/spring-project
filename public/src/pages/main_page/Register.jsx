import React, { useState } from 'react';
    import { useNavigate } from 'react-router-dom';
    import '../../styles/main_page/Register.css';

    function Register() {
      const [email, setEmail] = useState('');
      const [password, setPassword] = useState('');
      const [username, setUsername] = useState('');
      const [confirmPassword, setConfirmPassword] = useState('');
      const [error, setError] = useState('');
      const navigate = useNavigate();

      const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
      
        if (password !== confirmPassword) {
          setError('Passwords do not match');
          return;
        }
      
        try {
          const response = await fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, email, password }),
          });
      
          const resultText = await response.text();
      
          if (!response.ok || resultText !== "Success register!") {
            throw new Error(resultText || 'Registration failed');
          }
      
          alert('Registration successful! Please login.');
          navigate('/login');
        } catch (err) {
          setError(err.message);
        }
      };
      

      return (
        <div className="register-container">
          <h2>Register</h2>
          {error && <p className="error-message">{error}</p>}
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
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
            <div className="form-group">
              <label htmlFor="confirmPassword">Confirm Password:</label>
              <input
                type="password"
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit">Register</button>
          </form>
        </div>
      );
    }

    export default Register;

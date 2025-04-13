import React from 'react';
    import styled from 'styled-components';
    import { useAuth } from '../hooks/useAuth';
    import { useNavigate } from 'react-router-dom';

    const HomePageContainer = styled.div`
      background-color: #e0f7fa;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      text-align: center;
      width: 80%;
      max-width: 800px;
    `;

    const Header = styled.h1`
      color: #263238;
      margin-bottom: 20px;
    `;

    const Section = styled.section`
      margin-bottom: 20px;
      padding: 15px;
      border-radius: 8px;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    `;

    const SectionTitle = styled.h2`
      color: #009688;
      margin-bottom: 10px;
    `;

    const Button = styled.button`
      background-color: #009688;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      transition: background-color 0.2s ease;

      &:hover {
        background-color: #00796b;
      }
    `;

    function HomePage() {
      const { logout } = useAuth();
      const navigate = useNavigate();

      const handleLogout = () => {
        logout();
        navigate('/login');
      };

      return (
        <HomePageContainer>
          <Header>Welcome to the E-learning Platform!</Header>

          <Section>
            <SectionTitle>Dashboard</SectionTitle>
            <p>Here you can see your progress, upcoming lessons, and announcements.</p>
          </Section>

          <Section>
            <SectionTitle>Courses</SectionTitle>
            <p>Browse and enroll in courses relevant to your grade level.</p>
          </Section>

          <Section>
            <SectionTitle>Quizzes & Games</SectionTitle>
            <p>Test your knowledge with interactive quizzes and educational games.</p>
          </Section>

          <Button onClick={handleLogout}>Logout</Button>
        </HomePageContainer>
      );
    }

    export default HomePage;

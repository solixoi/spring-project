import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/main_page/HomePage.jsx';
import CourseList from './pages/courses/CourseList.jsx';
import Login from './pages/main_page/Login.jsx';
import Register from './pages/main_page/Register.jsx';
import CourseDetails from './pages/courses/CoursePage.jsx';
import SectionPage from './pages/courses/SectionPage.jsx'
import PracticePage from './pages/courses/PracticePage.jsx'
import UserProfilePage from './pages/user_profile/UserProfilePage.jsx'
import TeacherAddCourse from './pages/teacher/TeacherAddCourse.jsx'
import ControlUsersPage from './pages/admin/ControlUsersPage.jsx'
import { useAuth } from './hooks/AuthContext.jsx';
import './App.css';

function App() {
  const { isAuthenticated, isLoading, role } = useAuth();

  if (isLoading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>E-learning Platform</h1>
        <nav>
          <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/courses">Courses</a></li>
            {isAuthenticated ? (
              <>
                <li><a href="/user/profile">Profile</a></li>
                <li><a href="/logout">Logout</a></li>
              </>
            ) : (
              <>
                <li><a href="/login">Login</a></li>
                <li><a href="/register">Register</a></li>
              </>
            )}
          </ul>
        </nav>
      </header>

      <main className="app-main">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/courses" element={isAuthenticated ? <CourseList /> : <Navigate to="/login" />} />
          <Route path="/courses/:id" element={isAuthenticated ? <CourseDetails /> : <Navigate to="/login" />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="*" element={<p>404 Not Found</p>} />
          <Route path='/section' element={isAuthenticated ? <SectionPage /> : <Navigate to="/login" />} />
          <Route path='/practice' element={isAuthenticated ? <PracticePage /> : <Navigate to="/login" />} />
          <Route path='/user/profile' element={isAuthenticated ? <UserProfilePage /> : <Navigate to="/login" />} />
          <Route path='/teacher/add/course' element={isAuthenticated && role != "STUDENT" ? <TeacherAddCourse /> : <Navigate to="/" />} />
          <Route path='/admin/info/allusers' element={isAuthenticated && role === "ADMIN" ? <ControlUsersPage /> : <Navigate to="/" />} />
        </Routes>
      </main>

      <footer className="app-footer">
        <p>&copy; 2025 E-learning Platform</p>
      </footer>
    </div>
  );
}

function Logout() {
  const { logout } = useAuth();
  logout();
  return <Navigate to="/" />;
}

export default App;

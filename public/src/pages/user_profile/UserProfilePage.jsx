import React, { useEffect, useState } from 'react';
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell
} from 'recharts';
import '../../styles/user_profile/UserProfilePage.css';

export default function UserProfilePage() {
  const [userData, setUserData] = useState(null);
  const [menuOpen, setMenuOpen] = useState(false);


  const scrollToSection = (id) => {
    const el = document.getElementById(id);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' });
      setMenuOpen(false);
    }
  };


  useEffect(() => {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/api/users/profile', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => res.json())
      .then(setUserData)
      .catch(console.error);
  }, []);

  if (!userData) return <div className="loading">Загрузка...</div>;

  const { username, email, role, courses = [], section_progress = [] } = userData;

  const isTeacher = role === 'TEACHER' || role === 'ADMIN';
  const isAdmin = role === 'ADMIN';

  const chartData = courses.map(course => ({
    name: course.title,
    percent: course.score_percent
  }));

  const courseSectionsMap = courses.map(course => ({
    courseTitle: course.title,
    courseId: course.id,
    sections: section_progress
      .filter(sec => sec.course_id === course.id)
      .sort((a, b) => a.section_number - b.section_number)
  }));

  return (
    <div className="profile-container">
      {!menuOpen && (
        <div className="menu-icon" onClick={() => setMenuOpen(true)}>
          ☰
        </div>
      )}

      <div className={`side-menu ${menuOpen ? 'open' : 'closed'}`}>
        <div className="menu-header">
          <h2>Меню</h2>
          <span className="close-btn" onClick={() => setMenuOpen(false)}>
            <div className="close-line close-line1" />
            <div className="close-line close-line2" />
          </span>
        </div>

        <nav className="menu-nav">
          <div className="menu-group">  
            <h4>Управление аккаунтом </h4>
            <a href='/edit_account'> Изменить данные аккаунта</a>
            <a href='/courses'> Прохождение курсов </a>
            <a href='#!' onClick={() => scrollToSection('courses')}> Мои курсы</a>
            <a href='#!' onClick={() => scrollToSection('section_results')}> Мои результаты прохождения курсов </a>
          </div>
          <div className="menu-group">
          {isTeacher &&
              <>
                <h4>Учительский функционал</h4>
                <a href="/teacher/add/course"> Добавить курс</a>
                <a href='#'> Обновление курса </a>
                <a href='#'> Удаление курса </a>
                <a href='#'> Анализ успеваемости учащихся </a>
              </>
            }
          </div>
          {isAdmin && (
            <div className="menu-group">
              <h4>Админ панель</h4>
              <a href="/admin/info/allusers"> Управление пользователями</a>
              <a href='#'> Формирование отчетности по системе</a>
              <a href='#'> Мониторинг активности пользователей </a>
            </div>
          )}
        </nav>
      </div>

      {menuOpen && <div className="menu-overlay" onClick={() => setMenuOpen(false)} />}

      <div className="profile-header">
        <div>
          <h1 className="username">{username}</h1>
          <p className="email">{email}</p>
        </div>
        <div className="role-badge">{role}</div>
      </div>

      <div className="profile-section" id="courses">
        <h2>Общая статистика по курсам</h2>
        <div className="chart-wrapper">
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={chartData}>
              <XAxis dataKey="name" />
              <YAxis domain={[0, 100]} />
              <Tooltip formatter={val => `${val}%`} />
              <Bar dataKey="percent" animationDuration={1200}>
                {chartData.map((entry, idx) => (
                  <Cell key={idx} fill="#4caf50" />
                ))}
              </Bar>
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      <div className="profile-section" id='section_results'>
        <h2>Детализация по курсам</h2>
        {courseSectionsMap.map(course => (
          <div key={course.courseId} className="course-block">
            <h3>{course.courseTitle}</h3>
            <ul className="section-list">
              {course.sections.map(sec => (
                <li key={sec.id} className="section-item">
                  <div className="section-info">
                    <strong>Раздел {sec.section_number}</strong>
                    <p>Результат: {sec.score_percent}%</p>
                    <p>Попыток: {sec.attempts}</p>
                    <p>Время завершения: {new Date(sec.ended_at).toLocaleString()}</p>
                    <p>Длительность: {sec.completed_at}</p>
                  </div>
                  {sec.score_percent > 40 ?
                    <div className="section-status passed">  Пройдено </div>
                    :
                    <div className="section-status lose"> Провалено </div>
                  }

                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

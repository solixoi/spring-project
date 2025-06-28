import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/courses/CourseList.css';

function CourseList() {
  const [courses, setCourses] = useState([]);
  const [filteredCourses, setFilteredCourses] = useState([]);
  const [classFilter, setClassFilter] = useState('Все');
  const [subjectFilter, setSubjectFilter] = useState('Все');

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/api/courses/getAll', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
      .then(res => {
        if (!res.ok) {
          throw new Error(`Ошибка: ${res.status}`);
        }
        return res.json();
      })
      .then(data => {
        setCourses(data);
        setFilteredCourses(data);
      })
      .catch(err => console.error('Ошибка загрузки курсов:', err));
  }, []);

  useEffect(() => {
    let filtered = [...courses];

    if (classFilter !== 'Все') {
      filtered = filtered.filter(course => course.classNumber === Number(classFilter));
    }
    if (subjectFilter !== 'Все') {
      filtered = filtered.filter(course => course.subject === subjectFilter);
    }

    setFilteredCourses(filtered);
  }, [classFilter, subjectFilter, courses]);

  const classes = Array.from(new Set(courses.map(c => c.classNumber))).sort((a, b) => a - b);
  const subjects = classFilter === 'Все'
    ? Array.from(new Set(courses.map(c => c.subject)))
    : Array.from(new Set(courses.filter(c => c.classNumber === Number(classFilter)).map(c => c.subject)));

  return (
    <div className="course-list-container">
      <div className="filters">
        <select value={classFilter} onChange={e => { setClassFilter(e.target.value); setSubjectFilter('Все'); }}>
          <option value="Все">Все классы</option>
          {classes.map((cls, idx) => (
            <option key={idx} value={cls}>{cls} класс</option>
          ))}
        </select>

        <select value={subjectFilter} onChange={e => setSubjectFilter(e.target.value)}>
          <option value="Все">Все предметы</option>
          {subjects.map((subject, idx) => (
            <option key={idx} value={subject}>{subject}</option>
          ))}
        </select>
      </div>

      <div className="course-grid">
        {filteredCourses.map(course => (
          <div
            key={course.id}
            className="course-card"
            onClick={() => navigate(`/courses/${course.id}`)}
          >
            <img src={course.imagePath} alt={course.title} className="course-image" />
            <div className="course-details">
              <h2>{course.title}</h2>
              <p>{course.description}</p>
              
              {course.scorePercent !== null ? (
                <div className="progress-bar" style={{ "--progress-width": `${course.scorePercent}%` }}>
                  <div className="progress-bar-fill"></div>
                </div>
              ) : (
                <div className="course-not-started">
                  Курс ещё не начат
                </div>
              )}




              <div
                className="course-button"
                onClick={(e) => {
                  e.stopPropagation();
                  navigate(`/courses/${course.id}`);
                }}
              >
                Перейти к курсу
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CourseList;

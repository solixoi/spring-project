import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import '../../styles/courses/CoursePage.css';

function CoursePage() {
    const { id } = useParams();
    const [sections, setSections] = useState([]);
    const [progressWidths, setProgressWidths] = useState({});

    useEffect(() => {
        const token = localStorage.getItem('token');

        fetch(`http://localhost:8080/api/courses/about/course/${id}`, {
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
                setSections(data);
                const initialProgress = {};
                data.forEach(section => {
                    initialProgress[section.id] = 0;
                });
                setProgressWidths(initialProgress);

                setTimeout(() => {
                    const animatedProgress = {};
                    data.forEach(section => {
                        animatedProgress[section.id] = section.totalProgress || 0;
                    });
                    setProgressWidths(animatedProgress);
                }, 100);
            })
            .catch(err => console.error('Ошибка загрузки курсов:', err));
    }, [id]);

    return (
        <div className="course-page">
            <h1 className="course-title">Разделы курса</h1>

            <div className="sections-grid">
                {sections.map(section => (
                    <Link
                        key={section.id}
                        to={`/section?courseId=${id}&sectionId=${section.id}`}
                        className="section-card"
                    >
                        <div className="section-content">
                            <h2 className="section-title">{section.title}</h2>

                            <div className="progress-container-section">
                                <div
                                    className="progress-bar-section"
                                    style={{ width: `${progressWidths[section.id]}%` }}
                                ></div>
                            </div>

                            <div
                                className={`progress-text-section ${section.totalProgress >= 0 && section.totalProgress !== null ? (section.totalProgress < 40 ? 'progress-low' : 'progress-started') : 'progress-not-started'}`}
                            >
                                {section.totalProgress === null ? 'Вы не проходили этот раздел' : `Ваш максимальный результат ${section.totalProgress}%`}
                            </div>

                            <span className="section-button">Перейти</span>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default CoursePage;

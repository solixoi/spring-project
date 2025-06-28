import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/teacher/TeacherAddCourse.css';

function TeacherAddCourse() {
    const [course, setCourse] = useState({
        title: '',
        description: '',
        subject: '',
        classNumber: 1,
        imagePath: '',
        sections: []
    });

    const [imageFile, setImageFile] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImageFile(file);
            setPreviewUrl(URL.createObjectURL(file));
        }
    };

    const handleRemoveSection = (secIdx) => {
        const updatedSections = course.sections.filter((_, index) => index !== secIdx);
        setCourse({ ...course, sections: updatedSections });
    };


    const handleRemoveImage = () => {
        setImageFile(null);
        setPreviewUrl(null);
        document.getElementById('fileInput').value = '';
    };

    const handleAddSection = () => {
        setCourse({
            ...course,
            sections: [
                ...course.sections,
                {
                    title: '',
                    theoryText: '',
                    videoUrl: '',
                    test: null
                }
            ]
        });
    };

    const handleSectionChange = (idx, field, value) => {
        const newSections = [...course.sections];
        newSections[idx][field] = value;
        setCourse({ ...course, sections: newSections });
    };

    const handleAddTest = (secIdx) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test = {
            title: '',
            questions: []
        };
        setCourse({ ...course, sections: updatedSections });
    };

    const handleRemoveTest = (secIdx) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test = null;
        setCourse({ ...course, sections: updatedSections });
    };

    const handleTestChange = (secIdx, field, value) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test[field] = value;
        setCourse({ ...course, sections: updatedSections });
    };

    const handleAddQuestion = (secIdx) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test.questions.push({
            questionText: '',
            answerOption: 'single',
            answers: []
        });
        setCourse({ ...course, sections: updatedSections });
    };



    const handleRemoveQuestion = (secIdx, qIdx) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test.questions.splice(qIdx, 1);
        setCourse({ ...course, sections: updatedSections });
    };

    const handleQuestionChange = (secIdx, qIdx, field, value) => {
        const updatedSections = [...course.sections];
        const question = updatedSections[secIdx].test.questions[qIdx];

        question[field] = value;

        if (field === 'answerOption') {
            question.answers = [];
        }

        setCourse({ ...course, sections: updatedSections });
    };

    const handleFieldAnswerChange = (secIdx, qIdx, value) => {
        const updatedSections = [...course.sections];
        const question = updatedSections[secIdx].test.questions[qIdx];

        question.answers = [{
            answerText: value,
            isCorrect: true
        }];

        setCourse({ ...course, sections: updatedSections });
    };



    const handleAddAnswer = (secIdx, qIdx) => {
        const updatedSections = [...course.sections];
        updatedSections[secIdx].test.questions[qIdx].answers.push({
            answerText: '',
            isCorrect: false
        });
        setCourse({ ...course, sections: updatedSections });
    };

    const handleAnswerChange = (secIdx, qIdx, aIdx, field, value) => {
        const updatedSections = [...course.sections];
        const question = updatedSections[secIdx].test.questions[qIdx];

        if (field === 'isCorrect' && question.answerOption === 'single') {
            question.answers.forEach((_, i) => {
                question.answers[i].isCorrect = i === aIdx;
            });
        } else {
            question.answers[aIdx][field] = value;
        }

        setCourse({ ...course, sections: updatedSections });
    };

    const handleSubmit = async () => {
        let uploadedPath = '';
        const token = localStorage.getItem('token');

        if (imageFile) {
            const formData = new FormData();
            formData.append('file', imageFile);

            const uploadRes = await fetch('http://localhost:8080/api/teacher/upload', {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${token}`
                },
                body: formData
            });

            if (uploadRes.ok) {
                const data = await uploadRes.json();
                uploadedPath = data.path;
            } else {
                alert('Ошибка при загрузке изображения');
                return;
            }
        }

        const finalCourse = {
            ...course,
            imagePath: uploadedPath,
            sections: course.sections.map((section, idx) => ({
                ...section,
                sectionNumber: idx + 1,
                theoryText: parseTheoryText(section.theoryText)
            }))
        };

        console.log(finalCourse);

        const res = await fetch('http://localhost:8080/api/teacher/addCourses', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(finalCourse)
        });

        if (res.ok) {
            alert('Курс успешно добавлен!');
        } else {
            alert('Ошибка при добавлении курса');
        }
    };

    const parseTheoryText = (text) => {
        if (!text) return '';

        const lines = text.split('\n');
        let html = '';
        let currentList = [];

        const flushList = () => {
            if (currentList.length) {
                html += '<ul>' + currentList.map(item => `<li>${item}</li>`).join('') + '</ul>';
                currentList = [];
            }
        };

        for (let line of lines) {
            line = line.trim();

            if (!line) {
                flushList();
                continue;
            }

            if (line.startsWith('$$') && line.endsWith('$$')) {
                flushList();
                const content = line.slice(2, -2).trim();
                html += `<p>${content}</p>`;
            }
            else if (line.startsWith('&&') && line.endsWith('&&')) {
                const item = line.slice(2, -2).trim();
                currentList.push(item);
            }
            else {
                flushList();
                html += `<p>${line}</p>`;
            }
        }

        flushList();

        return html;
    };


    return (
        <div className="add-course-container">
            <div className="top-buttons">
                <Link to={`/user/profile`} className="back-button">← Вернуться в профиль</Link>
            </div>

            <h1>Добавить новый курс</h1>
            <input
                type="text"
                placeholder="Название курса"
                value={course.title}
                onChange={e => setCourse({ ...course, title: e.target.value })}
                required
            />
            <textarea
                placeholder="Описание"
                value={course.description}
                onChange={e => setCourse({ ...course, description: e.target.value })}
                required
            />
            <input
                type="text"
                placeholder="Предмет"
                value={course.subject}
                onChange={e => setCourse({ ...course, subject: e.target.value })}
                required
            />

            <div className="class-number">
                <small>Номер класса (от 1 до 11)</small>
                <input
                    type="number"
                    min="1"
                    max="11"
                    value={course.classNumber}
                    onChange={e => setCourse({ ...course, classNumber: Math.min(11, Math.max(1, e.target.value)) })}
                    required
                />
            </div>

            <label className="file-upload">
                <input
                    type="file"
                    accept="image/*"
                    id="fileInput"
                    onChange={handleImageChange}
                    required
                />
                <span>📁 Выбрать изображение</span>
            </label>

            {previewUrl && (
                <div className="image-preview">
                    <img src={previewUrl} alt="Превью" />
                    <button className="remove-image" onClick={handleRemoveImage}>Удалить изображение</button>
                    <div className="image-file-name">{imageFile?.name}</div>
                </div>
            )}

            <hr />
            <h2>Секции курса</h2>

            {course.sections.map((section, secIdx) => (
                <div key={secIdx} className="section-block">
                    <input
                        type="text"
                        placeholder="Заголовок секции"
                        value={section.title}
                        onChange={e => handleSectionChange(secIdx, 'title', e.target.value)}
                        required
                    />
                    <div className="theory-block">
                        <label className="theory-help">
                            <span>Правила Markdown:</span>
                            <ul>
                                <li>Обязательно используйте $$ для абзацев</li>
                                <li>Для списка используйте &&</li>
                            </ul>
                        </label>
                        <textarea
                            placeholder="Теория (можно использовать Markdown)"
                            value={section.theoryText}
                            onChange={e => handleSectionChange(secIdx, 'theoryText', e.target.value)}
                            required
                        />
                    </div>
                    <input
                        type="text"
                        placeholder="YouTube видео URL"
                        value={section.videoUrl}
                        onChange={e => handleSectionChange(secIdx, 'videoUrl', e.target.value)}
                        required
                    />

                    <button onClick={() => handleRemoveSection(secIdx)} className="remove-section-btn">
                        Удалить секцию
                    </button>


                    {section.test ? (
                        <>
                            <h4>Тест</h4>
                            <input
                                type="text"
                                placeholder="Название теста"
                                value={section.test.title}
                                onChange={e => handleTestChange(secIdx, 'title', e.target.value)}
                                required
                            />
                            {section.test.questions.map((q, qIdx) => (
                                <div key={qIdx} className="question-block">
                                    <input
                                        type="text"
                                        placeholder="Вопрос"
                                        value={q.questionText}
                                        onChange={e => handleQuestionChange(secIdx, qIdx, 'questionText', e.target.value)}
                                        required
                                    />
                                    <select
                                        value={q.answerOption}
                                        onChange={e => handleQuestionChange(secIdx, qIdx, 'answerOption', e.target.value)}
                                        required
                                    >
                                        <option value="single">Один вариант</option>
                                        <option value="field">Ввод пользователем</option>
                                    </select>


                                    {q.answerOption === 'single' && (
                                        <>
                                            {q.answers.map((a, aIdx) => (
                                                <div key={aIdx} className="answer-block">
                                                    <input
                                                        type="radio"
                                                        name={`question-${secIdx}-${qIdx}`}
                                                        checked={a.isCorrect}
                                                        onChange={() => handleAnswerChange(secIdx, qIdx, aIdx, 'isCorrect', true)}
                                                    />
                                                    <input
                                                        type="text"
                                                        placeholder="Ответ"
                                                        value={a.answerText}
                                                        onChange={e => handleAnswerChange(secIdx, qIdx, aIdx, 'answerText', e.target.value)}
                                                        required
                                                    />
                                                </div>
                                            ))}
                                            <button onClick={() => handleAddAnswer(secIdx, qIdx)}>Добавить вариант ответа</button>
                                        </>
                                    )}

                                    {q.answerOption === 'field' && (
                                        <div className="correct-answer-block">
                                            <input
                                                type="text"
                                                placeholder="Правильный ответ"
                                                value={q.answers[0]?.answerText || ''}
                                                onChange={e => handleFieldAnswerChange(secIdx, qIdx, e.target.value)}
                                                required
                                            />
                                        </div>
                                    )}


                                    <button onClick={() => handleRemoveQuestion(secIdx, qIdx)}>Удалить вопрос</button>
                                </div>
                            ))}
                            <button onClick={() => handleAddQuestion(secIdx)}>Добавить вопрос</button>
                            <button onClick={() => handleRemoveTest(secIdx)} style={{ marginTop: '10px' }}>Удалить тест</button>
                        </>
                    ) : (
                        <button onClick={() => handleAddTest(secIdx)}>Добавить тест к этой секции</button>
                    )}
                </div>
            ))}

            <button onClick={handleAddSection}>Добавить секцию</button>
            <hr />
            <button className="submit-btn" onClick={handleSubmit}>Сохранить курс</button>

        </div>
    );
}

export default TeacherAddCourse;
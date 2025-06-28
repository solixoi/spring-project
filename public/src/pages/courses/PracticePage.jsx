import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import '../../styles/courses/PracticePage.css';

function PracticePage() {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const courseId = searchParams.get('courseId');
    const sectionId = searchParams.get('sectionId');

    const [practiceData, setPracticeData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [userAnswers, setUserAnswers] = useState({});
    const [result, setResult] = useState(null);
    const [startTime, setStartTime] = useState(Date.now());
    const [elapsedTime, setElapsedTime] = useState(0);
    const [errorMessage, setErrorMessage] = useState('');
    const [timerActive, setTimerActive] = useState(true);
    const [showRetryButton, setShowRetryButton] = useState(false);

    const handleRetry = () => {
        setUserAnswers({});
        setResult(null);
        setErrorMessage('');
        setStartTime(Date.now());
        setElapsedTime(0);
        setTimerActive(true);
        setShowRetryButton(false);
    };

    useEffect(() => {
        if (!timerActive) return;
        const timer = setInterval(() => {
            setElapsedTime(Math.floor((Date.now() - startTime) / 1000));
        }, 1000);
        return () => clearInterval(timer);
    }, [startTime, timerActive]);

    useEffect(() => {
        const token = localStorage.getItem('token');
        fetch(`http://localhost:8080/api/practice/information?courseId=${courseId}&sectionId=${sectionId}`, {
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
            .then(data => setPracticeData(data))
            .catch(err => console.error('Ошибка загрузки практики:', err))
            .finally(() => setLoading(false));
    }, [courseId, sectionId]);

    const handleChange = (questionId, value, isMultiple = false) => {
        if (result) return;

        setUserAnswers(prev => {
            if (isMultiple) {
                const current = prev[questionId] || [];
                if (current.includes(value)) {
                    return { ...prev, [questionId]: current.filter(v => v !== value) };
                } else {
                    return { ...prev, [questionId]: [...current, value] };
                }
            } else {
                return { ...prev, [questionId]: value };
            }
        });
    };

    const validateAnswers = () => {
        for (let question of practiceData.answer) {
            if ((question.answerOption === 'single' || question.answerOption === 'multiple') && !userAnswers[question.id]) {
                return 'Пожалуйста, ответьте на все вопросы.';
            }
            if (question.answerOption === 'field' && !userAnswers[question.id]) {
                return 'Пожалуйста, заполните все текстовые поля.';
            }
        }
        return null;
    };

    const handleSubmit = async () => {
        const error = validateAnswers();
        if (error) {
            setErrorMessage(error);
            return;
        }

        const token = localStorage.getItem('token');

        const answersToSend = Object.entries(userAnswers).map(([questionId, currentAnswer]) => ({
            id: Number(questionId),
            currentAnswer,
        }));

        const totalTimeInSeconds = elapsedTime;
        const totalTimeInMinutes = Math.floor(totalTimeInSeconds / 60);
        const remainingSeconds = totalTimeInSeconds % 60;
        const timeSpent = `${totalTimeInMinutes}:${remainingSeconds < 10 ? '0' + remainingSeconds : remainingSeconds}`;

        try {
            const response = await fetch(`http://localhost:8080/api/practice/check`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    answers: answersToSend,
                    courseId,
                    sectionId,
                    timeSpent,
                    endTime: Date.now(),
                }),
            });

            if (!response.ok) {
                throw new Error(`Ошибка проверки: ${response.status}`);
            }

            const data = await response.json();
            setResult(data);
            setTimerActive(false);
            setShowRetryButton(true);
        } catch (error) {
            console.error('Ошибка отправки ответов:', error);
        }
    };

    if (loading) {
        return <div className="practice-loading">Загрузка...</div>;
    }

    if (!practiceData) {
        return <div className="practice-error">Не удалось загрузить данные.</div>;
    }

    return (
        <div className="practice-container">
            <div className="top-buttons">
                <Link to={`/section?courseId=${courseId}&sectionId=${sectionId}`} className="back-button">
                    ← Вернуться к теоретической части
                </Link>
            </div>

            <h1 className="practice-title">{practiceData.title}</h1>

            <div className="practice-timer">
                <span>Время прошедшее: </span>
                <span className="time">
                    {Math.floor(elapsedTime / 3600) > 0
                        ? `${Math.floor(elapsedTime / 3600)}:${String(Math.floor((elapsedTime % 3600) / 60)).padStart(2, '0')}:${String(elapsedTime % 60).padStart(2, '0')}`
                        : `${Math.floor(elapsedTime / 60)}:${String(elapsedTime % 60).padStart(2, '0')}`}
                </span>
            </div>

            {errorMessage && <div className="error-message">{errorMessage}</div>}

            {practiceData.answer.map((question) => (
                <div
                    key={question.id}
                    className={`practice-question-block ${result?.incorrectAnswerId?.includes(question.id) ? 'incorrect' : ''}`}
                >
                    <h2 className="practice-question-text">{question.questionText}</h2>

                    {(question.answerOption === 'single' || question.answerOption === 'multiple') && (
                        <ul className="practice-answers-list">
                            {question.answers.map((answer, idx) => {
                                const isSelected = question.answerOption === 'multiple'
                                    ? userAnswers[question.id]?.includes(answer.answerText)
                                    : userAnswers[question.id] === answer.answerText;

                                const isQuestionIncorrect = result && result.incorrectAnswerId?.includes(question.id);

                                const isCorrectSelected = result && isSelected && !isQuestionIncorrect;

                                return (
                                    <li
                                        key={idx}
                                        className={`practice-answer-item
                ${isSelected ? 'selected' : ''}
                ${isQuestionIncorrect && isSelected ? 'incorrect' : ''}
                ${isCorrectSelected ? 'correct' : ''}`}
                                        onClick={() => handleChange(question.id, answer.answerText, question.answerOption === 'multiple')}
                                        style={{ pointerEvents: result ? 'none' : 'auto' }}
                                    >
                                        <input
                                            type={question.answerOption === 'single' ? 'radio' : 'checkbox'}
                                            name={`question-${question.id}`}
                                            value={answer.answerText}
                                            checked={isSelected}
                                            readOnly
                                        />
                                        {answer.answerText}
                                    </li>
                                );
                            })}
                        </ul>
                    )}

                    {question.answerOption === 'field' && (
                        <input
                            type="text"
                            className={`practice-input-field
                ${result?.incorrectAnswerId?.includes(question.id) ? 'incorrect' : ''}
                ${result && !result.incorrectAnswerId?.includes(question.id) ? 'correct' : ''}
            `}
                            placeholder="Ваш ответ"
                            value={userAnswers[question.id] || ''}
                            onChange={(e) => handleChange(question.id, e.target.value)}
                            readOnly={!!result}
                        />
                    )}
                </div>
            ))}

            <button
                className="practice-submit-button"
                onClick={handleSubmit}
                disabled={!timerActive}
            >
                Отправить ответы
            </button>

            {showRetryButton && (
                <button className="practice-submit-button" onClick={handleRetry}>
                    Пройти еще раз
                </button>
            )}

            {result && (
                <div className="practice-result">
                    <h2>Результат:</h2>
                    <p>Верных ответов: {result.quantityCorrectAnswer} из {result.totalQuestions}</p>
                    <p>Процент: {((result.quantityCorrectAnswer / result.totalQuestions) * 100).toFixed(2)}%</p>
                </div>
            )}
        </div>
    );
}

export default PracticePage;

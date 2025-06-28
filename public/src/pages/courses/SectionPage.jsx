import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import '../../styles/courses/SectionPage.css';
import { getYoutubeEmbedUrl } from '../../utils/youtubeUtils';

function SectionPage() {
  
    const [searchParams] = useSearchParams();
    const courseId = searchParams.get('courseId');
    const sectionId = searchParams.get('sectionId');
    const [sectionData, setSectionData] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('token');

        fetch(`http://localhost:8080/api/section/get/information?courseId=${courseId}&sectionId=${sectionId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Ошибка загрузки раздела: ${res.status}`);
                }
                return res.json();
            })
            .then(data => setSectionData(data))
            .catch(err => console.error(err));
    }, [courseId, sectionId]);

      if (!sectionData) {
        return <div className="text-center text-gray-500">Загрузка...</div>;
      }
    
      return (
        <div className="section-container">
          <div className="top-buttons">
            <Link to={`/courses/${courseId}`} className="back-button">
              ← Вернуться к разделам
            </Link>
          </div>
    
          <h1 className="section-title">{sectionData.title}</h1>
    
          <div className="theory-content" dangerouslySetInnerHTML={{ __html: sectionData.theoryText }} />
    
          {sectionData.hasTest && (
            <div className="practice-button">
              <Link to={`/practice?courseId=${courseId}&sectionId=${sectionData.id}`} className="btn-practice">
                Перейти к практической части
              </Link>
            </div>
          )}
    
          {sectionData.videoUrl && (
            <div className="video-container">
              <iframe
                src={getYoutubeEmbedUrl(sectionData.videoUrl)}
                title="Видео по теме"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              ></iframe>
            </div>
          )}
        </div>
      );
    }
      

export default SectionPage;

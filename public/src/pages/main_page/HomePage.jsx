import React from 'react';
import '../../styles/main_page/HomePage.css';
import Map from '../components/Map.jsx';

function HomePage() {
  return (
    <div className="home-main-container">
      <div className="home-container">
        <h1>Welcome to Our E-learning Platform</h1>
        <p>Explore a wide range of courses designed for students in general secondary education institutions.</p>
        <p>Start learning today!</p>
        <a href="/courses" className="cta-button">View Courses</a>
      </div>
      <div className="map_api">
        <div className='map_container'>
          <Map />
        </div>
        <div className="info-container">
          <div className="info-content">
            <h1>Welcome to Our E-learning Platform</h1>
            <p>
              Discover a world of interactive education. Our platform offers high-quality
              content for students in secondary schools.
            </p>
            <p>
              Choose from a variety of subjects, learn at your own pace, and track your progress.
            </p>
            <p><strong>Start learning today and unlock your full potential!</strong></p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomePage;

import './css/App.css';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Regist from './Regist.tsx';
import Done from './Done.tsx';
import img1 from './assets/1.gif';
import img2 from './assets/2.gif';
import img3 from './assets/3.gif';
import left from './assets/ic_left_arrow.png';
import right from './assets/ic_right_arrow.png';
import Carousel from 'react-bootstrap/Carousel';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/regist" element={<Regist />} />
        <Route path="/done" element={<Done />} />
      </Routes>
    </Router>
  );
}

function Home() {
  const navigate = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0);
  const images = [img1, img2, img3];

  const prevSlide = () => {
    const isFirstSlide = currentIndex === 0;
    const newIndex = isFirstSlide ? images.length - 1 : currentIndex - 1;
    setCurrentIndex(newIndex);
  };

  const nextSlide = () => {
    const isLastSlide = currentIndex === images.length - 1;
    const newIndex = isLastSlide ? 0 : currentIndex + 1;
    setCurrentIndex(newIndex);
  };

  const move = () => {
    navigate('/regist');
  };
  

  return (
    <>
      <div className='div1'>
        <h1>Pickit!</h1>
        <h5>나만의 온라인 투표를 생성해 보세요</h5>
        <div className='okButton' onClick={move}>시작하기</div>
      </div>

      {/* <div className='div2'>
        <img className="arrow" src={left} alt="left" onClick={prevSlide} />
        <img className="mainImage" src={images[currentIndex]} alt="img" />
        <img className="arrow" src={right} alt="right" onClick={nextSlide} />
      </div> */}

    <Carousel data-bs-theme="dark">
      <Carousel.Item>
        <img
          className="d-block w-100 mainImage"
          src={img1}
          alt="First slide"
        />
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100 mainImage" 
          src={img2}
          alt="Second slide"
        />
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100 mainImage"
          src={img3}
          alt="Third slide"
        />
      </Carousel.Item>
    </Carousel>
    </>
  );
}


export default App;

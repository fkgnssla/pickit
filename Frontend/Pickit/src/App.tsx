import './css/App.css';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Regist from './Regist.tsx';
import Done from './Done.tsx';
import Manage from './Manage.tsx';
import img1 from './assets/1.gif';
import img2 from './assets/2.gif';
import img3 from './assets/3.gif';
import Carousel from 'react-bootstrap/Carousel';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/regist" element={<Regist />} />
        <Route path="/done" element={<Done />} />
        <Route path="/manage" element={<Manage />} />
      </Routes>
    </Router>
  );
}

function Home() {
  const navigate = useNavigate();

  const move = () => {
    navigate('/regist');
  };
  
  return (
    <>
      <div className='titleDiv'>
        <h1>Pickit!</h1>
        <h5>나만의 온라인 투표를 생성해 보세요</h5>
        <div className='okButton' onClick={move}>시작하기</div>
      </div>
      <Carousel data-bs-theme="dark" className='carouselDiv'>
        <Carousel.Item>
          <img
            className="d-block w-100 carouselImage"
            src={img1}
            alt="First slide"
          />
        </Carousel.Item>
        <Carousel.Item>
          <img
            className="d-block w-100 carouselImage" 
            src={img2}
            alt="Second slide"
          />
        </Carousel.Item>
        <Carousel.Item>
          <img
            className="d-block w-100 carouselImage"
            src={img3}
            alt="Third slide"
          />
        </Carousel.Item>
      </Carousel>
    </>
  );
}


export default App;

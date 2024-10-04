import { useNavigate } from "react-router-dom";
import axios from "axios";

function Done() {
  const navigate = useNavigate();
  const home = async () => {
    navigate("/");
  };
  const viewTempVotes = () =>{
    axios({
      method: 'get',
      url: 'https://j11a309.p.ssafy.io/api/temp-vote-session',
      withCredentials: true, 
    })
    .then((res) => {
      console.log("결과");
      console.log(res);
    })
    .catch((e) => {
      console.log("결과보기실패");
      console.log(e);
    });
  }

  return (
    <div>
      <h1>Done</h1>
      <h5>작성 내용을 심사 후 투표가 생성될 예정입니다.</h5>
      <h5 className="okButton" onClick={home}> 메인으로 </h5>
      <button onClick={viewTempVotes}>등록 투표 보기</button>
    </div>
  );
}
export default Done;

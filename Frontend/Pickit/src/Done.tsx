import { useNavigate } from "react-router-dom";

function Done() {
  const navigate = useNavigate();
  const home = async () => {
    navigate("/");
  };

  return (
    <div>
      <h1>Done</h1>
      <h5>작성 내용을 심사 후 투표가 생성될 예정입니다.</h5>
      <h5 className="okButton" onClick={home}> 메인으로 </h5>
    </div>
  );
}
export default Done;

import { useNavigate } from "react-router-dom";

function Done() {
  const navigate = useNavigate();
  const home = async () => {
    navigate("/");
  };
  return (
    <div>
      <h1>Done</h1>
      <h5
        className="okButton"
        onClick={home}
      >
        메인으로
      </h5>
    </div>
  );
}
export default Done;

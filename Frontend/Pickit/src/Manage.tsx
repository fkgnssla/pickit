import "./css/App.css";
import "./css/regist.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import deleteIcon from "./assets/delete.svg";
import addIcon from "./assets/add.svg";

function Manage() {
  const navigate = useNavigate();
  const [tempVotes, setTempVotes] = useState([]);
  const [password, setPassword] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const correctPassword = import.meta.env.VITE_APP_PASSWORD;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (password === correctPassword) {
      setIsLoggedIn(true);
    } else {
      alert("비밀번호가 틀렸습니다.");
    }
  };

  const toHome = () => {
    navigate("/");
  };

  useEffect(() => {
    axios({
      method: "get",
      url: "https://j11a309.p.ssafy.io/api/temp-vote-session",
      withCredentials: true,
    })
      .then((res) => {
        // console.log(res.data.data);
        setTempVotes(res.data.data);
      })
      .catch((e) => {
        // console.log("결과보기실패");
        // console.log(e);
      });
  }, []);

  const deleteVote = (id: number) => {
    if (confirm(id + "를 삭제하겠습니까?")) {
      axios({
        method: "delete",
        url: "https://j11a309.p.ssafy.io/api/temp-vote-session?id=" + id,
        withCredentials: true,
      })
        .then(() => {
          alert("삭제완료");
          location.href = location.href;
        })
        .catch((e) => {
          // console.log("삭제실패");
          // console.log(e);
        });
    }
  };

  const createVote = (id: number) => {
    if (confirm(id + "를 투표로 등록하겠습니까?")) {
      axios({
        method: "post",
        url: "https://j11a309.p.ssafy.io/api/vote-session",
        withCredentials: true,
        data: { id: id },
      })
        .then(() => {
          alert("등록완료");
          location.href = location.href;
        })
        .catch((e) => {
          // console.log("등록실패");
          // console.log(e);
        });
    }
  };

  const nowVote = () => {
    axios({
      method: "get",
      url: "https://j11a309.p.ssafy.io/api/vote-session/ongoing",
      withCredentials: true,
      headers: {
        Authorization: "",
      },
    })
      .then((res) => {
        alert("등록완료");
        // console.log(res.data.data);
      })
      .catch((e) => {
        // console.log(e);
      });
  };
  const endVote = () => {
    axios({
      method: "get",
      url: "https://j11a309.p.ssafy.io/api/vote-session/end",
      withCredentials: true,
      headers: {
        Authorization: "",
      },
    })
      .then((res) => {
        alert("등록완료");
        // console.log(res.data.data);
      })
      .catch((e) => {
        // console.log(e);
      });
  };

  return (
    <>
      <div>
        {isLoggedIn ? (
          <div className="registForm">
            <div className="border">
              <div className="box">
                {tempVotes.map((tempVote, index) => (
                  <div
                    key={tempVote.id}
                    style={{
                      backgroundColor: "rgb(220, 255, 225)",
                      padding: "10px",
                      margin: "10px",
                      borderRadius: "5px",
                    }}
                  >
                    <div
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                      }}
                    >
                      <h3>
                        {index + 1}. {tempVote.title}
                      </h3>
                      <div>
                        <img
                          src={addIcon}
                          className="deleteButton"
                          alt="add"
                          onClick={() => createVote(tempVote.id)}
                        />
                        <img
                          src={deleteIcon}
                          className="deleteButton"
                          alt="delete"
                          onClick={() => deleteVote(tempVote.id)}
                        />
                      </div>
                    </div>
                    <img
                      src={tempVote.thumbnail}
                      alt={tempVote.title}
                      width="100"
                    />
                    <span> | 방송사: {tempVote.broadcast_name}</span>
                    <span> | 설명: {tempVote.description}</span>
                    <br />
                    <span>
                      기간:{" "}
                      {new Date(tempVote.start_date).toLocaleDateString() +
                        " " +
                        new Date(tempVote.start_date).toLocaleTimeString()}{" "}
                      ~{" "}
                      {new Date(tempVote.end_date).toLocaleDateString() +
                        new Date(tempVote.end_date).toLocaleTimeString()}
                    </span>
                    <hr />
                    <h5>후보 목록:</h5>
                    <ol>
                      {tempVote.candidates.map((candidate, candidateIndex) => (
                        <li key={candidate.number}>
                          <img
                            src={candidate.profile_img}
                            alt={candidate.name}
                            width="50"
                          />
                          <span>
                            {" "}
                            | 후보 {candidateIndex + 1}: {candidate.name}
                          </span>
                          <span> | 득표수: {candidate.vote_cnt}</span>
                        </li>
                      ))}
                    </ol>
                  </div>
                ))}
                <button onClick={toHome}>메인 화면으로</button>
                <hr />
                <button onClick={nowVote}>진행중인 투표</button>
                <button onClick={endVote}>종료된 투표</button>
              </div>
            </div>
          </div>
        ) : (
          <form onSubmit={handleSubmit}>
            <label style={{width:"100%"}}>
              비밀번호: &nbsp;
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="비밀번호 입력"
              />
              &nbsp;
              <button type="submit">확인</button>
              &nbsp;
              <button onClick={toHome}>메인 화면으로</button>
            </label>
          </form>
        )}
      </div>
    </>
  );
}

export default Manage;

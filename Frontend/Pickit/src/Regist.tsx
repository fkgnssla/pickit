import { useState, ChangeEvent, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import noFile from "./assets/no_file.png";
import redDot from "./assets/red_dot.png";
import deleteIcon from "./assets/delete.svg";
import addIcon from "./assets/add.svg";
import "./css/regist.css";

const Regist = () => {
  const navigate = useNavigate();
  const [name, setName] = useState<string>('');
  const [contact, setContact] = useState<string>('');
  const [broadcast, setBroadcast] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [mainImage, setMainImage] = useState<string>('');
  const [imageName, setImageName] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [now, setNow] = useState<string>(changeDateFormat(new Date())); // 날짜 형식 yyyy-mm-dd hh-mm
  const [startPeriod, setStartPeriod] = useState<string>('');
  const [endPeriod, setEndPeriod] = useState<string>('');
  const [openResult, setOpenResult] = useState(false);
  const [shouldPeriodValidate, setShouldPeriodValidate] = useState(false); // 투표 시간이 유효한지 확인
  const [shouldNavigate, setShouldNavigate] = useState(false); // 상태 업데이트를 감지하기 위한 상태 추가
  const [candidates, setCandidates] = useState(
    Array.from({ length: 2 }, () => ({ name: "", imageName: "", image: "" }))
  );
  const [result, setResult] = useState({
    host: { name: "", contact: "", broadcast: "" },
    content: { title: "", mainImage: "", description: "", startPeriod: "", endPeriod: "", openResult: false },
    candidates: Array({name: "", imageName: "", image: ""}),
  });
  
  /// 투표 기간 영역
  function ConvertTo2Digits(newNum: number) {
    return newNum.toString().padStart(2, '0');
  }

  function changeDateFormat(newDate: Date) {
    return (
        [
          newDate.getFullYear(),
          ConvertTo2Digits(newDate.getMonth() + 1),
          ConvertTo2Digits(newDate.getDate()),
        ].join('-') +
        ' ' +
        [
          ConvertTo2Digits(newDate.getHours()),
          ConvertTo2Digits(newDate.getMinutes()),
          ConvertTo2Digits(newDate.getSeconds()),
          ].join(':')
     );
  }
  
  const validatePeriod = (period: string, type: string) => {
    if(type === "startPeriod") {
      // 시작 시간이 과거면 예외처리
      if(new Date(period).getTime() < new Date(now).getTime()){
        alert("투표 시작 시간이 현재 이전일 수 없습니다.")
        return;
      } else {
        // 시작 시간 > 종료 시간이면 예외처리
        if(endPeriod === ""){
          setStartPeriod(period);
        } else {
          if(new Date(period).getTime() >= new Date(endPeriod).getTime()){
            alert("투표 종료 날짜를 확인하세요");
            setStartPeriod(""); 
          } else{
           setStartPeriod(period); 
          }
        }
      }
    } else if (type === "endPeriod") {
      // 시작 시간이 먼저 있어야 함
      if(startPeriod === ""){
        alert("투표 시작 날짜를 먼저 선택하세요")
        setEndPeriod(""); 
        return;
      } else {
        // 시작 시간 >= 종료 시간 이전이면 예외처리
        if(new Date(startPeriod).getTime() >= new Date(period).getTime()){
          alert("투표 시작 시간를 확인하세요");
          setEndPeriod(""); 
        } else {
          setEndPeriod(period); 
        }
      }
    }
  }
  /// 투표 기간 영역(끝)

  // 잘 입력되었으면 전송
  useEffect(() => {
    if (shouldNavigate) {

      const formData = new FormData();
      formData.append('broadcast_id', broadcast);
      formData.append('title', title);
      formData.append('description', description);
      formData.append('start_date', startPeriod);
      formData.append('end_date', endPeriod);
      if (mainImage) {
        formData.append('thumbnail', mainImage); // 메인 이미지 파일
      }
      candidates.forEach((candidate, index) => {
        formData.append(`candidates[${index}].name`, candidate.name);
        if (candidate.image) {
          formData.append(`candidates[${index}].profile_img`, candidate.image); // 후보자 이미지 파일
        }
      });

      axios({
        method: 'post',
        url: 'http://43.202.60.229:8080/api/temp-vote-session',
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': '???????????',
        },
        data: formData
      });
           
      navigate("/done");
    }
  }, [shouldNavigate]);

  // 빈 값이 없고, 투표 시간이 적절하면 통과
  useEffect(() =>{
    if(shouldPeriodValidate) {
      // 투표 시작시간이 과거면 에러처리
      setNow(changeDateFormat(new Date()));
      if((new Date(now).getTime() > new Date(startPeriod).getTime())) {
        alert("투표 시작 시간을 확인해주세요 (폼 작성 도중 시작 시간을 지났는지 확인하세요.)")
        setNow(changeDateFormat(new Date()));
        setStartPeriod("");
        setEndPeriod("");
        setShouldPeriodValidate(false);
        return;
      } else {
        setResult({
          host: { name: name, contact: contact, broadcast: broadcast },
          content: { title: title, mainImage: mainImage, description: description, startPeriod: startPeriod, endPeriod: endPeriod, openResult: openResult },
          candidates: candidates,
        });
        setShouldNavigate(true);
      }
    }
  }, [shouldPeriodValidate])

  const isNull = () => {
    const messages = ["투표 주최자를 작성하세요", "연락처를 작성하세요", "주최 방송국을 선택하세요", "투표 이름을 작성하세요", "투표 이미지를 등록하세요", "투표 설명을 작성하세요", "시작 기간을 설정하세요", "종료 기간을 설정하세요"];
    const elements = [name, contact, broadcast, title, mainImage, description, startPeriod, endPeriod];
    
    for (let i = 0; i < elements.length; i++) {
      if (elements[i] === "") {
        alert(messages[i]);
        return;
      }
    }
    // 후보자들 이름이 빈 값인지 검사
    const hasEmptyCandidateName = candidates.some(candidate => candidate.name === "");
    if (hasEmptyCandidateName) {
      alert("후보자 이름을 작성하세요(빈 칸은 삭제하세요)");
      return;
    }
    setShouldPeriodValidate(true);
  };

  const addCandidate = () => {
    setCandidates([...candidates, {name: "", imageName: "", image: ""}]);
  };

  const deleteCandidate = (index: number) => {
    if(candidates.length <= 2){
      alert("2명 이하로 줄일 수 없습니다");
      return;
    }
    const updatedCandidates = [
      ...candidates.slice(0, index),   // index 앞쪽 부분
      ...candidates.slice(index + 1)   // index 뒤쪽 부분
    ];
    setCandidates(updatedCandidates); // 삭제 후 배열 업데이트
  };

  // 후보자 이름 업데이트
  const candidateNameChange = (index: number, value: string) => {
    const updatedCandidates = [...candidates];
    updatedCandidates[index].name = value; 
    setCandidates(updatedCandidates);
  };

  // 후보자 사진 업데이트
  const candidateImageChange = (index: number, file: File | null) => {
    if (file) {
      const updatedCandidates = [...candidates];
      const reader = new FileReader();
      reader.onload = () => {
        updatedCandidates[index].imageName = file.name; // 파일의 이름을 imageName 필드에 저장
        updatedCandidates[index].image = reader.result as string; // 파일을 읽어와서 image 필드에 저장
        setCandidates(updatedCandidates);
      };
      reader.readAsDataURL(file);
    }
  };

  const mainImageChange = (file: File | null) => {
    if (file) {
      setImageName(file.name);
      const reader = new FileReader();
      reader.onload = () => {
        setMainImage(reader.result as string); // 파일을 읽어와 메인 이미지에 저장
      };
      reader.readAsDataURL(file);
    }
  };
  
  // 이미지파일인지 검증
  const validateFile = (
    e: ChangeEvent<HTMLInputElement>,
    onValidFile: (file: File | null) => void,
    index: number
  ) => {
    const fileInput = e.target;
    const file = fileInput.files ? fileInput.files[0] : null;
  
    if (!file) {
      if (index === 99999) {
        setMainImage("");
        setImageName("");
      } else {
        const updatedCandidates = [...candidates];
        updatedCandidates[index].imageName = "";
        updatedCandidates[index].image = "";
        setCandidates(updatedCandidates);
      }
    } else {
      // 파일 확장자 검증 로직
      const filePath = fileInput.value;
      const reg = /(.*?)\.(jpg|bmp|jpeg|png|webp|gif|PNG)$/;
  
      if (filePath && !reg.test(filePath)) {
        alert("이미지 파일만 업로드 가능합니다. (jpg, bmp, jpeg, png, webp, gif)");
        fileInput.value = ""; // 파일 입력 초기화
      } else {
        onValidFile(file); // 파일이 유효한 경우 처리
      }
    }
  };
  
  return (
    <div className="registForm">
      <h1>투표 생성하기</h1>
      <br />
      <div className="registHeader">
        <h3>주최</h3>
        <p><img src={redDot} alt="redDot" className="redDot"/>  는 필수로 입력해야 합니다</p>
      </div>
      <div className="border">
        <div className="box">
          <div><img src={redDot} alt="redDot" className="redDot"/> 주최자</div>
          <input type="text" placeholder="회사 or 프로젝트 이름" onChange={(e) => setName(e.target.value)}/>
        </div>
        <div className="box">
          <div><img src={redDot} alt="redDot" className="redDot" /> 연락처</div>
          <input type="tel" onChange={(e) => setContact(e.target.value)}/>
        </div>
        <div className="box">
          <div><img src={redDot} alt="redDot" className="redDot"/> 방송사</div>
          <select value={broadcast} onChange={(e) => setBroadcast(e.target.value)}>
            <option value="" disabled hidden>
              선택하세요
            </option>
            <option value="none">기타(해당 없음)</option>
            <option value="SBS">SBS</option>
            <option value="KBS">KBS</option>
            <option value="MBC">MBC</option>
            <option value="JTBC">JTBC</option>
            <option value="Mnet">Mnet</option>
            <option value="tvN">tvN</option>
            <option value="WATCHA">WATCHA</option>
            <option value="Wavve">Wavve</option>
            <option value="coupang play">coupang play</option>
            <option value="Disney">Disney</option>
            <option value="TVING">TVING</option>
            <option value="NETFLIX">NETFLIX</option>
            <option value="TV CHOSUN">TV CHOSUN</option>
            <option value="ChannelA">ChannelA</option>
            <option value="afreecaTV">afreecaTV</option>
            <option value="twitch">twitch</option>
          </select>
        </div>
      </div>
      <br />
      <h3>투표 서식</h3>
      <div className="border">
        <div className="box">
          <div><img src={redDot} alt="redDot" className="redDot"/> 제목</div>
          <input type="text" placeholder="투표 제목" onChange={(e) => setTitle(e.target.value)}/>
        </div>
        <div className="box candidateContent">
          <div className="candidateInput">
            <div><img src={redDot} alt="redDot" className="redDot"/> 메인 이미지</div>
            <div className="fileInput">
              <label htmlFor="upload_file">업로드</label>
              <input
                id="upload_file"
                type="file"
                accept=".jpg,.jpeg,.png,.webp,.gif,.bmp"
                onChange={(e) =>
                  validateFile(e, (file) => mainImageChange(file), 99999) // 검증 통과 시 mainImageChange 호출
                }
              />
              <span>{imageName === "" ? "선택된 파일 없음" : imageName}</span>
            </div>
          </div>
          <img className="uploadFile" src={mainImage ? mainImage : noFile} alt="img" /> {/* 이미지 미리보기 */}
        </div>
        <div className="box">
          <div><img src={redDot} alt="redDot" className="redDot"/> 설명 </div>
          <textarea  onChange={(e) => setDescription(e.target.value)}/>
        </div>
        <div className="box">
        <img src={redDot} alt="redDot" className="redDot"/>
          <span> 시작 </span>
          <input type="datetime-local" value={startPeriod} onChange={(e) => validatePeriod(e.target.value, "startPeriod")}/>
          <span> ~ 종료 </span>
          <input type="datetime-local" value={endPeriod} onChange={(e) => validatePeriod(e.target.value, "endPeriod")}/>
        </div>
        <div className="box">
          <span><img src={redDot} alt="redDot" className="redDot" /> 중간결과 공개 여부 </span>
          <input type="checkbox" checked={openResult} onChange={(e) => setOpenResult(!e.target.value)}/>
        </div>
      {/* </div> */}
      <br />
      <div className="box">
        <h4>후보자</h4>
        <span><img src={redDot} alt="redDot" className="redDot"/> 최소 2명 이상 등록</span>
        <hr />
      </div>
      {/* <div className="border"> */}
        {candidates.map((candidate, index) => (
          <div className="box" key={index}>
            <div className="candidateHeader">
              <div>{index + 1}</div>
              <div className="deleteButton" onClick={() => deleteCandidate(index)}>
                <img src={deleteIcon} alt="" />
              </div>
            </div>
            <div className="candidateContent">
              <div className="candidateInput">
                <input
                  type="text"
                  style={{ width: "calc(50% + 25px )" }}
                  placeholder={`후보자 이름 (필수)`}
                  value={candidate.name}
                  onChange={(e) => candidateNameChange(index, e.target.value)}
                />
                <div className="fileInput">
                <label htmlFor={`upload_file_${index}`}>업로드</label>
                  <input
                    id={`upload_file_${index}`} // 각 파일 input에 고유한 id 부여
                    type="file"
                    accept=".jpg,.jpeg,.png,.webp,.gif,.bmp"
                    onChange={(e) =>
                      validateFile(e, (file) => candidateImageChange(index, file), index) // index를 추가
                    }
                    placeholder="이미지"
                  />
                  <span>{candidate.imageName === "" ? "선택된 파일 없음" : candidate.imageName}</span>
                </div>
              </div>
              <div>
                <img
                  className={"uploadFile"}
                  src={candidate.image ? candidate.image : noFile}
                  alt={"img"}
                />
              </div>
            </div>
            <hr />
          </div>
        ))}
          <div className="addButton" onClick={addCandidate}>
            <span>추가&nbsp;</span>  
            <img src={addIcon} alt="add" />
          </div>
      </div>
      <br />
      <div className="okButtonBox">
        <p className="okButton" onClick={isNull}>제출</p>
      </div>
    </div>
  );
};
export default Regist;
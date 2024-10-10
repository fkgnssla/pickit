const Voting = artifacts.require("Voting");

module.exports = async function (callback) {
  try {
    // 인자추출
    const candidateNamesArg = process.argv.find(arg => arg.startsWith('--candidate-names='));
    const startTimeArg = process.argv.find(arg => arg.startsWith('--start-time='));
    const endTimeArg = process.argv.find(arg => arg.startsWith('--end-time='));
    
    // candidateNamesArg가 undefined가 아닌지 확인 후 split
    if (!candidateNamesArg) {
      throw new Error("Missing --candidate-names argument");
    }

    if (!startTimeArg) {
      throw new Error("Missing --start-time argument");
    }

    if (!endTimeArg) {
      throw new Error("Missing --end-time argument");
    }

    const candidateNames = candidateNamesArg.split('=')[1].split(",");
    const startTime = parseInt(startTimeArg.split('=')[1], 10); // Unix timestamp로 변환
    const endTime = parseInt(endTimeArg.split('=')[1], 10); // Unix timestamp로 변환
    
    // 계정 목록 불러오기
    const accounts = await web3.eth.getAccounts();

    // 컨트랙트 배포
    const instance = await Voting.new(candidateNames, startTime, endTime, { from: accounts[0] });
    console.log("Contract Address:", instance.address);

    callback();
  } catch (error) {
    console.error("Error deploying contract:", error);
    callback(error);
  }
};

const Voting = artifacts.require("Voting");

module.exports = async function (callback) {
  try {
    // 인자추출
    const candidateNamesArg = process.argv.find(arg => arg.startsWith('--candidate-names='));
    
    // candidateNamesArg가 undefined가 아닌지 확인 후 split
    if (!candidateNamesArg) {
      throw new Error("Missing --candidate-names argument");
    }

    const candidateNames = candidateNamesArg.split('=')[1].split(",");
    
    // 계정 목록 불러오기
    const accounts = await web3.eth.getAccounts();

    // 컨트랙트 배포
    const instance = await Voting.new(candidateNames, { from: accounts[0] });
    console.log("Contract Address:", instance.address);

    callback();
  } catch (error) {
    console.error("Error deploying contract:", error);
    callback(error);
  }
};

const fs = require('fs');
const path = require('path');
const yaml = require('js-yaml');
const TruffleContract = require('truffle-contract');
const Web3 = require('web3');

// Voting.sol 컴파일된 아티팩트를 로드합니다.
const VotingArtifact = require(path.join(__dirname, '../build/contracts/Voting.json'));

async function deployVoting(networkConfigPath, envConfigPath) {
  // 네트워크 설정을 JSON 파일에서 불러옵니다.
  const networkConfig = JSON.parse(fs.readFileSync(networkConfigPath, 'utf8'));

  // 환경 설정을 YAML 파일에서 불러옵니다.
  const envConfig = yaml.load(fs.readFileSync(envConfigPath, 'utf8')).network;

  // Web3 인스턴스를 설정합니다.
  const web3 = new Web3(new Web3.providers.HttpProvider(`http://${envConfig.host}:${envConfig.port}`));

  // TruffleContract로 Voting 계약을 초기화합니다.
  const Voting = TruffleContract(VotingArtifact);
  Voting.setProvider(web3.currentProvider);

  // 계정 정보를 가져옵니다.
  const accounts = await web3.eth.getAccounts();

  // 계약을 배포합니다.
  const deployedContract = await Voting.new(networkConfig.candidates, { from: accounts[0], gas: 5500000 });

  console.log(`Contract deployed at address: ${deployedContract.address}`);
}

async function main() {
  // 사용자로부터 네트워크 파일 경로와 환경 설정 경로를 인자로 받습니다.
  const networkFilePath = process.argv[2];
  const envFilePath = process.argv[3];

  if (!networkFilePath || !envFilePath) {
    console.error("Please provide the path to the network config JSON file and environment config YAML file.");
    process.exit(1);
  }

  try {
    await deployVoting(networkFilePath, envFilePath);
    console.log("Deployment successful!");
  } catch (error) {
    console.error("Deployment failed:", error);
  }
}

main();

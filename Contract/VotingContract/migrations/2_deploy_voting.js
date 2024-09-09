const Voting = artifacts.require("Voting");

module.exports = async function (deployer) {
  const candidate_name = ["김형민", "김민철", "남경민", "이동기", "구승석", "방태연"];

  await deployer.deploy(Voting, candidate_name).then(async (instance) => {
    console.log("투표 계약 주소: ", instance.address);
  }).catch((err) => {
    console.error("Error : ", err);
  });

  console.log("배포 완료.");
};

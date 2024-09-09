const Voting = artifacts.require("Voting");

module.exports = function (deployer) {
  const candidate_name = ["김형민", "김민철", "남경민", "이동기", "구승석", "방태연"];
  deployer.deploy(Voting, candidate_name);
};

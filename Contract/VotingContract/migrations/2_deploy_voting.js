const Voting = artifacts.require("Voting");

module.exports = function (deployer) {
  const candidate_name = ["Alice", "Bob", "Charlie"];
  deployer.deploy(Voting, candidate_name);
  //deployer.deploy(Voting);
};

const Voting = artifacts.require("Voting");

module.exports = async function (deployer) {
  const candidateNamesArg = process.argv.find(arg => arg.startsWith('--candidate-names='));
  const candidateNames = candidateNamesArg.split('=')[1].split(',');

  await deployer.deploy(Voting, candidateNames).then(async (instance) => {
    console.log("Contract Address: ", instance.address);
  }).catch((err) => {
    console.error("Error : ", err);
  });

  console.log("Deployment complete.");
};

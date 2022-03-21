const MLKToken = artifacts.require("MLKToken");

module.exports = function (deployer, network) {
  console.log(`network = ${network}`);
  deployer.deploy(MLKToken);
};

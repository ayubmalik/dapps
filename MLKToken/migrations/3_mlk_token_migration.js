const MLKToken = artifacts.require("MLKToken");

module.exports = function (deployer) {
  deployer.deploy(MLKToken);
};

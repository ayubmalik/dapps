const ownership = artifacts.require("Ownership");

module.exports = function (deployer) {
  deployer.deploy(ownership);
};

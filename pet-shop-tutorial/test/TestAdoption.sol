// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/Adoption.sol";

contract TestAdoption {
    Adoption adoption = Adoption(DeployedAddresses.Adoption());

    uint256 expectedPetId = 0;

    address expectedAdopter = address(this);

    // Testing the adopt() function
    function testUserCanAdoptPet() public {
        uint256 returnedId = adoption.adopt(expectedPetId);

        Assert.equal(
            returnedId,
            expectedPetId,
            "Adoption of the expected pet should match what is returned."
        );
    }

    // Testing retrieval of a single pet's owner
    function testGetAdopterAddressByPetId() public {
        address adopter = adoption.adopters(expectedPetId);

        Assert.equal(
            adopter,
            expectedAdopter,
            "Owner of the expected pet should be this contract"
        );
    }
}

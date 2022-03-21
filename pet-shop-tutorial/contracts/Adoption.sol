// SPDX-License-Identifier: MIT
pragma solidity >=0.8.12 <0.9.0;

contract Adoption {
    address[16] public adopters;

    // Adopting a pet
    function adopt(uint256 petId) public returns (uint256) {
        require(petId >= 0 && petId <= 15);
        adopters[petId] = msg.sender;

        return petId;
    }

    // All the adopters as it is an array
    function getAdopters() public view returns (address[16] memory) {
        return adopters;
    }
}

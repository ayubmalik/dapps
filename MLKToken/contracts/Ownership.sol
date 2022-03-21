// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract Ownership {
    address public owner;
    address public newOwner;

    event ChangeOwnership(address indexed _owner, address indexed _newOwner);

    constructor() {
        owner = msg.sender;
    }

    function changeOwnership(address _newOwner) public {
        require(owner == msg.sender, "only current owner can change owner");
        newOwner = _newOwner;
    }

    function acceptOwnership() public {
        require(newOwner == msg.sender, "only new owner can accept ownership");
        emit ChangeOwnership(owner, newOwner);
        owner = newOwner;
        newOwner = address(0); // reset new owner;
    }
}

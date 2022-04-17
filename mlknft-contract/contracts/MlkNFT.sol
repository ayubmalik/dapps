// SPDX-License-Identifier: MIT
pragma solidity >=0.8.12 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";

contract MlkNFT is ERC721 {
    constructor() ERC721("Malik NFT", "MlkNFT") {}
}

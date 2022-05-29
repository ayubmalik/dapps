//SPDX-License-Identifier: Unlicense
pragma solidity ^0.8.0;

contract MarketSentiment {
    address public owner;
    string[] public tickersArray;

    constructor() {
        owner = msg.sender;
    }

    struct Ticker {
        bool exists;
        uint256 up;
        uint256 down;
        mapping(address => bool) voters;
    }

    event TickerUpdated(uint256 up, uint256 down, address voter, string ticker);

    mapping(string => Ticker) private tickers;
}

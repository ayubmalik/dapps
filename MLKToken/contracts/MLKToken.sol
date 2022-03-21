// SPDX-License-Identifier: MIT
pragma solidity >=0.8.12 <0.9.0;

interface Token {
    /// @param _owner The address from which the balance will be retrieved
    /// @return balance the balance
    function balanceOf(address _owner) external view returns (uint256 balance);

    /// @notice send `_value` token to `_to` from `msg.sender`
    /// @param _to The address of the recipient
    /// @param _value The amount of token to be transferred
    /// @return success Whether the transfer was successful or not
    function transfer(address _to, uint256 _value)
        external
        returns (bool success);

    /// @notice send `_value` token to `_to` from `_from` on the condition it is approved by `_from`
    /// @param _from The address of the sender
    /// @param _to The address of the recipient
    /// @param _value The amount of token to be transferred
    /// @return success Whether the transfer was successful or not
    function transferFrom(
        address _from,
        address _to,
        uint256 _value
    ) external returns (bool success);

    /// @notice `msg.sender` approves `_addr` to spend `_value` tokens
    /// @param _to The address of the account able to transfer the tokens
    /// @param _value The amount of wei to be approved for transfer
    /// @return success Whether the approval was successful or not
    function approve(address _to, uint256 _value)
        external
        returns (bool success);

    /// @param _from The address of the account owning tokens
    /// @param _to The address of the account able to transfer the tokens
    /// @return remaining Amount of remaining tokens allowed to spent
    function allowance(address _from, address _to)
        external
        view
        returns (uint256 remaining);

    event Transfer(address indexed _from, address indexed _to, uint256 _value);
    event Approval(
        address indexed _owner,
        address indexed _spender,
        uint256 _value
    );
}

contract MLKToken is Token {
    uint256 private constant MAX_AMOUNT = 2**256 - 1;
    string public constant name = "Malik Token";
    uint8 public constant decimals = 18;
    string public constant symbol = "MLK";

    address private owner;
    mapping(address => mapping(address => uint256)) allowances;
    mapping(address => uint256) balances;

    constructor() {
        owner = msg.sender;
        balances[owner] = 70000000000000000000;
    }

    function balanceOf(address _address)
        external
        view
        override
        returns (uint256 balance)
    {
        return balances[_address];
    }

    function transfer(address _to, uint256 _value)
        external
        override
        returns (bool success)
    {
        require(
            balances[msg.sender] >= _value,
            "token balance is lower than the value requested"
        );
        balances[msg.sender] -= _value;
        balances[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    function transferFrom(
        address _from,
        address _to,
        uint256 _value
    ) external override returns (bool success) {
        uint256 amount = allowances[_from][_to];
        require(
            balances[_from] >= _value && amount >= _value,
            "token balance or allowance is lower than amount requested"
        );
        balances[_from] -= _value;
        balances[_to] += _value;
        allowances[_from][_to] -= _value;
        emit Transfer(_from, _to, _value);
        return true;
    }

    function approve(address _to, uint256 _value)
        external
        override
        returns (bool success)
    {
        allowances[msg.sender][_to] += _value;
        emit Approval(msg.sender, _to, _value);
        return true;
    }

    function allowance(address _from, address _to)
        external
        view
        override
        returns (uint256 remaining)
    {
        return allowances[_from][_to];
    }
}

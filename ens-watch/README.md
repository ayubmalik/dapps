# Getting Started with Create React App

# Contract Addresses

base 0x57f1887a8BF19b14fC0dF6Fd9B2acc9Af147eA85

reg controller
https://ropsten.etherscan.io/address/0x283af0b28c62c092c9727f1ee09c02ca627eb7f5#code

## Event ABI

topic = NameRegistered(string, bytes32, address, uint256, uint256)

```
{
    "anonymous":false,
    "inputs":[
      {
        "indexed":false,
        "internalType":"string",
        "name":"name",
        "type":"string"
      },
      {
        "indexed":true,
        "internalType":"bytes32",
        "name":"label",
        "type":"bytes32"
      },
      {
        "indexed":true,
        "internalType":"address",
        "name":"owner",
        "type":"address"
      },
      {
        "indexed":false,
        "internalType":"uint256",
        "name":"cost",
        "type":"uint256"
      },
      {
        "indexed":false,
        "internalType":"uint256",
        "name":"expires",
        "type":"uint256"
      }
    ],
    "name":"NameRegistered",
    "type":"event"
  }
```

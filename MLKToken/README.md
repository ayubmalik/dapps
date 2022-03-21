# MLK Token

Example ERC20 token, Malik (MLK) Token.

## MetaMask + Ganache Notes

* Install MetaMask
* Import wallet using **ganache** seed phrase
* Add ganache network Main Network | custom rpc | http://127.0.0.1:7545 chainid=1337
* Import individaul accounts using Import account | private key

## Truffle console testing

```
const mlkToken = await MLKToken.deployed();

const accounts = await web3.eth.getAccounts();
const owner = accounts[0];
const receiver = accounts[1];

mlkToken.transfer(receiver, "1000000000000000000"); // use string for amount


```

## TODO
 * Add "minter" state to differentiate between contract owner and actual token owner/minter
 * add Ownership

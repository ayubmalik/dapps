package main

import (
	"crypto/ecdsa"
	"fmt"
	"github.com/ethereum/go-ethereum/crypto"
	"log"
)

func main() {
	fmt.Println("generating keys")
	key, err := genKey()
	if err != nil {
		log.Fatalln(err)
	}

	hash, _ := stampedHash("hello everyone, its nice to be here aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ")
	sig, _ := sign(hash, key)
	fmt.Println("hash", hash)
	fmt.Println("sig", sig)
	pubKey, err := crypto.SigToPub(hash, sig)

	if err != nil {
		log.Fatalln(err)
	}
	fmt.Println("pubKey1", crypto.PubkeyToAddress(key.PublicKey).String())
	fmt.Println("pubKey2", crypto.PubkeyToAddress(*pubKey).String())
}

func sign(hash []byte, key *ecdsa.PrivateKey) ([]byte, error) {
	sig, err := crypto.Sign(hash, key)
	if err != nil {
		return nil, err
	}
	return sig, nil
}

func stampedHash(msg string) ([]byte, error) {
	hash := crypto.Keccak256Hash([]byte(msg))
	stamp := []byte("\x19malik.eth signed message:\n32")
	signed := crypto.Keccak256Hash(stamp, hash.Bytes())
	return signed.Bytes(), nil
}

func genKey() (*ecdsa.PrivateKey, error) {
	privateKey, err := crypto.GenerateKey()
	if err != nil {
		return nil, err
	}
	return privateKey, err
}

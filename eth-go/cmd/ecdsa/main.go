package main

import (
	"crypto/ecdsa"
	"fmt"
	"github.com/ethereum/go-ethereum/crypto"

	"os"
)

func main() {
	fmt.Println("generating keys")
	for i := 0; i < 500; i++ {
		privateKey, err := genKey()
		if err != nil {
			os.Exit(1)
		}
		fmt.Printf("private key: %s\n", crypto.PubkeyToAddress(privateKey.PublicKey))
	}
}

func genKey() (*ecdsa.PrivateKey, error) {
	privateKey, err := crypto.GenerateKey()
	if err != nil {
		return nil, err
	}
	return privateKey, err
}

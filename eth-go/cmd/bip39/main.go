package main

import (
	"bufio"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"io"
	"net/http"
	"os"
	"regexp"
	"strconv"
)

func main() {
	words := loadWords()
	ent, err := entropy()
	if err != nil {
		os.Exit(1)
	}

	indices := wordIndices(ent)
	phrase := getPhrase(words, indices)
	fmt.Println("phrase =", phrase)
}

func getPhrase(words []string, indices []int) []string {
	var selected []string
	for _, i := range indices {
		selected = append(selected, words[i])
	}
	return selected
}

func entropy() ([]byte, error) {
	url := "https://www.random.org/cgi-bin/randbyte?nbytes=16&format=h"
	resp, err := http.Get(url)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}

	reg, _ := regexp.Compile(`[^a-f,\d]+`)
	cleaned := reg.ReplaceAll(body, nil)
	return hex.DecodeString(string(cleaned))
}

func hash(data []byte) []byte {
	h := sha256.New()
	h.Write(data)
	return h.Sum(nil)
}

func wordIndices(entropy []byte) []int {
	hash := hash(entropy)
	checksum := bin(hash)[:4] // todo - hardcoded for 128 bits
	full := bin(entropy) + checksum
	max := len(full) / 11
	indices := make([]int, max)
	for i := 0; i < max; i++ {
		sub := full[i*11 : i*11+11]
		n, _ := strconv.ParseInt(sub, 2, 64)
		indices[i] = int(n)
	}
	return indices
}

func bin(data []byte) string {
	s := ""
	for _, b := range data {
		s += fmt.Sprintf("%08b", b)
	}
	return s
}

func loadWords() []string {
	f, err := os.Open("/home/ayub/projects/dapps/eth-go/cmd/bip39/bip39-wordlist.txt")
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	defer f.Close()
	words := make([]string, 0)
	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		words = append(words, scanner.Text())
	}
	return words
}

// spike
func split(x byte) (byte, byte) {
	n := 4
	var p, q byte
	for i := 0; i < n; i++ {
		p = p | (x&0x80)>>7
		x = x << 1
		p = p << 1
	}
	for i := 0; i < n; i++ {
		q = q | (x&0x80)>>7
		x = x << 1
		q = q << 1
	}
	return p >> 1, q >> 1
}

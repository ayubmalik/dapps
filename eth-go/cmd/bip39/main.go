package main

import (
	"encoding/hex"
	"fmt"
	"io"
	"net/http"
	"os"
	"regexp"
	"unsafe"
)

func main() {
	numbers := generateNumbers(12)
	fmt.Println("got numbers", numbers)
	i := 23 + 1<<8
	fmt.Printf("i = %d (%d)\n", i, unsafe.Sizeof(i))
	fmt.Printf("i = %b\n", i)
	e, err := entropy()
	if err != nil {
		os.Exit(1)
	}
	fmt.Println("bytes = ", e)
	fmt.Println("bytes = ", string(e))
}

func chunk(i int) []uint16 {
	return nil
}

func generateNumbers(n int) []int {
	return nil
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

	dst := make([]byte, 16)
	_, err = hex.Decode(dst, reg.ReplaceAll(body, nil))
	if err != nil {
		return nil, err
	}
	return dst, nil
}

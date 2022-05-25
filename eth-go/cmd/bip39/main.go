package main

import (
	"encoding/hex"
	"fmt"
	"io"
	"net/http"
	"os"
	"regexp"
	"time"
	"unsafe"
)

func main() {
	g := "264F46DCB388AB1744B5722CC66C94573"
	fmt.Println(len(g))
	x := byte(189)
	start := time.Now()
	p, q := split(x)
	took := time.Now().Sub(start)
	fmt.Printf("x = %d, %b\n", x, x)
	fmt.Printf("p = %d, %b\n", p, p)
	fmt.Printf("q = %d, %b\n", q, q)
	fmt.Printf("took %dns\n", took.Nanoseconds())
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

func split(x byte) (byte, byte) {
	n := 4
	var p, q byte
	for i := 0; i < n; i++ {
		p = p | (x&0x80)>>7
		x = x << 1
		if i < n-1 {
			p = p << 1
		}
	}
	for i := 0; i < n; i++ {
		q = q | (x&0x80)>>7
		x = x << 1
		if i < n-1 {
			q = q << 1
		}
	}
	return p, q
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

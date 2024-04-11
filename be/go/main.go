package main

import (
	"flag"
	"fmt"

	"github.com/ProlificLabs/captrivia-be/server"
)

var (
	listen string
)

func main() {
	flag.StringVar(&listen, "listen", "localhost:8080", "Listen address")
	flag.Parse()

	gameServer := server.NewGameServer()
	httpServer := server.NewHTTPServer(listen, gameServer)

	err := httpServer.ListenAndServe()
	if err != nil {
		fmt.Println("listen failed", err) // todo, better logging and error handling
	}
}

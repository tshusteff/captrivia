package server

import "net/http"

func NewRouter(gameServer *GameServer) *http.ServeMux {
	mux := http.NewServeMux()

	mux.HandleFunc("GET /leaderboard", gameServer.Leaderboard) // Get global leaderboard

	return mux
}

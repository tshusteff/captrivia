package server

import (
	"encoding/json"
	"net/http"
)

type GameServer struct {
}

func NewGameServer() *GameServer {
	return &GameServer{}
}

// Leaderboard writes the global leaderboard to the response.
func (g *GameServer) Leaderboard(w http.ResponseWriter, r *http.Request) {
	// TODO: Fix this data so it is not hardcoded, and is the right shape
	// that the frontend expects, or maybe not worry about leaderboards at all!
	leaderboard := []struct {
		Name  string
		Score float64
	}{
		{"Alice", 89.79},
		{"John", 65.35},
		{"James", 15.92},
		{"Janice", 3.14},
	}
	writeJSON(w, http.StatusOK, leaderboard)
}

func writeJSON(w http.ResponseWriter, statusCode int, obj any) error {
	b, err := json.Marshal(obj)
	if err != nil {
		return err
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(statusCode)
	// TODO: Handle this error
	_, err = w.Write(b)
	return err
}

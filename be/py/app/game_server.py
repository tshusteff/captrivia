import json
from flask import jsonify

class GameServer:
    def leaderboard(self):
        # TODO: Fix this data so it is not hardcoded, and is the right shape
        # that the frontend expects, or maybe not worry about leaderboards at all!
        leaderboard = [
            {"Name": "Alice", "Score": 89.79},
            {"Name": "John", "Score": 65.35},
            {"Name": "James", "Score": 15.92},
            {"Name": "Janice", "Score": 3.14},
        ]
        return jsonify(leaderboard), 200

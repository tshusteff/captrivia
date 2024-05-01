from .game_server import GameServer

def register_routes(app, game_server: GameServer):
    @app.route('/leaderboard', methods=['GET'])
    def leaderboard():
        return game_server.leaderboard()

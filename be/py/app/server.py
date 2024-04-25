from flask import Flask
from flask_cors import CORS
from .router import register_routes
from .game_server import GameServer

def create_app():
    app = Flask(__name__)
    CORS(app)

    game_server = GameServer()
    register_routes(app, game_server)

    return app

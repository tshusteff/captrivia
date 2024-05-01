import argparse
from app.server import create_app

def parse_arguments():
    parser = argparse.ArgumentParser(description='Captrivia Backend Server')
    parser.add_argument('--host', default='localhost', help='Host to listen on')
    parser.add_argument('--port', type=int, default=8080, help='Port to listen on')
    return parser.parse_args()

if __name__ == '__main__':
    args = parse_arguments()
    app = create_app()
    app.run(host=args.host, port=args.port)

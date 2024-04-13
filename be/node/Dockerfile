# TODO: Consider making this Dockerfile nicer or adding a multistage build.

FROM node:20.12.2-alpine3.18
WORKDIR /app

# Copy source
COPY . .

# Install dependencies and build the server
RUN npm install && \
    npm run build

EXPOSE 8080

# Start up the server
CMD ["node", "build/index.js"]

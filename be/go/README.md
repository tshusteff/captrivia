# go

A starter backend for captrivia in Go. Make sure to grab the questions from
the directory above.

The dockerfile is meant for development in case you prefer to develop inside
Docker. Just build & run with the following:

```bash
docker build . --tag captrivia-be
docker run -it --publish "8080:8080" --mount "type=bind,source=${PWD},target=app" captrivia-be
```

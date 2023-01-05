### to run

```
ENVIRONMENT=DEV go run main.go
ENVIRONMENT=PROD bin/sillycat-authcenter-golang
```

### build on local

```
go build -o bin/sillycat-authcenter-golang -v ./
```

### generate swagger docs
```
swag init
```

### swagger pages

http://localhost:8080/docs/index.html#/

### clean the <none>

```
docker rmi $(docker images | grep "<none>" | awk "{print \$3}")
```

### Possible commands

```
go install github.com/swaggo/swag/cmd/swag@latest

go mod tidy

go mod tidy -compat=1.17
```
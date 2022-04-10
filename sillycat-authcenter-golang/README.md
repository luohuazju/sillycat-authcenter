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

FROM golang:1.17.8 as builder

ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

WORKDIR /build
COPY . .
RUN go mod download
RUN go get github.com/swaggo/swag/cmd/swag
RUN swag init
RUN go build -o sillycat-authcenter-golang -v ./

FROM scratch
COPY ./conf /conf
COPY --from=builder /build/sillycat-authcenter-golang /
COPY --from=builder /build/docs /
ENTRYPOINT ["/sillycat-authcenter-golang"]
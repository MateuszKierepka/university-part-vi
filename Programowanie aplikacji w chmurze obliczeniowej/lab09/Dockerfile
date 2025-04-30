# etap budowania
FROM golang:1.23-alpine AS builder

# utworzenie katalogi roboczego
WORKDIR /app

# skopiowanie kodu i plików .go
COPY main.go . go.* ./

# kompilacja aplikacji
RUN go build -o app main.go

FROM alpine:3.18

# imie i nazwisko zgodnie ze standardem oci
LABEL org.opencontainers.image.authors="Mateusz Kierepka"

# instalacja curl'a
RUN apk --no-cache add curl

WORKDIR /app

# kopiowanie skompilowanej aplikacji i 
COPY --from=builder /app/app .

# kopiowanie plików html i css
COPY templates ./templates

# wyeksponowanie portu
EXPOSE 8080

# healthcheck
HEALTHCHECK --interval=10s --timeout=1s \
  CMD curl -f http://localhost:8080/ || exit 1

# uruchomienie aplikacji
CMD ["./app"]
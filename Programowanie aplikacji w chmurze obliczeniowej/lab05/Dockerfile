# stage 1
FROM scratch AS s
ADD alpine-minirootfs-3.21.3-x86_64.tar /

WORKDIR /usr/app

ARG VERSION=1.0.0
RUN echo '#!/bin/sh' > app.sh && \
    echo 'echo "<html><body>"' >> app.sh && \
    echo 'echo "Adres IP serwera: $(hostname -i)<br>"' >> app.sh && \
    echo 'echo "Nazwa serwera: $(hostname)<br>"' >> app.sh && \
    echo 'echo "Wersja aplikacji: ${VERSION}"' >> app.sh && \
    echo 'echo "</body></html>"' >> app.sh && \
    chmod +x app.sh && \
    ./app.sh > index.html

# stage 2
FROM nginx:latest
WORKDIR /usr/share/nginx/html

COPY --from=s /usr/app/index.html /usr/share/nginx/html/index.html

EXPOSE 80

HEALTHCHECK --interval=10s --timeout=1s \
  CMD curl -f http://localhost:80/ || exit 1
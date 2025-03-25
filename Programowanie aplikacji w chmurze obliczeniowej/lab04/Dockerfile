# pobranie obrazu Ubuntu w najnowszej wersji
FROM ubuntu:latest

# podanie autora 
LABEL surname="Kierepka" email="s99573@pollub.edu.pl"

# system Ubuntu ma być zaktualizowany, instalacja serwera HTTP Apache w najnowszej wersji
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y apache2 && \
    rm -rf /var/lib/apt/lists/*

# skopiowanie dowolnej strony WWW do serwera Apache
COPY index.html /var/www/html/

# Expose the port for access
EXPOSE 80/tcp

# Uruchomienie serwera Apache
ENTRYPOINT ["apache2ctl"]
CMD ["-D", "FOREGROUND"]
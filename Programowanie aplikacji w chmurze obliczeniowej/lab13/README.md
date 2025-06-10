# Realizacja zadania nieobowiązkowego z laboratorium 12

### Zbudowano plik docker-compose.yml, który uruchamia stack LEMP wraz z phpMyAdmin, wykorzystując Docker Secrets do zarządzania danymi wrażliwymi.

Stack LEMP składa się z następujących usług składowych:

- L – dla Linux
- E – dla Nginx
- M – dla MySQL
- P – dla PHP

Aplikacja zawiera cztery kontenery (mikrousługi):

- jeden kontener dla Nginx
- jeden kontener dla PHP (PHP-FPM)
- jeden kontener dla MySQL
- jeden kontener dla phpMyAdmin

### Zrealizowane założenia:

- Wykorzystano obrazy ze zdefiniowanym tag-iem z DockerHub
- Skonfigurowano sieci: PHP i MySQL w sieci backend, Nginx i phpMyAdmin w sieciach backend i frontend
- Nginx został skonfigurowany na porcie 4001
- Skonfigurowano wyświetlanie strony startowej php (index.php)
- phpMyAdmin został skonfigurowany na porcie 6001 z możliwością logowania i zarządzania bazą danych
- Dane wrażliwe (hasła) są przechowywane jako Docker Secrets
- Dane niewrażliwe (nazwa bazy danych, użytkownik) są przechowywane jako zmienne środowiskowe

### Przebieg realizacji:

1. Utworzenie plików z danymi wrażliwymi:

```bash
mkdir -p secrets
echo "root" > secrets/mysql_root_password.txt
echo "test" > secrets/mysql_password.txt
```

2. Uruchomienie stack-a LEMP:

```bash
docker compose up -d
```

3. Weryfikacja działania stack-a LEMP - sprawdzenie strony głównej:

![Strona główna LEMP](screenshots/1.png)

4. Weryfikacja działania phpMyAdmin i bazy danych:

![Panel phpMyAdmin](screenshots/2.png)

5. Zatrzymanie i usunięcie kontenerów:

```bash
docker compose down
```

### Podsumowanie realizacji:

1. Stack LEMP został poprawnie skonfigurowany i uruchomiony:

   - Nginx działa na porcie 4001
   - phpMyAdmin działa na porcie 6001
   - Strona testowa wyświetla się poprawnie

2. Konfiguracja sieci:

   - PHP i MySQL są w sieci backend
   - Nginx i phpMyAdmin są w sieciach backend i frontend
   - Wszystkie usługi są dostępne z zewnątrz na odpowiednich portach

3. Baza danych:

   - Utworzona została testowa baza `test_db`
   - Dostęp do bazy możliwy przez phpMyAdmin
   - Dane dostępowe skonfigurowane poprawnie

4. Bezpieczeństwo:

   - Hasła są przechowywane jako Docker Secrets
   - Każda usługa ma dostęp tylko do potrzebnych jej sekretów
   - Sekrety są montowane w czasie uruchomienia kontenerów
   - Dane niewrażliwe są przechowywane jako zmienne środowiskowe

5. Wszystkie kontenery zostały pomyślnie uruchomione i zatrzymane

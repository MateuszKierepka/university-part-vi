const express = require('express');
const path = require('path');
const getDate = require('./server-files/getDate');
const app = express();
const PORT = 3000;

app.use((req, res, next) => {
    const filePath = req.path;
    console.log(`${getDate()}--- Klient wysłał zapytanie o plik ${filePath}`);
    next();
});

app.use(express.static(path.join(__dirname, 'public')));

app.listen(PORT, () => {console.log(`${getDate()} === Serwer zostaje uruchomiony na porcie ${PORT}.`)});
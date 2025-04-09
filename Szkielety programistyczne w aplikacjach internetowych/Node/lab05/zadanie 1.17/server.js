const express = require('express');
const path = require('path');
const app = express();
const PORT = 3000;

app.get('/strona1', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'strona1.html'));
});

app.get('/strona2', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'strona2.html'));
});

app.get('/strona3', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'strona3.html'));
});

app.get('/strona4', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'strona4.html'));
});

app.get('/strona5', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'strona5.html'));
});

app.listen(PORT, () => {
    console.log(`Serwer działa na porcie ${PORT}.`);
});
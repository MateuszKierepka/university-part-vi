const express = require('express');
const path = require('path');
const app = express();
const PORT = 3000;

function convert(value, toRad) {
    if (toRad === 'true') {
        const radians = (value * Math.PI) / 180;
        return `${value} stopni to ${radians} radianow.`;
    } else {
        const degrees = (value * 180) / Math.PI;
        return `${value} radianow to ${degrees} stopni.`;
    }
}

app.get('/convert', (req, res) => {
    const { value, toRad } = req.query;

    if (!value || isNaN(value)) {
        return res.status(400).send('blad');
    }
    if (toRad !== 'true' && toRad !== 'false') {
        return res.status(400).send('blad');
    }

    const result = convert(parseFloat(value), toRad);
    res.send(result);
});

app.get('/convert', (req, res) => {
    const { value, toRad } = req.query;

    if (!value || isNaN(value)) {
        return res.status(400).send('Błąd: Musisz podać wartość liczbową w parametrze "value".');
    }
    if (toRad !== 'true' && toRad !== 'false') {
        return res.status(400).send('Błąd: Musisz podać parametr "toRad" jako "true" lub "false".');
    }

    const result = convert(parseFloat(value), toRad);
    res.send(result);
});

app.get('/background', (req, res) => {
    const { bg } = req.query;
    const backgroundColor = bg || 'white';
    const html = `
        <!DOCTYPE html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    background-color: ${backgroundColor};
                    color: black;
                }
            </style>
        </head>
        <body>
            Kolor tła: ${backgroundColor}
        </body>
        </html>
    `;

    res.send(html);
});

app.listen(PORT, () => {console.log(`Serwer działa na porcie ${PORT}.`)});
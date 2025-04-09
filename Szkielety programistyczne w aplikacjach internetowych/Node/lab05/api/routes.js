const express = require('express');
const router = express.Router();
const users = require('../users');
const isAuthorized = require('../middleware/autoryzacja');

router.get("/form", (req, res) => {
    res.sendFile(path.join(__dirname, "form.html"))
});

router.post("/result", isAuthorized, (req, res) => {
    let username = req.body.username;
    let password = req.body.password;

    if (!username || !password) {
        return res.send("Uzupełnij dane!");
    }

    res.send("Użytkownik: " + username + "<br>Hasło: " + password);
});

module.exports = router;
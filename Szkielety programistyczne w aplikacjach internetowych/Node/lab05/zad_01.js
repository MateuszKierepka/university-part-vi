// const express = require('express');
// const path = require('path');
// const app = express();
// const PORT = 3000;

// app.use(express.urlencoded({ extended: true }));

// app.get("/form", (req, res) => {
//     res.sendFile(path.join(__dirname, "form.html"));
// });

// app.post("/result", (req, res) => {
//     const fullname = req.body.fullname;
//     const languages = req.body.language;

//     if (!fullname || !languages) {
//         return res.send("Uzupełnij dane!");
//     }

//     const languageNames = {
//         'ru': 'rosyjski',
//         'en': 'angielski',
//         'de': 'niemiecki'
//     }
//     let langList = "";
    
//     if (Array.isArray(languages)) {
//         for (let code of languages) {
//             langList += `<li>${languageNames[code]}</li>`;
//         }
//     } else {
//         langList = `<li>${languageNames[languages]}</li>`;
//     }    
//     res.send(`
//         Użytkownik: ${fullname}<br>
//         Znajomość języków:
//         <ul>
//             ${langList}
//         </ul>
//     `);
// });

// app.listen(PORT, () => console.log(`Serwer działa na porcie ${PORT}`));

// zadanie 1.11.
// const express = require('express');
// const path = require('path');
// const { check, validationResult } = require('express-validator');
// const app = express();
// const PORT = 3000;

// app.use(express.urlencoded({ extended: true }));

// app.get("/form", (req, res) => {
//     res.sendFile(path.join(__dirname, "form.html"));
// });

// app.post("/result", [
//     check('username').trim().isLength({ min: 3 , max: 25}).withMessage('Nazwa użytkownika musi mieć od 3 do 25 znaków').stripLow().withMessage('Nazwa użytkownika zawiera niedozwolone znaki.').bail().customSanitizer(value => {
//         return value.split(' ').map(word => word.charAt(0)).join('');
//     }),
//     check('email').trim().normalizeEmail().isEmail().withMessage('Niepoprawny adres email').bail(),
//     check('age').trim().isInt({ min: 0, max: 110 }).withMessage('Wiek musi być liczbą całkowitą z zakresu 0-110 lat').bail(),
// ], (req, res) => {
//     const errors = validationResult(req)
//     if (!errors.isEmpty()) {
//         return res.status(422).json({ errors: errors.array() });
//     }
//     const username = req.body.username;
//     const email = req.body.email;
//     const age = req.body.age;
//     res.send("Użytkownik: " + username + 
//         "<br>Email: " + email + 
//         "<br>Wiek: " + age);
// });

// app.listen(PORT, () => console.log(`Serwer działa na porcie ${PORT}`));

// zadanie 1.12.
// const express = require('express');
// const path = require('path');
// const users = require('./users');
// const app = express();
// const PORT = 3000;

// let metoda = (req, res, next) => {
//     const metoda = `Metoda: ${req.method}`;
//     const sciezka = `Ścieżka: ${req.protocol}://${req.get('host')}${req.originalUrl}`;
//     res.send(`
//         <p>${metoda}</p>
//         <p>${sciezka}</p>
//     `);
// };
// app.use(metoda);
// app.use(express.json());
// app.use(express.urlencoded({ extended: true }));

// app.get('/api/users', (req, res) => {
//   res.json(users);
// });

// app.get('/api/users/:id', (req, res) => {
//     const found = users.some(user => user.id === parseInt(req.params.id))
//     if(found){
//         res.json(users.filter(user => user.id === parseInt(req.params.id)))
//     } else {
//         res.status(400).json({msg: `Użytkownik o id ${req.params.id} nie został odnaleziony`})
//     }
// });

// app.post('/api/users', (req, res) => {
//     const newUser = {
//         id: users.length + 1,
//         name: req.body.name,
//         email: req.body.email,
//         status: "aktywny"
//     }
//     if(!newUser.name || !newUser.email){
//         return res.status(400).json({msg: 'Wprowadź poprawne imię i nazwisko oraz email!'})
//     }
//     users.push(newUser)
//     res.json(users)
// });

// app.patch('/api/users/:id', (req, res) => {
//     const found = users.some(user => user.id === parseInt(req.params.id))
//     if(found){
//         const updUser = req.body
//         users.forEach(user => {
//             if(user.id === parseInt(req.params.id)) {
//                 user.name = updUser.name ? updUser.name : user.name
//                 user.email = updUser.email ? updUser.email : user.email
//                 res.json({msg: 'Dane użytkownika zaktualizowane', user})
//             }
//         })
//     } else {
//         res.status(400).json({msg: `Użytkownik o id ${req.params.id} nie istnieje!`})
//     }
// });

// app.delete('/api/users/:id', (req, res) => {
//     const found = users.some(user => user.id === parseInt(req.params.id))
//     if(found){
//         const updUser = req.body
//         users.forEach((user, index) => {
//             if(user.id === parseInt(req.params.id)) {
//                 users.splice(index, 1)
//                 res.json({msg: 'Użytkownik usunięty', users})
//             }
//         })
//     } else {
//         res.status(400).json({msg: `Użytkownik o id ${req.params.id} nie istnieje!`})
//     }
// });

// app.listen(PORT, () => console.log(`Serwer działa na porcie ${PORT}`));

// zadanie 1.14.
// const express = require('express');
// const routes = require('./api/routes')
// const app = express();
// const PORT = 3000;

// app.use(express.urlencoded({ extended: true }));

// app.use('/', routes);

// app.listen(PORT, ()=> console.log(`Serwer działa na porcie ${PORT}`));

// zadanie 1.15.
const express = require('express');
const path = require('path');
const { check, validationResult } = require('express-validator');
// const hbs = require('hbs');
const reactEngine = require ('express-react-views')
const app = express();
const PORT = 3000;

// app.set('view engine', 'hbs');
// app.set('views', path.join(__dirname, 'views'));

app.set('view engine', 'jsx')
app.set('views', path.join(__dirname, 'views'));
app.engine('jsx', reactEngine.createEngine())

app.use(express.urlencoded({ extended: true }));

app.get("/form", (req, res) => {
res.sendFile(path.join(__dirname, "form.html"));
});

app.post("/result", [
    check('username').trim().isLength({ min: 3, max: 25 }).withMessage('Nazwa użytkownika musi mieć od 3 do 25 znaków').bail(),
    check('email').trim().normalizeEmail().isEmail().withMessage('Niepoprawny adres email').bail(),
    check('age').trim().isInt({ min: 0, max: 110 }).withMessage('Wiek musi być liczbą całkowitą z zakresu 0-110 lat').bail(),
], (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() });
    }

    const { username, email, age } = req.body;

    // res.render('info', { username, email, age });
    res.render('about', { nazwisko: username, email, wiek: age });
});

app.get('/about', (req, res) => {
    res.render('about', { nazwisko: 'Nowak', email: 'nowak@gmail.com', wiek: 24 });
});

app.listen(PORT, () => console.log(`Serwer działa na porcie ${PORT}`));
const express = require("express");
const path = require("path");
const handleBars = require("handlebars");
const exphbs = require("express-handlebars");
const { allowInsecurePrototypeAccess } = require("@handlebars/allow-prototype-access");

require("./db");
const studentController = require("./controllers/StudentController");

const app = express();

app.use(express.urlencoded({
    extended: true
}));

app.set("views", path.join(__dirname, "views"));

app.engine(
    "hbs",
    exphbs.engine({
        handlebars: allowInsecurePrototypeAccess(handleBars),
        extname: "hbs",
        defaultLayout: "layout",
        layoutsDir: path.join(__dirname, "views/layouts"),
    })
);

app.set("view engine", "hbs")

app.get("/", (req, res) => {
    res.send(`
    <h3 style="text-align:center">Baza danych studentów</h3>
    <h4 style="text-align:center">Kliknij <a href="/list"> tutaj</a>, aby uzyskać dostęp do bazy.</h4>`)
})

app.get("/list", studentController.list);
app.get("/addOrEdit", studentController.renderAddOrEdit);
app.get("/:id", studentController.getStudent);
app.get("/delete/:id", studentController.deleteStudent);
app.post("/", (req, res) => {
    if (req.body._id == "") {
        studentController.insert(req, res);
    } else {
        studentController.update(req, res);
    }
});

app.listen(3000, () => {
    console.log("Serwer nasłuchuje na porcie 3000")
})
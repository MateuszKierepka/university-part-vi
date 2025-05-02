const Student = require("../models/Student");

async function insert(req, res) {
    let student = new Student();
    student.fullName = req.body.fullName;
    student.email = req.body.email;
    student.mobile = req.body.mobile;
    student.city = req.body.city;
    try {
        await student.save();
        res.redirect("/list");
    } catch (err) {
        console.log("Błąd podczas dodawania studenta: " + err);
    }
}

async function update(req, res) {
    try {
        await Student.findOneAndUpdate({ _id: req.body._id }, req.body, { new: true });
        res.redirect("list");
    } catch (err) {
        console.log("Błąd podczas aktualizowania danych: " + err);
    }
}

async function list(req, res) {
    try {
        const students = await Student.find();
        res.render("list", { list: students });
    } catch (err) {
        console.log("Błąd pobierania danych: " + err);
    }
}

async function getStudent(req, res) {
    try {
        const student = await Student.findById(req.params.id);
        res.render("addOrEdit", {
            viewTitle: "Zaktualizuj dane studenta",
            student,
        });
    } catch (err) {
        console.log("Błąd podczas pobierania danych studenta: " + err);
    }
}

async function deleteStudent(req, res) {
    try {
        await Student.findByIdAndDelete(req.params.id);
        res.redirect("/list");
    } catch (err) {
        console.log("Błąd podczas usuwania: " + err);
    }
}

function renderAddOrEdit(req, res) {
    res.render("addOrEdit", { viewTitle: "Dodaj studenta" });
}

module.exports = {
    insert,
    update,
    list,
    getStudent,
    deleteStudent,
    renderAddOrEdit,
};
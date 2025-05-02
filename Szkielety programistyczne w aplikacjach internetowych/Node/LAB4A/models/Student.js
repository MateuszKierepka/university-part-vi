const mongoose = require("../db");

const studentSchema = new mongoose.Schema({
    fullName: String,
    email: String,
    mobile: Number,
    city: String,
});

const Student = mongoose.model("Student", studentSchema);

module.exports = Student;
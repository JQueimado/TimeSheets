const mongoose = require("mongoose");
const Schema = mongoose.Schema;

let Sheet = new Schema({
    kart: String,
    date: Date,
    best_lap: Number,
    avg_lap: Number,
    laps: [String],
    dif: [String]
});

module.exports = mongoose.model("Scheet", Sheet);
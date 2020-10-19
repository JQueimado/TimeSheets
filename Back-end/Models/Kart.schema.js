const mongoose = require("mongoose");
const Schema = mongoose.Schema;

let Kart = new Schema({
    name: String,
    best_lap: Number,
    sheets: [mongoose.Types.ObjectId],
});

module.exports = mongoose.model("Kart", Kart);

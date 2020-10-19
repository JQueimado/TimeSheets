const express = require("express");
const mongose = require("mongoose");
const app = express();
const bodyParser = require("body-parser");
const cors = require("cors");

const Sheet = require("./Models/Sheet.schema");
const Kart = require("./Models/Kart.schema");
/*
.env structure

PORT
DATABASE_URI

*/

require("dotenv").config();

app.use(cors());
app.use(bodyParser.json());

const uri = process.env.DATABASE_URI;
mongose.connect(uri, {
  useNewUrlParser: true,
  useCreateIndex: true,
  useUnifiedTopology: true,
});
const connection = mongose.connection;

connection.once("open", () => {
  console.log("Connected to DataBase");

  //Routes

  //Sheet
  var sheetControler = require("./Controllers/SheetControler.controler")
  app.use('/sheet', sheetControler)

  //Kart
  var kartControler = require("./Controllers/KartControler.controler")
  app.use('/kart', kartControler)

  const PORT = parseInt(process.env.PORT);
  app.listen(PORT, function () {
    console.log("Server is running on Port: " + PORT);
  });
});
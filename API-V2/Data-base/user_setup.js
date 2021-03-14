//run as root

//Connection
con = new Mongo("mongodb://root:rootpassword@localhost");

try{
  db = con.getDB("time-sheets");

  db.createCollection("temp");

  db.createUser({
    user: "devuser",

    pwd: "devuser",

    roles: ["readWrite", "dbAdmin"],

    mechanisms: ["SCRAM-SHA-1"],
  });

}catch (expt) {}

/*
Connect: mongodb://devuser:devuser@database/time-sheets
*/

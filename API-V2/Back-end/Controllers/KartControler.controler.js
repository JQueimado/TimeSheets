const router = require("express").Router()

const Sheet = require("../Models/Sheet.schema");
const Kart = require("../Models/Kart.schema");

function toString(time_ms){
    const mint_ms = (1000 * 60);
    const sec_ms = 1000;

    var miliseconds = time_ms % sec_ms
    time_ms -= miliseconds;

    var seconds = (time_ms % mint_ms)
    time_ms -= seconds
    seconds /= sec_ms

    var minutes = time_ms / mint_ms;

    return `${minutes}.${seconds}.${miliseconds}`
}

router.route("/").get( (request, response) => {
    Kart.find( (err, karts) => {
        if (err){
            response.status(404).json({ text: "No Karts found"});
        }else{
            var send =[];
            karts.map( (kart, i) => {
                var temp = {
                    sheets: kart.sheets,
                    name: kart.name,
                    best_lap: toString(kart.best_lap)
                }
                send.push(temp);
            })

            response.status(200).json(send);
        }
    } )
} )

router.route("/name").get( (request, response) => {
    Kart.find( (err, karts) => {
        if (err){
            response.status(404).json({ text: "No Karts found"});
        }else{
            var send =[];
            karts.map( (kart, i) => {
                send.push(kart.name);
            })

            response.status(200).json(send);
        }
    } )
} )

module.exports = router;
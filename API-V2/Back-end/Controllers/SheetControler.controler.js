const router = require("express").Router()

const Sheet = require("../Models/Sheet.schema");
const Kart = require("../Models/Kart.schema");

const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
];

function toMiliseconds(time) {
    var arr = time.split(".")
    
    let res = parseInt(arr[2]);
    res = res + parseInt(arr[1]) * 1000;
    res = res + parseInt(arr[0]) * 1000 * 60;
    return res;
}

function toString(time_ms){
    var time_ms = parseInt(time_ms)
    const mint_ms = (1000 * 60);
    const sec_ms = 1000;

    var miliseconds = time_ms % sec_ms
    time_ms -= miliseconds;

    var seconds = (time_ms % mint_ms)
    time_ms -= seconds
    seconds /= sec_ms

    var minutes = time_ms / mint_ms;

    return `${minutes >= 10 ? minutes: "0"+minutes}.${seconds >= 10 ? seconds : "0"+ seconds }.${miliseconds === 0? "000":miliseconds}`
}

function formatDate(date) {
    date.setTime(date.getTime() + (1*60*60*1000))
    return `${monthNames[date.getMonth()]}${date.getDate()}-${date.getHours()}:${date.getMinutes()}`
}
/*
-add
    {
        kart: String
        date: Date
        laps: [String]
    }
*/
router.route("/add").post((request, response) => {
    var best_lap = null;
    const kart_name = request.body.kart.toLowerCase()
    
    var avg = 0;
    var prev_lap = 0;
    var dif = [];
    request.body.laps.map((lap,i) => {
        var conv = toMiliseconds(lap);
        if( best_lap === null || best_lap > conv)
            best_lap = conv;
        
        if( i === 0 )
            dif.push("+0");
        else{
            var diferance = conv-prev_lap;

            if(diferance < 0 )
                diferance*=-1

            if(diferance >=1000){
                const mint_ms = (1000 * 60);
                const sec_ms = 1000;

                var miliseconds = diferance % sec_ms
                diferance -= miliseconds;
            
                var seconds = (diferance % mint_ms)
                diferance -= seconds
                seconds /= sec_ms

                var minutes = diferance / mint_ms;

                diferance= `${minutes > 0 ? minutes+"." : ""}${seconds < 10 ? "0"+seconds: seconds }.${miliseconds === 0? "000":miliseconds }`;
            }

            if( conv-prev_lap > 0 )
                dif.push("+"+diferance)
            else
                dif.push("-"+diferance)
        }

        prev_lap = conv;
        avg += conv;    
    });

    avg = avg/request.body.laps.length;

    let sheet = new Sheet({
        kart: kart_name,
        date: request.body.date ? new Date(request.body.date) : new Date(),
        best_lap: best_lap,
        avg_lap: avg,
        laps: request.body.laps,
        dif: dif
    });

    sheet
    .save()
    .then((sheet) =>{ 
        Kart.findOne({name: kart_name }, (err, kart) =>{
            if (err) console.log(err);
            if(!kart)
            {
                new Kart({ 
                    name: kart_name,
                    best_lap:sheet.best_lap,
                    sheets:[ sheet.id ]
                })
                .save();
            }
            else
            {
                kart.sheets.push(sheet.id);
                kart.save();
            }
        }).then(() => response.status(200).json({
            id: sheet.id
        }))

    })
    .catch((err) => {
        response.status(400).json({text: "Error creating sheet"});
        console.log(err);
    });

});

//Remove
router.route("/remove/:id").delete((request, response) => {
    Sheet.findByIdAndDelete(request.params.id)
    .then((_) =>
    response.status(200).json({
        text: "Removed Sucssesfully"
    })
    )
    .catch((err) =>
    response.status(400).json({
        text: "Error removing sheet"
    })
    );
});

//Edit
router.route("/edit/:id").post( (request,response) => {
    Sheet.findById(request.params.id, (sheet) => {
        sheet.date = new Date(request.body.date)
    });
})

//Get All
router.route("/").get((request, response) => {
    Sheet.find((err, sheets) => {
        if (err) 
            response.status(400).json({
                text: "Error Geting sheets"
            });
        else {
            var send = [];
            sheets.map( (sheet,i) => {
                var temp ={ 
                    laps: sheet.laps,
                    kart: sheet.kart,
                    date: formatDate( sheet.date ),
                    best_lap: toString(sheet.best_lap),
                    avg_lap: toString(sheet.avg_lap),
                    id: sheet.id,
                    diferance: sheet.dif
                }
                send.push(temp);
            } );
            response.status(200).json(send);
        };
    });
});

//Get One
router.route("/:id").get((request, response) => {
    Sheet.findById(request.params.id, (_,sheet) => {
        if (!sheet) response.status(404).json({
            text: "Sheet not found"
        });
        else {
            let send = {
                kart: sheet.name,
                date: sheet.date,
                best_lap: toString(sheet.best_lap),
                avg_lap: toString(sheet.avg_lap),
                laps: sheet.laps,
                diferance: sheet.dif
            }
            response.status(200).json(send);
        }
    });
});

module.exports = router;

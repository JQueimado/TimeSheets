import React from 'react';
import Table from "react-bootstrap/Table";

import "../Styles/Sheet.css"

const Sheet = props => (
    <div className="sheet-container" >
        <Table striped bordered hover size="sm" variant="dark">
            <thead>
                <tr>
                    <th>Date</th>
                    <td> {props.data.date} </td>
                </tr>
            </thead>
            <thead>
                <tr>
                    <th>Kart</th>
                    <th>BestLap</th>
                    <th>AverageLap</th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td>{props.data.kart}</td>
                <td>{props.data.best_lap}</td>
                <td>{props.data.avg_lap}</td>
            </tr>
            </tbody>

            <thead>
            <tr>
                <th>Lap</th>
                <th>Time</th>
                <th>Diferance</th>
            </tr>
            </thead>
            <tbody>

                { props.data.laps.map( (lap, i) =>{return( 
                    <tr>  
                        <td>{i+1}</td>
                        <td>{lap}</td>
                        <td>{props.data.diferance[i]}</td>
                    </tr> 
                )})}

            </tbody>
        </Table>
    </div>
);
 
export default Sheet;
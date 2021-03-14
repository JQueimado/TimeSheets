import React, { Component } from 'react';
import Sheet from "./Sheet";
import axios from "axios";

import "../Styles/Home.css"

class Home extends Component {
    state = { sheets:[] }

    componentDidMount(){
        axios
            .get("http://localhost:3001/sheet")
            .then( response => { this.setState({sheets: response.data }) } )
            .catch( err => console.log(err));
    }

    render() { 
        return ( 
        <div className="sheets-display">

            {this.state.sheets.map( ( sheet, i ) => { return <Sheet data={sheet} /> } )}

        </div> );
    }
}
 
export default Home;
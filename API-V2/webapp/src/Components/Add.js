import React, { Component } from 'react';
import {Form,Row,Col,Table,Button} from "react-bootstrap";
import DatePicker from "react-datepicker";
import axios from "axios";
import "react-datepicker/dist/react-datepicker.css";

import "../Styles/Add.css"

class Add extends Component {

    state = { 
        karts:[],
        new_kart:false,
        kart_select:"",
        date: new Date(),
        laps:[""],
        
        id:"",
        err: false,

        new_kart_name:"",
        new_kart_number:""
    }

    constructor(props){
        super(props);

        this.on_kart_change = this.on_kart_change.bind(this);
        this.on_date_change = this.on_date_change.bind(this);
        this.on_add_Click = this.on_add_Click.bind(this);
        this.on_edit_change = this.on_edit_change.bind(this);
        this.on_delete_click = this.on_delete_click.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.on_hp_change = this.on_hp_change.bind(this);
        this.on_n_change = this.on_n_change.bind(this);
    }

    componentDidMount(){
        axios.get("http://localhost:3001/kart/name")
            .then( (response) => this.setState({ karts: response.data }) )
            .catch((err) => console.log(err) );
    }

    on_kart_change(event){
        var form = event.currentTarget;
        var temp = { kart_select: form.value , new_kart:false }

        if(form.value ==="New +" )
            temp.new_kart = true;

        if(form.value ==="idk")
            temp.kart_select = "none"

        if(form.value ==="Select Kart")
            temp.kart_select = ""

        this.setState(temp);
    }

    on_date_change(date){
        this.setState({date: date})
    }

    on_add_Click(event){
        var temp = this.state.laps;
        temp.push("");
        this.setState({laps:temp})
    }

    on_edit_change(event, i){
        var temp = this.state.laps;
        var value = event.currentTarget.value;

        value = value.split(".").join("")

        var v = parseInt(value);
        value = v.toString();

        while(value.length < 7)
            value = '0'+value;

        value = value.substring(0,2) +"."+value.substring(2,4)+"."+value.substring(4,8)

        temp[i] = value;
        this.setState({laps:temp})
    }

    on_delete_click(event, i){
        var temp = this.state.laps;
        console.log(i)
        temp.splice(i,1);
        this.setState({ laps: temp })
    }

    handleSubmit(event){

        var kart_name = `${this.state.new_kart_name}-${this.state.new_kart_number}`

        var body ={ 
            date: this.state.date.toString(),
            kart: this.state.new_kart ? kart_name : this.state.kart_select,
            laps:this.state.laps
            }

        axios
            .post("http://localhost:3001/sheet/add",body)
            .then((response) => { this.setState({ id: response.data.id, err:false}) })
            .catch( (err) => this.setState({ id: "", err:true}))

    }

    on_hp_change(event){
        var value = event.currentTarget.value;
        value = value.split("cc").join("")+"cc"

        this.setState({ new_kart_name:value })
    }

    on_n_change(event){
        this.setState({ new_kart_number: event.currentTarget.value })
    }

    render() {
        return ( 
        <div className="form-container"> 
            <Form>
                <Form.Label title> Add Sheet </Form.Label>
                
                <Form.Group as={Row}>
                    
                    <Form.Label column sm="1"> Kart </Form.Label>
                    
                    <Col sm="2">
                        <Form.Control as="select" value={this.state.kart_select} onChange={this.on_kart_change} placeholder="Select A kart" required>
                        <option>Select Kart</option>
                        {this.state.karts.map( (kart,i) => <option> {kart} </option> )}
                        <option>idk</option>
                        <option>New +</option>
                        </Form.Control>
                    </Col>
                    
                    { this.state.new_kart ? 
                        <Form.Group as={Row}>
                            <Form.Label column sm="2" > Hp: </Form.Label>
                            <Col > 
                                <Form.Control value={this.state.new_kart_name} onChange={this.on_hp_change} type="text" placeholder="ex:50cc" />
                            </Col>
                            <Form.Label column sm="2" > N: </Form.Label>
                            <Col > 
                                <Form.Control  value={this.state.new_kart_number} onChange={this.on_n_change} type="text" placeholder="ex:13" />
                            </Col> 
                        </Form.Group >
                    : null}

                </Form.Group>
                
                <Form.Group as ={Row}>
                    <Form.Label column sm="1"> Date </Form.Label>
                    <Col>
                        <DatePicker 
                            selected={this.state.date} 
                            onChange={this.on_date_change}
                            showTimeSelect
                            dateFormat="Pp"
                            locale="pt"
                        ></DatePicker>
                    </Col>
                </Form.Group>
                
                <Form.Group >
                    <Table className="add-table-container">
                        <thead>
                            <tr>
                                <th>Lap</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.laps.map( (laps,i) => 
                                <tr>
                                    <td>{i+1}</td>
                                    <td>
                                        <Form.Control type="text" onKeyPress={(target) => { if(target.charCode===13) this.on_add_Click() } } onChange={ (event) => this.on_edit_change(event,i) } value={this.state.laps[i]} placeholder="00.00.000" required></Form.Control>
                                    </td>
                                    <td> <Button variant="danger" onClick={(event) => this.on_delete_click(event,i) } disabled={this.state.laps.length <= 1} >Delete</Button> </td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                    <Button variant="dark" className="add-table-add-button" onClick={this.on_add_Click} > + </Button>
                </Form.Group>

                <Form.Group as={Row}> 
                     <Col sm="1" > <Button variant="dark" onClick={this.handleSubmit}> Done </Button> </Col>
                     { this.state.id !== "" ? <Col> <Form.Label> Sucssesfuly Added </Form.Label> </Col> :null }
                     { this.state.err ? <Col> <Form.Label> Error! </Form.Label> </Col> :null }
                </Form.Group>

            </Form>
        </div> );
    }
}
 
export default Add;
import React from 'react';
import {Navbar, Nav} from 'react-bootstrap';
import Link from 'react-router-dom/Link';

const NavigationBar = () => {
    return ( 
    <Navbar bg="dark" variant="dark">
        <Link to="/"> <Navbar.Brand > Time Sheets  </Navbar.Brand> </Link>
        <Nav className="mr-auto">
            <Nav.Link >Kart</Nav.Link>
            <Link to="/add"> <Nav.Link href="/add" >Add</Nav.Link> </Link>
            <Nav.Link >Search</Nav.Link>
        </Nav>
    </Navbar> );
}
 
export default NavigationBar;
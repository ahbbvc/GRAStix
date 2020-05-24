import React, { Component } from 'react'
import { Navbar } from 'react-bootstrap';

class Header extends Component {
    render() {
        return (
            <div>
                <Navbar bg="primary" variant="dark">
                    <Navbar.Brand>GRAStix</Navbar.Brand>
                </Navbar>
            </div>
        )
    }
}

export default Header;




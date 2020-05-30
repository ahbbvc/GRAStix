import React, { Component } from "react";
import { Navbar } from "react-bootstrap";
import logo from "./logo.png";

class Header extends Component {
    render() {
        return (
            <div>
                <Navbar bg="primary" variant="dark">
                    <img src={logo} alt="logo" style={{ marginRight: "10px", width: "40px" }} />
                    <Navbar.Brand>GRAStix</Navbar.Brand>
                </Navbar>
            </div>
        );
    }
}

export default Header;

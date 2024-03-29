import React, { Component } from "react";
import { Navbar } from "react-bootstrap";
import logo from "./logo.png";

class Header extends Component {
    logout = () => {
        localStorage.removeItem("access_token");
    }

    render() {
        return (
            <div>
                <Navbar bg="primary" variant="dark" style={{ justifyContent: "space-between" }}>
                    <div>
                        <img src={logo} alt="logo" style={{ marginRight: "10px", width: "40px" }} />
                        <Navbar.Brand>GRAStix</Navbar.Brand>
                    </div>
                    <div>
                        <Navbar.Text>
                            <a href="/profile" style={{ marginRight: "10px" }}>Profile</a>
                        </Navbar.Text>
                        <Navbar.Text>
                            <a href="/" onClick={this.logout}>Log Out</a>
                        </Navbar.Text>
                    </div>
                </Navbar>
            </div>
        );
    }
}

export default Header;

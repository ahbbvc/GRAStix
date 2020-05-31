import React, { Component } from "react";
import "./HomePage.css";
import Login from "./Login.js";
import Register from "./Register.js";

class HomePage extends Component {
    state = {
    };

    render() {
        return (
            <div className="home-container">
                <Login></Login>
                <Register></Register>
            </div>
        );
    }
}

export default HomePage;

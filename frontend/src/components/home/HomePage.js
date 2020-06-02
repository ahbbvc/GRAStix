import React, { Component } from "react";
import "./HomePage.css";
import Login from "./Login.js";
import Register from "./Register.js";
import axios from "axios";

class HomePage extends Component {
    state = {
        accessToken: "",
        refreshToken: ""
    };

    markLogin = (loginRes, userEmail) => {
        this.setState({
            accessToken: loginRes.data.access_token,
            refreshToken: loginRes.data.refresh_token
        });

        axios.get(`http://localhost:8762/users/user?email=${userEmail}`,
            {
                headers: {
                    'Authorization': `Bearer ${this.state.accessToken}`
                }
            }
        ).then((response) => {
            this.setState({ user: response.data });
            sessionStorage.setItem('access_token', this.state.accessToken);
            if (this.state.user.status === 'admin') this.props.history.push('/admin');
            else this.props.history.push('/tickets');
        }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request.",
                alertVisible: true,
                alertColor: "danger",
            });
        });
    }

    render() {
        return (
            <div className="home-container">
                <Login markLogin={this.markLogin}></Login>
                <Register></Register>
            </div>
        );
    }
}

export default HomePage;

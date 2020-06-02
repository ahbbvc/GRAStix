import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";
import "./HomePage.css";

class Login extends Component {
    state = {
        alertVisible: false,
        alertMessage: "",
        alertColor: "",
        email: "",
        password: ""
    };

    handleEmail = (e) => {
        this.setState({ email: e.target.value });
    };

    handlePassword = (e) => {
        this.setState({ password: e.target.value });
    };

    handleSubmit = (e) => {
        if (!this.loginHasError()) {
            e.preventDefault();
            this.handleLogin();
        } else {
            this.setState({
                alertMessage: "Error! All fields must be provided.",
                alertColor: "danger",
                alertVisible: true,
            });
        }
    };

    handleLogin = () => {

        this.setState({ alertVisible: false });
        axios.post("http://localhost:8762/login", this.state).then((response) => {
            this.props.markLogin(response, this.state.email);
        }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request.",
                alertVisible: true,
                alertColor: "danger",
            });
        });
    };

    loginHasError = () => {
        return !this.state.email || !this.state.password;
    };

    toggle = () => {
        this.setState({ alertVisible: !this.state.alertVisible });
    };

    render() {
        return (
            <div>
                <Alert
                    variant={this.state.alertColor}
                    dismissible
                    show={this.state.alertVisible}
                    onClose={this.toggle}>
                    {this.state.alertMessage}
                </Alert>
                <Card className="card-home">
                    <Card.Body>
                        <Card.Title>Login</Card.Title>
                        <Form>
                            <Form.Group controlId="loginEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder="Email"
                                    onChange={this.handleEmail}
                                />
                            </Form.Group>

                            <Form.Group controlId="loginPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Password"
                                    onChange={this.handlePassword}
                                />
                            </Form.Group>

                            <Button className="home-button" onClick={this.handleSubmit}>Login</Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}

export default Login;

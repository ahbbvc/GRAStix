import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";
import "./HomePage.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class Register extends Component {
    state = {
        alertVisible: false,
        alertMessage: "",
        alertColor: "",
        form: {
            firstName: "",
            lastName: "",
            email: "",
            birthDate: new Date(),
            password: "",
            confirmPassword: "",
            status: "student"
        }
    };

    handleChange = (e) => {
        this.setState({ form: { ...this.state.form, status: e.target.value } });
    };

    handleValueChange = (event) => {
        let name = event.target.name;
        let val = event.target.value;
        this.setState({ form: { ...this.state.form, [name]: val } });
    }

    handleBirthdate = (date) => {
        this.setState({ form: { ...this.state.form, birthDate: date } });
    }

    handleSubmit = (e) => {
        if (!this.formHasError()) {
            this.handleRegister();
            e.preventDefault();
        }
    };

    handleRegister = () => {
        this.setState({ alertVisible: false });
        axios.post("http://localhost:8762/users/user/add", this.state.form).then(() => {
            this.setState({
                alertMessage: "Registration is successful, you can now login.",
                alertVisible: true,
                alertColor: "success",
            });
        }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request. " + error.response?.data,
                alertVisible: true,
                alertColor: "danger",
            });
        });
    };

    formHasError = () => {
        let message = "";
        let hasError = false;
        let emailRegex = new RegExp("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}");

        if (!this.state.form.firstName ||
            !this.state.form.lastName ||
            !this.state.form.email ||
            !this.state.form.birthDate ||
            !this.state.form.password ||
            !this.state.form.confirmPassword) {
            message += "All fields are required.";
            hasError = true;
        }

        if (this.state.form.email && !emailRegex.test(this.state.form.email)) {
            message += " Email is in wrong format."
            hasError = true;
        }

        if (this.state.form.password != this.state.form.confirmPassword) {
            message += " Passwords must match.";
            hasError = true;
        }

        if (hasError) {
            this.setState({
                alertMessage: "Error! " + message,
                alertColor: "danger",
                alertVisible: true,
            });
            return true;
        }

        return false;
    };

    toggle = () => {
        this.setState({ alertVisible: !this.state.alertVisible });
    };

    render() {
        return (
            <div>
                <Alert
                    className="alert-admin"
                    variant={this.state.alertColor}
                    dismissible
                    show={this.state.alertVisible}
                    onClose={this.toggle}>
                    {this.state.alertMessage}
                </Alert>
                <Card className="card-home">
                    <Card.Body>
                        <Card.Title>Register</Card.Title>
                        <Form>
                            <Form.Group>
                                <Form.Label>First name</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="First Name"
                                    name="firstName"
                                    onChange={this.handleValueChange}
                                />
                            </Form.Group>

                            <Form.Group>
                                <Form.Label>Last name</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Last Name"
                                    name="lastName"
                                    onChange={this.handleValueChange}
                                />
                            </Form.Group>

                            <Form.Group className="flex-container-admin">
                                <Form.Label>Birth date</Form.Label>
                                <DatePicker
                                    className="datepicker-admin"
                                    dateFormat="dd-MM-yyyy HH:mm"
                                    name="birthDate"
                                    selected={this.state.form.birthDate}
                                    onChange={this.handleBirthdate}
                                    maxDate={new Date()}
                                ></DatePicker>
                            </Form.Group>

                            <Form.Group controlId="registerEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    placeholder="Email"
                                    name="email"
                                    onChange={this.handleValueChange}
                                />
                            </Form.Group>

                            <Form.Group controlId="registerPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Password"
                                    name="password"
                                    onChange={this.handleValueChange}
                                />
                            </Form.Group>

                            <Form.Group controlId="confirmPassword">
                                <Form.Label>Confirm password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Confirm password"
                                    name="confirmPassword"
                                    onChange={this.handleValueChange}
                                />
                            </Form.Group>

                            <Form.Group controlId="status">
                                <Form.Label>Status</Form.Label>
                                <Form.Control name="status" as="select" onChange={this.handleChange}>
                                    <option value="student">Student</option>
                                    <option value="radnik">Worker</option>
                                    <option value="penzioner">Pensioner</option>
                                </Form.Control>
                            </Form.Group>

                            <Button className="home-button" onClick={this.handleSubmit}>Register</Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}

export default Register;

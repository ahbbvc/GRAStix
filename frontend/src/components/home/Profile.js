import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";
import "./HomePage.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const config = {
    headers: { Authorization: `Bearer ${localStorage.getItem("access_token")}` },
};
const userId = localStorage.getItem("userId");

class Profile extends Component {

    constructor() {
        super();
    }

    componentDidMount() {
        this.getUserData();
    }

    state = {
        profileAlert: {
            alertVisible: false,
            alertMessage: "",
            alertColor: "",
        },
        cardAlert: {
            alertVisible: false,
            alertMessage: "",
            alertColor: "",
        },
        user: {
            firstName: undefined,
            lastName: undefined,
            email: "",
            birthDate: "",
            password: "",
            confirmPassword: "",
            status: "",
            cardNumber: undefined,
            expiryDate: ""
        }
    };

    getUserData = () => {
        axios.get(`http://localhost:8762/users/user/${userId}`, config).then(response => {
            this.setState({
                user: {
                    ...response.data,
                    birthDate: !!response.data.birthDate ? new Date(response.data.birthDate) : "",
                    expiryDate: !!response.data.expiryDate ? new Date(response.data.expiryDate) : ""
                },
                hasPaymentInfo: !!response.data.cardNumber
            });
        }).catch(() => {
            this.setState({
                profileAlert: {
                    alertMessage: "There was an error while fetching data.",
                    alertVisible: true,
                    alertColor: "danger",
                }
            });
        });
    }

    handleValueChange = (event) => {
        let name = event.target.name;
        let val = event.target.value;
        this.setState({ user: { ...this.state.user, [name]: val } });
    }

    handleBirthdate = (date) => {
        this.setState({ user: { ...this.state.user, birthDate: date } });
    }

    handleExpiryDate = (date) => {
        this.setState({ user: { ...this.state.user, expiryDate: date } });
    }

    handleChange = (e) => {
        this.setState({ user: { ...this.state.user, status: e.target.value } });
    };

    handleSubmit = (e) => {
        e.preventDefault();
        if (!this.userHasError()) {
            if (!!this.state.user.password) this.handlePasswordUpdate();
            else this.handleSave(false);
        }
    };

    handleSave = (isPaymentSubmit) => {
        if (isPaymentSubmit) this.toggleCardAlert();
        else this.toggleProfileAlert();
        axios.put(`http://localhost:8762/users/user/update/${userId}`, this.state.user, config).then((response) => {
            if (isPaymentSubmit) {
                this.setState({
                    hasPaymentInfo: !!response.data.cardNumber,
                    cardAlert: {
                        alertMessage: "Succesfully updated data.",
                        alertVisible: true,
                        alertColor: "success",
                    }
                });
            }
            else {
                this.setState({
                    profileAlert: {
                        alertMessage: "Succesfully updated data.",
                        alertVisible: true,
                        alertColor: "success",
                    }
                });
            }

        }).catch((error) => {
            if (isPaymentSubmit) {
                this.setState({
                    cardAlert: {
                        alertMessage: "There was an error while processing request.",
                        alertVisible: true,
                        alertColor: "danger",
                    }
                });
            } else {
                this.setState({
                    profileAlert: {
                        alertMessage: "There was an error while processing request.",
                        alertVisible: true,
                        alertColor: "danger",
                    }
                });
            }
        });
    }

    userHasError = () => {
        let message = "";
        let hasError = false;
        let emailRegex = new RegExp("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}");

        if (!this.state.user.firstName ||
            !this.state.user.lastName ||
            !this.state.user.email ||
            !this.state.user.birthDate) {
            message += "No field can be left empty.";
            hasError = true;
        }

        if (this.state.user.email && !emailRegex.test(this.state.user.email)) {
            message += " Email is in wrong format."
            hasError = true;
        }

        if (!!this.state.user.password && !!this.state.user.confirmPassword &&
            (this.state.user.password != this.state.user.confirmPassword)) {
            message += " Passwords must match.";
            hasError = true;
        }

        if (hasError) {
            this.setState({
                profileAlert: {
                    alertMessage: "Error! " + message,
                    alertColor: "danger",
                    alertVisible: true,
                }
            });
            return true;
        }

        return false;
    };

    handlePaymentSubmit = (e) => {
        e.preventDefault();
        let cardNumberRegex = new RegExp("^[0-9]{16}$");
        if (!this.state.user.cardNumber || !this.state.user.expiryDate || !cardNumberRegex.test(this.state.user.cardNumber)) {
            this.setState({
                cardAlert: {
                    alertMessage: "Error! Card info is wrong.",
                    alertColor: "danger",
                    alertVisible: true,
                }
            });
        } else {
            this.handleSave(true);
        }
    }

    handlePaymentRemove = (e) => {
        e.preventDefault();
        this.state.user.cardNumber = "";
        this.state.user.expiryDate = undefined;
        this.handleSave(true);
    }

    handlePasswordUpdate = () => {
        this.toggleProfileAlert();
        axios.put(`http://localhost:8762/users/user/newPassword/${userId}`, this.state.user, config).then((response) => {
            this.setState({
                profileAlert: {
                    alertMessage: "Succesfully updated data.",
                    alertVisible: true,
                    alertColor: "success",
                }
            });
        }).catch((error) => {
            this.setState({
                profileAlert: {
                    alertMessage: "There was an error while processing request.",
                    alertVisible: true,
                    alertColor: "danger",
                }
            });
        });
    }

    toggleProfileAlert = () => {
        this.setState({
            profileAlert: {
                alertVisible: false
            }
        });
    };

    toggleCardAlert = () => {
        this.setState({
            cardAlert: {
                alertVisible: false
            }
        });
    };

    render() {
        return (
            <div className="home-container">
                <div>
                    <Alert
                        className="alert-admin"
                        variant={this.state.profileAlert.alertColor}
                        dismissible
                        show={this.state.profileAlert.alertVisible}
                        onClose={this.toggleProfileAlert}>
                        {this.state.profileAlert.alertMessage}
                    </Alert>
                    <Card className="card-home">
                        <Card.Body>
                            <Form>
                                <Form.Group controlId="firstName">
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={this.state.user.firstName || ""}
                                        placeholder="First name"
                                        name="firstName"
                                        onChange={this.handleValueChange}
                                    />
                                </Form.Group>

                                <Form.Group controlId="lastName">
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={this.state.user.lastName || ""}
                                        placeholder="Last name"
                                        name="lastName"
                                        onChange={this.handleValueChange}
                                    />
                                </Form.Group>

                                <Form.Group className="flex-container-admin">
                                    <Form.Label>Birth date</Form.Label>
                                    <DatePicker
                                        className="datepicker-admin"
                                        dateFormat="dd-MM-yyyy"
                                        name="birthDate"
                                        selected={this.state.user.birthDate || ""}
                                        onChange={this.handleBirthdate}
                                        maxDate={new Date()}
                                    ></DatePicker>
                                </Form.Group>

                                <Form.Group controlId="registerEmail">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        value={this.state.user.email}
                                        placeholder="Email"
                                        name="email"
                                        onChange={this.handleValueChange}
                                    />
                                </Form.Group>

                                <Form.Group controlId="password">
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
                                    <Form.Control name="status" as="select" onChange={this.handleChange} value={this.state.user.status}>
                                        <option value="student">Student</option>
                                        <option value="radnik">Worker</option>
                                        <option value="penzioner">Pensioner</option>
                                    </Form.Control>
                                </Form.Group>

                                <Button className="home-button" onClick={this.handleSubmit}>Save</Button>
                            </Form>
                        </Card.Body>
                    </Card>
                </div>
                <div>
                    <Alert
                        className="alert-admin"
                        variant={this.state.cardAlert.alertColor}
                        dismissible
                        show={this.state.cardAlert.alertVisible}
                        onClose={this.toggleCardAlert}>
                        {this.state.cardAlert.alertMessage}
                    </Alert>
                    <Card className="card-home">
                        <Card.Body>
                            <Card.Title>Payment options</Card.Title>
                            <Form>
                                <Form.Group controlId="cardNumber">
                                    <Form.Label>Card Number</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={this.state.user.cardNumber || ""}
                                        placeholder="Card number"
                                        name="cardNumber"
                                        onChange={this.handleValueChange}
                                    />
                                </Form.Group>

                                <Form.Group className="flex-container-admin">
                                    <Form.Label>Expiry date</Form.Label>
                                    <DatePicker
                                        className="datepicker-admin"
                                        dateFormat="MM/yyyy"
                                        selected={this.state.user.expiryDate || ""}
                                        showMonthYearPicker
                                        name="expiryDate"
                                        onChange={this.handleExpiryDate}
                                        minDate={new Date()}
                                    ></DatePicker>
                                </Form.Group>

                                {
                                    this.state.hasPaymentInfo ?
                                        <React.Fragment>
                                            <div style={{ display: "flex", justifyContent: "flex-end" }}>
                                                <Button type="button" onClick={this.handlePaymentSubmit} style={{ marginRight: "10px" }}>Edit</Button>
                                                <Button type="button" onClick={this.handlePaymentRemove}>Remove</Button>
                                            </div>
                                        </React.Fragment>
                                        :
                                        <Button className="home-button" onClick={this.handlePaymentSubmit}>Add</Button>
                                }
                            </Form>
                        </Card.Body>
                    </Card>
                </div>
            </div >
        );
    }
}

export default Profile;

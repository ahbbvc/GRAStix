import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";
import "./AdminPanel.css";

const config = {
  headers: { Authorization: `Bearer ${localStorage.getItem('access_token')}` }
};

class CreateStation extends Component {
  state = {
    station: { stationName: "" },
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
  };

  handleChange = (e) => {
    this.setState({ stationName: e.target.value });
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handlePost();
      e.preventDefault();
    }
  };

  handlePost = (e) => {
    axios.post("http://localhost:8762/routes/stations", this.state, config).then(() => {
      this.setState({
        alertMessage: "Success. Station is created.",
        alertVisible: true,
        alertColor: "success",
      });
      this.props.markCreated("newStation", true);
    });
  };

  validate = (e) => {
    if (
      this.state.stationName === undefined ||
      !this.state.stationName.trim().length
    ) {
      this.setState({
        alertMessage: "Error! Station name cannot be blank.",
        alertColor: "danger",
        alertVisible: true,
      });
      return false;
    } else return true;
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
          onClose={this.toggle}
        >
          {this.state.alertMessage}
        </Alert>
        <Card className="card-admin">
          <Card.Body>
            <Card.Title>Create station</Card.Title>
            <Form>
              <Form.Group controlId="routeName">
                <Form.Label>Station name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter station name"
                  onChange={this.handleChange}
                />
              </Form.Group>
              <Button onClick={this.handleSubmit}>Create</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default CreateStation;

import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";
import "./AdminPanel.css";

class CreateRoute extends Component {
  state = {
    route: { routeName: "", transportType: "Bus" },
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
  };

  handleChangeSelect = (e) => {
    this.setState({ transportType: e.target.value });
  };

  handleChange = (e) => {
    this.setState({ routeName: e.target.value });
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handlePost();
      e.preventDefault();
    }
  };

  handlePost = (e) => {
    axios.post("http://localhost:8083/routes", this.state).then(() => {
      this.setState({
        alertMessage: "Success. Route is created.",
        alertVisible: true,
        alertColor: "success",
      });
      this.props.createdRoute.bind(this, true);
    });
  };

  validate = (e) => {
    let s = "";
    let isError = false;
    if (
      this.state.routeName === undefined ||
      !this.state.routeName.trim().length
    ) {
      s += "Route name cannot be blank.";
      isError = true;
    }
    if (this.state.transportType === undefined) {
      s += " Transport type is not selected.";
      isError = true;
    }
    if (isError) {
      this.setState({
        alertMessage: "Error! " + s,
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
            <Card.Title>Create route</Card.Title>
            <Form>
              <Form.Group controlId="routeName">
                <Form.Label>Route name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter route name"
                  onChange={this.handleChange}
                />
              </Form.Group>
              <Form.Group controlId="transportType">
                <Form.Label>Transport type</Form.Label>
                <Form.Control as="select" onChange={this.handleChangeSelect}>
                  <option value="Bus">Bus</option>
                  <option value="Tram">Tram</option>
                  <option value="Trolley">Trolley</option>
                </Form.Control>
              </Form.Group>
              <Button onClick={this.handleSubmit}>Create</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default CreateRoute;

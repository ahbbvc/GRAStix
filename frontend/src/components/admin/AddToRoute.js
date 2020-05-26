import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";

class AddToRoute extends Component {
  state = {
    routes: [],
    stations: [],
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selectedRoute: "",
    selectedStation: "",
  };

  componentDidMount() {
    axios.get("http://localhost:8083/routes").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });

    axios.get("http://localhost:8083/stations").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["stationName"]));
      this.setState({ stations: jsonString });
    });
  }

  Add = (e) => {
    let stationId = this.state.selectedStation[0].id;
    let routeId = this.state.selectedRoute[0].id;

    axios
      .post(
        "http://localhost:8083/routestations?route=" +
          routeId +
          "&station=" +
          stationId
      )
      .then(
        this.setState({
          alertMessage: "Success. Station is added to route.",
          alertVisible: true,
          alertColor: "success",
        })
      );
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.Add();
      e.preventDefault();
    }
  };

  validate = (e) => {
    return true;
  };

  toggle = () => {
    this.setState({ alertVisible: !this.state.alertVisible });
  };

  render() {
    return (
      <div style={{ alignSelf: "flex-start", justifySelf: "left" }}>
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
            <Card.Title>Add to route</Card.Title>
            <Typeahead
              className="typeahead-admin"
              id="basic-example"
              onChange={(selectedStation) => this.setState({ selectedStation })}
              placeholder="Choose a station..."
              options={this.state.stations}
            />
            <Typeahead
              className="typeahead-admin"
              id="basic-example"
              onChange={(selectedRoute) => this.setState({ selectedRoute })}
              placeholder="Choose a route..."
              options={this.state.routes}
            />
            <Form>
              <Button className="button-admin" onClick={this.handleSubmit}>
                Add
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default AddToRoute;

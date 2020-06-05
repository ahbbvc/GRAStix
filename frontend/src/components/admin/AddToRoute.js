import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";

const config = {
  headers: { Authorization: `Bearer ${localStorage.getItem('access_token')}` }
};

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
    this.fetchData();
  }

  componentDidUpdate(prevProps) {
    if (
      prevProps.newRoute !== this.props.newRoute ||
      prevProps.newStation !== this.props.newStation
    ) {
      this.fetchData();
    }
  }

  fetchData = () => {
    axios.get("http://localhost:8762/routes/routes", config).then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });

    axios.get("http://localhost:8762/routes/stations", config).then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["stationName"]));
      this.setState({ stations: jsonString });
    });
  };

  Add = (e) => {
    let stationId = this.state.selectedStation[0].id;
    let routeId = this.state.selectedRoute[0].id;

    axios
      .post(
        "http://localhost:8762/routes/routestations?route=" +
        routeId +
        "&station=" +
        stationId
        , config)
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
    let s = "";
    let isError = false;
    if (
      this.state.selectedStation === "" ||
      this.state.selectedStation.length === 0
    ) {
      s += "Station is not selected.";
      isError = true;
    }
    if (
      this.state.selectedRoute === "" ||
      this.state.selectedRoute.length === 0
    ) {
      s += " Route is not selected.";
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
            <Card.Title>Add station to route</Card.Title>
            <Form>
              <Form.Group>
                <Form.Label>Station</Form.Label>
                <Typeahead
                  className="typeahead-admin"
                  id="basic-example"
                  onChange={(selectedStation) =>
                    this.setState({ selectedStation })
                  }
                  placeholder="Choose a station..."
                  options={this.state.stations}
                />
                <Form.Label>Route</Form.Label>
                <Typeahead
                  className="typeahead-admin"
                  id="basic-example"
                  onChange={(selectedRoute) => this.setState({ selectedRoute })}
                  placeholder="Choose a route..."
                  options={this.state.routes}
                />
              </Form.Group>
              <Button onClick={this.handleSubmit}>Add</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default AddToRoute;

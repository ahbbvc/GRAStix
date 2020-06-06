import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";

const config = {
  headers: { Authorization: `Bearer ${localStorage.getItem("access_token")}` },
};

class DeleteStation extends Component {
  cons;
  state = {
    stations: [],
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selected: "",
  };

  componentDidMount() {
    this.fetchStations();
  }

  componentDidUpdate() {
    if (this.props.newStation) {
      this.fetchStations();
      this.props.markCreated("newStation", false);
    }
  }

  fetchStations = () => {
    axios.get("http://localhost:8762/routes/stations", config).then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["stationName"]));
      this.setState({ stations: jsonString });
    });
  };

  handleDelete = (e) => {
    let id = this.state.selected[0].id;
    axios
      .delete("http://localhost:8762/routes/stations/" + id, config, {})
      .then(() => {
        this.setState({
          stations: this.state.stations.filter((station) => station.id !== id),
          alertMessage: "Success. Station is deleted.",
          alertVisible: true,
          alertColor: "success",
        });
        this.props.markCreated("newStation", true);
      });
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handleDelete();
      e.preventDefault();
    }
  };

  validate = (e) => {
    if (this.state.selected === "" || this.state.selected.length === 0) {
      this.setState({
        alertMessage: "Error! Station is not selected.",
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
      <div style={{ marginTop: "20px" }}>
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
            <Card.Title>Delete station</Card.Title>
            <Form>
              <Form.Group>
                <Form.Label>Station</Form.Label>
                <Typeahead
                  id="basic-example"
                  onChange={(selected) => this.setState({ selected })}
                  placeholder="Choose a station..."
                  options={this.state.stations}
                />
              </Form.Group>
              <Button onClick={this.handleSubmit}>Delete</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default DeleteStation;

import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";

class DeleteStation extends Component {
  state = {
    stations: [],
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selected: "",
  };

  componentDidMount() {
    axios.get("http://localhost:8083/stations").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["stationName"]));
      this.setState({ stations: jsonString });
    });
  }

  handleDelete = (e) => {
    let id = this.state.selected[0].id;
    axios.delete("http://localhost:8083/stations/" + id).then(
      this.setState({
        stations: this.state.stations.filter((station) => station.id !== id),
        alertMessage: "Success. Station is deleted.",
        alertVisible: true,
        alertColor: "success",
      })
    );
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handleDelete();
      e.preventDefault();
    }
  };

  validate = (e) => {
    console.log(this.state.selected);
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
            <Card.Title>Delete station</Card.Title>
            <Typeahead
              id="basic-example"
              onChange={(selected) => this.setState({ selected })}
              placeholder="Choose a station..."
              options={this.state.stations}
            />
            <Form>
              <Button className="button-admin" onClick={this.handleSubmit}>
                Delete
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default DeleteStation;

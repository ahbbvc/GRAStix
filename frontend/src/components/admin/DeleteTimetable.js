import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";
import Timetable from "./Timetable";

class DeleteTimetable extends Component {
  constructor(props) {
    super(props);
    this.fetchTimetables = this.fetchTimetables.bind(this);
  }
  state = {
    routes: [],
    timetables: [],

    showTimetables: false,
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selectedRoute: "",
  };

  fetchData() {
    axios.get("http://localhost:8762/routes/routes").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });
  }

  componentDidMount() {
    this.fetchData();
  }

  async fetchTimetables() {
    try {
      const response = await axios.get("http://localhost:8762/routes/timetables");

      let data = response.data;
      this.setState({
        timetables: [
          ...data.filter(
            (item) =>
              item.routeStation.route.id === this.state.selectedRoute[0].id
          ),
        ],
      });
    } catch (error) {
      this.setState({
        alertMessage: "Error! Please try again.",
        alertColor: "danger",
        alertVisible: true,
      });
    }
  }

  search = () => {
    if (this.validate()) {
      this.setState({ showTimetables: true });
      this.fetchTimetables();
    }
  };

  updateTimetables = (id) => {
    this.setState({
      timetables: this.state.timetables.filter((item) => item.id !== id),
    });
  };

  validate = (e) => {
    return true;
  };

  toggle = () => {
    this.setState({ alertVisible: !this.state.alertVisible });
  };

  render() {
    return (
      <div className="scrollable-admin">
        <Alert
          style={{ width: "560px" }}
          className="alert-admin"
          variant={this.state.alertColor}
          dismissible
          show={this.state.alertVisible}
          onClose={this.toggle}
        >
          {this.state.alertMessage}
        </Alert>
        <Card className="card-large-admin">
          <Card.Body>
            <Card.Title>Delete timetable</Card.Title>
            <div>
              <Form.Group>
                <Form.Label>Route</Form.Label>
                <Typeahead
                  className="typeahead-admin"
                  id="basic-example"
                  onChange={(selectedRoute) => this.setState({ selectedRoute })}
                  placeholder="Choose a route..."
                  options={this.state.routes}
                />
              </Form.Group>
              <Button onClick={this.search}>Search</Button>
              {this.state.showTimetables
                ? this.state.timetables.map((item) => (
                  <Timetable
                    data={item}
                    updateTimetables={this.updateTimetables}
                  ></Timetable>
                ))
                : null}
            </div>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default DeleteTimetable;

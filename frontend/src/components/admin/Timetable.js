import React, { Component } from "react";
import { Card, Alert, Form, Button, Col } from "react-bootstrap";
import axios from "axios";

class Timetable extends Component {
  constructor(props) {
    super(props);
  }

  state = {
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
  };

  handleDelete = () => {
    axios
      .delete("http://localhost:8762/routes/timetables/" + this.props.data.id)
      .then(() => {
        this.props.updateTimetables(this.props.data.id);
        this.setState({
          alertMessage: "Success. Timetable is deleted.",
          alertVisible: true,
          alertColor: "success",
        });
      });
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
        <Card className="card-mult-admin">
          <Card.Body>
            <Form>
              <Form.Group className="flex-row-admin">
                <Form.Label className="half-admin label-admin">
                  Station
                </Form.Label>
                <Form.Control
                  className="half-admin"
                  type="text"
                  readOnly
                  value={this.props.data.routeStation.station.stationName}
                />
              </Form.Group>
              <Form.Group className="flex-row-admin">
                <Form.Label className="half-admin label-admin">
                  Time of arrival
                </Form.Label>
                <Form.Control
                  className="half-admin"
                  type="text"
                  readOnly
                  value={this.props.data.timeOfArrival.slice(0, 16)}
                />
              </Form.Group>
              <Form.Group className="flex-row-admin">
                <Form.Label className="half-admin label-admin">
                  Time of departure
                </Form.Label>
                <Form.Control
                  className="half-admin"
                  type="text"
                  readOnly
                  value={this.props.data.timeOfDeparture.slice(0, 16)}
                />
              </Form.Group>
              {this.props.data.regular ? (
                <Card.Text> Regular </Card.Text>
              ) : null}

              <Button onClick={this.handleDelete}>Delete</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default Timetable;

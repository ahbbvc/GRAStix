import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";
import "./AdminPanel.css";

function convertDate(inputFormat) {
  function pad(s) {
    return s < 10 ? "0" + s : s;
  }
  var d = new Date(inputFormat);
  let s = [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join("-");
  s +=
    " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "0+0000";
  return s;
}

class CreateTimeTable extends Component {
  constructor() {
    super();
    this.state = {
      routes: [],
      stations: [],
      timetable: {
        arrivalTime: "",
        departureTime: "",
        regular: true,
      },
      alertVisible: false,
      alertMessage: "",
      alertColor: "",
      selectedRoute: "",
      selectedStation: "",
    };

    this.handleSubmit = this.handleSubmit.bind(this);
  }

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

  async handleSubmit() {
    let d1 = new Date(this.state.arrivalTime);
    let d2 = new Date(this.state.departureTime);
    let json1 = convertDate(d1);
    let json2 = convertDate(d2);

    let stationId = this.state.selectedStation[0].id;
    let routeId = this.state.selectedRoute[0].id;

    const firstRequest = axios.post("http://localhost:8083/timetables", {
      timeOfArrival: json1,
      timeOfDeparture: json2,
      regular: true,
    });

    const secondRequest = axios.post(
      "http://localhost:8083/routestations?route=" +
        routeId +
        "&station=" +
        stationId
    );

    const [firstResponse, secondResponse] = await Promise.all([
      firstRequest,
      secondRequest,
    ]);

    await axios.post(
      "http://localhost:8083/timetables/addtimetable?timetable=" +
        firstResponse.data.id +
        "&routestation=" +
        secondResponse.data.id
    );
  }

  handleChangeArr = (date) => {
    this.setState({ arrivalTime: date });
  };

  handleChangeDep = (date) => {
    this.setState({ departureTime: date });
  };

  toggleChange = () => {
    this.setState({
      regular: !this.state.regular,
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
        <Card className="card-admin">
          <Card.Body>
            <Card.Title>Create station</Card.Title>
            <Form>
              <Form.Group controlId="toA" className="flex-container-admin">
                <Form.Label>Time of arrival</Form.Label>
                <DatePicker
                  showTimeSelect
                  timeIntervals={15}
                  timeCaption="time"
                  dateFormat="dd-MM-yyyy HH:mm:ss"
                  selected={this.state.arrivalTime}
                  onChange={this.handleChangeArr}
                ></DatePicker>
              </Form.Group>
              <Form.Group controlId="toD" className="flex-container-admin">
                <Form.Label>Time of departure</Form.Label>
                <DatePicker
                  showTimeSelect
                  timeIntervals={15}
                  timeCaption="time"
                  dateFormat="dd-MM-yyyy HH:mm:ss"
                  selected={this.state.departureTime}
                  onChange={this.handleChangeDep}
                ></DatePicker>
              </Form.Group>
              <label>
                <input
                  type="checkbox"
                  defaultChecked={this.state.regular}
                  onChange={this.toggleChange}
                />
                Regular
              </label>
              <Typeahead
                className="typeahead-admin"
                id="basic-example"
                onChange={(selectedStation) =>
                  this.setState({ selectedStation })
                }
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
              <Button className="button-admin" onClick={this.handleSubmit}>
                Create
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default CreateTimeTable;

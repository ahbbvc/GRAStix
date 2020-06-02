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
  s += " " + d.getHours() + ":" + d.getMinutes() + ":00+0000";
  return s;
}

class CreateTimetable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      routes: [],
      stations: [],
      timetable: {
        arrivalTime: "",
        departureTime: "",
        regular: false,
      },

      selectedRoute: "",
      selectedStation: "",

      alertVisible: false,
      alertMessage: "",
      alertColor: "",
    };

    this.handlePost = this.handlePost.bind(this);
  }

  componentDidMount() {
    axios.get("http://localhost:8762/routes/routes").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });

    axios.get("http://localhost:8762/routes/stations").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["stationName"]));
      this.setState({ stations: jsonString });
    });
  }

  async handlePost() {
    let d1 = new Date(this.state.arrivalTime);
    let d2 = new Date(this.state.departureTime);
    let json1 = convertDate(d1);
    let json2 = convertDate(d2);

    let stationId = this.state.selectedStation[0].id;
    let routeId = this.state.selectedRoute[0].id;

    const firstRequest = axios.post("http://localhost:8762/routes/timetables", {
      timeOfArrival: json1,
      timeOfDeparture: json2,
      regular: this.state.regular,
    });

    const secondRequest = axios.post(
      "http://localhost:8762/routes/routestations?route=" +
      routeId +
      "&station=" +
      stationId
    );
    try {
      const [firstResponse, secondResponse] = await Promise.all([
        firstRequest,
        secondRequest,
      ]);
      await axios
        .post(
          "http://localhost:8762/routes/timetables/addtimetable?timetable=" +
          firstResponse.data.id +
          "&routestation=" +
          secondResponse.data.id
        )
        .then(
          this.setState({
            alertMessage: "Success. Timetable is created.",
            alertVisible: true,
            alertColor: "success",
          })
        );
      console.log(this.state.regular);
    } catch (error) {
      this.setState({
        alertMessage:
          "Error! Time of arrival must be before time of departure.",
        alertColor: "danger",
        alertVisible: true,
      });
    }
  }

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handlePost();
      e.preventDefault();
    }
  };

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

  validate = () => {
    let s = "";
    let isError = false;
    if (this.state.arrivalTime === "" || this.state.arrivalTime === undefined) {
      s += "Time of arrival is not selected.";
      isError = true;
    }
    if (
      this.state.departureTime === "" ||
      this.state.departureTime === undefined
    ) {
      s += " Time of departure is not selected.";
      isError = true;
    }
    if (
      this.state.selectedStation === "" ||
      this.state.selectedStation.length === 0
    ) {
      s += " Station is not selected.";
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
            <Card.Title>Create timetable</Card.Title>
            <Form>
              <Form.Group controlId="toA" className="flex-container-admin">
                <Form.Label>Time of arrival</Form.Label>
                <DatePicker
                  className="datepicker-admin"
                  showTimeSelect
                  timeIntervals={15}
                  timeCaption="time"
                  dateFormat="dd-MM-yyyy HH:mm"
                  selected={this.state.arrivalTime}
                  onChange={this.handleChangeArr}
                  minDate={new Date()}
                  maxDate={null}
                ></DatePicker>
              </Form.Group>
              <Form.Group controlId="toD" className="flex-container-admin">
                <Form.Label>Time of departure</Form.Label>
                <DatePicker
                  className="datepicker-admin"
                  showTimeSelect
                  timeIntervals={15}
                  timeCaption="time"
                  dateFormat="dd-MM-yyyy HH:mm"
                  selected={this.state.departureTime}
                  onChange={this.handleChangeDep}
                  minDate={new Date()}
                  maxDate={null}
                ></DatePicker>
              </Form.Group>
              <Form.Group>
                <div>
                  <label>
                    <input
                      type="checkbox"
                      className="checkbox-admin"
                      defaultChecked={this.state.regular}
                      onChange={this.toggleChange}
                    />
                    Regular
                  </label>
                </div>
                <br></br>
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
              <Button onClick={this.handleSubmit}>Create</Button>
            </Form>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default CreateTimetable;

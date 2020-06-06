import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import SockJsClient from "react-stomp";
import "./AdminPanel.css";

const config = {
  headers: { Authorization: `Bearer ${localStorage.getItem("access_token")}` },
};

const token = localStorage.getItem("access_token");

class DeleteRoute extends Component {
  state = {
    routes: [],
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selected: "",
  };

  componentDidMount() {
    this.fetchRoutes();
  }

  componentDidUpdate() {
    if (this.props.newRoute) {
      this.fetchRoutes();
      this.props.markCreated("newRoute", false);
    }
  }

  fetchRoutes = () => {
    axios.get("http://localhost:8762/routes/routes", config).then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });
  };

  handleDelete = (e) => {
    let id = this.state.selected[0].id;
    axios.delete("http://localhost:8762/routes/routes/" + id, config, {});
  };

  handleSubmit = (e) => {
    if (this.validate()) {
      this.handleDelete();
      e.preventDefault();
    }
  };

  showMessage = (msg) => {
    if (msg.split(".")[0] === "Success") {
      this.fetchRoutes();
    }

    this.setState({
      alertMessage: this.state.alertMessage + "\n" + msg,
      alertColor: "success",
      alertVisible: true,
    });
  };

  validate = (e) => {
    if (this.state.selected === "" || this.state.selected.length === 0) {
      this.setState({
        alertMessage: "Error! Route is not selected.",
        alertColor: "danger",
        alertVisible: true,
      });
      return false;
    } else return true;
  };

  toggle = () => {
    this.setState({ alertVisible: !this.state.alertVisible, alertMessage: "" });
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

        <div>
          <SockJsClient
            url={"http://localhost:8083/socket"}
            topics={["/topic/notification"]}
            onMessage={this.showMessage}
          />
        </div>
        <Card className="card-admin">
          <Card.Body>
            <Card.Title>Delete route</Card.Title>
            <Form>
              <Form.Group>
                <Form.Label>Route</Form.Label>
                <Typeahead
                  id="basic-example"
                  onChange={(selected) => this.setState({ selected })}
                  placeholder="Choose a route..."
                  options={this.state.routes}
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

export default DeleteRoute;

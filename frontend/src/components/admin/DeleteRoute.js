import React, { Component } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import axios from "axios";
import "./AdminPanel.css";

class DeleteRoute extends Component {
  state = {
    routes: [],
    alertVisible: false,
    alertMessage: "",
    alertColor: "",
    selected: "",
  };

  componentDidMount() {
    axios.get("http://localhost:8083/routes").then((res) => {
      var jsonString = res.data;
      jsonString.map((x) => (x["label"] = x["routeName"]));
      this.setState({ routes: jsonString });
    });
  }

  handleDelete = (e) => {
    let id = this.state.selected[0].id;
    axios.delete("http://localhost:8083/routes/" + id).then(
      this.setState({
        routes: this.state.routes.filter((route) => route.id !== id),
        alertMessage: "Success. Route is deleted.",
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
            <Card.Title>Delete route</Card.Title>
            <Typeahead
              id="basic-example"
              onChange={(selected) => this.setState({ selected })}
              placeholder="Choose a route..."
              options={this.state.routes}
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

export default DeleteRoute;

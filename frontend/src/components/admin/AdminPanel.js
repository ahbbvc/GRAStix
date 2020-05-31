import React, { Component } from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import CreateRoute from "./CreateRoute";
import "./AdminPanel.css";
import DeleteRoute from "./DeleteRoute";
import DeleteStation from "./DeleteStation";
import CreateStation from "./CreateStation";
import AddToRoute from "./AddToRoute";
import CreateTimetable from "./CreateTimeTable";
import DeleteTimetable from "./DeleteTimetable";

class AdminPanel extends Component {
  state = {
    activeTab: "",
    newRoute: false,
    newStation: false,
    newTimetable: false,
  };

  onSelect = (selectedKey) => {
    this.setState({ activeTab: selectedKey });
  };

  markCreated = (updatedState, status) => {
    this.setState({ [updatedState]: status });
  };

  render() {
    return (
      <div>
        <Nav
          className="justify-content-center"
          variant="tabs"
          defaultActiveKey="/routes"
          onSelect={this.onSelect}
        >
          <Nav.Item>
            <Nav.Link as={Link} to="/admin/routes" eventKey="link-1">
              Routes
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link as={Link} to="/admin/stations" eventKey="link-2">
              Stations
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link as={Link} to="/admin/timetables" eventKey="link-3">
              Timetables
            </Nav.Link>
          </Nav.Item>
        </Nav>
        {this.state.activeTab === "link-1" ? (
          <div className="flex-main-container-admin">
            <CreateRoute markCreated={this.markCreated} />
            <DeleteRoute
              newRoute={this.state.newRoute}
              markCreated={this.markCreated}
            />
          </div>
        ) : null}
        {this.state.activeTab === "link-2" ? (
          <div className="flex-main-container-admin">
            <div>
              <CreateStation markCreated={this.markCreated} />
              <DeleteStation
                newStation={this.state.newStation}
                markCreated={this.markCreated}
              />
            </div>
            <div>
              <AddToRoute
                newStation={this.state.newStation}
                newRoute={this.state.newRoute}
              />
            </div>
          </div>
        ) : null}
        {this.state.activeTab === "link-3" ? (
          <div className="flex-main-container-admin">
            <CreateTimetable markCreated={this.markCreated} />
            <DeleteTimetable newTimetable={this.state.newTimetable} />
          </div>
        ) : null}
      </div>
    );
  }
}

export default AdminPanel;

import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";
import Tickets from "./components/tickets/Tickets";

class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route path="/admin" component={AdminPanel} />
          <Route path="/tickets" component={Tickets}/>
        </Router>
      </div>
    );
  }
}

export default App;
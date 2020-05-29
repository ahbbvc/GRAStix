import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";

class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route path="/admin" component={AdminPanel} />
        </Router>
      </div>
    );
  }
}

export default App;

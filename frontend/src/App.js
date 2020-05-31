import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";
import HomePage from "./components/home/HomePage";

class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route exact path="/" component={HomePage}/>
          <Route path="/admin" component={AdminPanel} />
        </Router>
      </div>
    );
  }
}

export default App;

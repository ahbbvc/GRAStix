import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";
import Tickets from "./components/tickets/Tickets";
import HomePage from "./components/home/HomePage";

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (
    !!localStorage.getItem('access_token')
      ? <Component {...props} />
      : <Redirect to='/' />
  )} />
)

class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route exact path="/" component={HomePage} />
            <PrivateRoute path="/admin" component={AdminPanel}/>
            <PrivateRoute path="/tickets" component={Tickets}/>
        </Router>
      </div>
    );
  }
}

export default App;
import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";
import Tickets from "./components/tickets/Tickets";
import HomePage from "./components/home/HomePage";
import Profile from "./components/home/Profile";

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (
    !!localStorage.getItem('access_token')
      ? <Component {...props} />
      : <Redirect to='/' />
  )} />
)

const PrivateAdminRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (
    (!!localStorage.getItem('access_token') && localStorage.getItem('isUserAdmin')) == "true"
      ? <Component {...props} />
      : <Redirect to='/tickets' />
  )} />
)

class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route exact path="/" component={HomePage} />
          <PrivateAdminRoute path="/admin" component={AdminPanel} />
          <PrivateRoute path="/tickets" component={Tickets} />
          <PrivateRoute path="/profile" component={Profile} />
        </Router>
      </div>
    );
  }
}

export default App;
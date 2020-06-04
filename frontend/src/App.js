import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import { GuardProvider, GuardedRoute } from 'react-router-guards';
import "./App.css";
//import "bootstrap/dist/css/bootstrap.min.css";
import "./cosmo.css";
import Header from "./components/main/Header";
import AdminPanel from "./components/admin/AdminPanel";
import Tickets from "./components/tickets/Tickets";
import HomePage from "./components/home/HomePage";

const requireLogin = (to, from, next) => {
  if (to.meta.auth) {
    if (!!localStorage.getItem('access_token')) {

      if (to.meta.isAdmin && !localStorage.getItem('isUserAdmin')) {
        next.redirect('/');
      }

      next();

    } else {
      next.redirect('/');
    }

  } else {
    next.redirect('/');
  }
}


class App extends Component {
  render() {
    return (
      <div>
        <Header></Header>
        <Router>
          <Route exact path="/" component={HomePage} />
          <GuardProvider guards={[requireLogin]}>
            <GuardedRoute path="/admin" component={AdminPanel} meta={{ auth: true, isAdmin: true }} />
            <GuardedRoute path="/tickets" component={Tickets} meta={{ auth: true, isAdmin: false }} />
          </GuardProvider>
        </Router>
      </div>
    );
  }
}

export default App;
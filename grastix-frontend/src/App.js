import React, { Component } from 'react'
import './App.css';
import CenteredTabs from './components/main/CenteredTabs'
import MenuAppBar from './components/main/MenuAppBar'
import AdminPanel from './components/admin/AdminPanel';


class App extends Component {
  render() {
    return (
        <div>
          <MenuAppBar></MenuAppBar>
          {/* <AdminPanel /> */}
        </div>
    );
  }
}
export default App;

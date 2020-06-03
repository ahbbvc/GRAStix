
import React, { Component } from 'react'
import { Nav } from "react-bootstrap"
import { Link } from "react-router-dom"
import MySingleTickets from './MySingleTickets'
import MyMonthlyTickets from './MyMonthlyTickets'

class MyTickets extends Component {
    state={
        userId: 9,
        isLoaded : false,  
        activeTab: ""
    }
    
    onSelect = (selectedKey) => {
        this.setState({ activeTab: selectedKey });
    };
     
    render(){
        
      /*  if(this.state.isLoaded===false){
                return (<div>Loading ...</div>)
        }
        else{*/
        return (
            <div>
            <Nav className="justify-content-center" variant ="tabs" onSelect={this.onSelect} >
                    <Nav.Item>
                        <Nav.Link as={Link} to="/tickets/mytickets/single_tickets" eventKey="link-1"> My Single Tickets</Nav.Link> 
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link as={Link} to ="/tickets/single_ticket/monthly_tickets" eventKey="link-2">My Monthly Ticket</Nav.Link>
                    </Nav.Item> 
                </Nav>
            <div >
            {this.state.activeTab === "link-1" ? (
                    <div className="container-center">
                        <MySingleTickets/>
                    </div>
                ): null }
            {this.state.activeTab === "link-2" ? (
                    <div className="container-center">
                        <MyMonthlyTickets/>
                    </div>
                ): null }
            </div>
            </div>
        );
       // }
    }
}

export default MyTickets;
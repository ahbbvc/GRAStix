

import React, { Component } from 'react'
import { Nav } from "react-bootstrap"
import { Link } from "react-router-dom"
import MyTickets from './MyTickets'
import SingleTicket from './SingleTicket'
import MonthlyTicket from './MonthlyTicket'
import "./Tickets.css";

export class Tickets extends Component {
    state ={
        activeTab : "link-1"
        
    }
    onSelect = (selectedKey) => {
        this.setState({ activeTab: selectedKey });
    };
    
    render() {
        return (
            <div>
                <Nav className="justify-content-center" variant ="tabs" defaultActiveKey="/mytickets"onSelect={this.onSelect} >
                    <Nav.Item>
                        <Nav.Link as={Link} to="/tickets/mytickets" eventKey="link-1">My Tickets</Nav.Link> 
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link as={Link} to ="/tickets/single_ticket" eventKey="link-2">Single Ticket</Nav.Link>
                    </Nav.Item> 
                    <Nav.Item> 
                        <Nav.Link as={Link} to ="/tickets/monthly_ticket" eventKey="link-3">Monthly Ticket</Nav.Link>
                    </Nav.Item>
                       
                    
                </Nav>
                {this.state.activeTab === "link-1" ? (
                    <div>
                        <MyTickets/>
                    </div>
                ): null }
                {this.state.activeTab === "link-2" ? (
                    <div>
                        <SingleTicket/>
                    </div>
                ): null }
                {this.state.activeTab === "link-3" ? (
                    <div>
                        <MonthlyTicket/>
                    </div>
                ): null }
            </div>
        )
    }
}

export default Tickets

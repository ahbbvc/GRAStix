import React, { Component } from 'react'
import {ListGroup, Card, ListGroupItem} from "react-bootstrap"
export class MyMonthlyTicket extends Component {
    constructor(props){
        super(props);
    }
    
    list = this.props.monthlyTicket.routes.map((route)=>(
        <ListGroupItem >
            <div> Route: {route.routeName}</div>
            <div> Transport type: {route.transportType}</div>
        </ListGroupItem>
    ));
    render() {
        
        var selectTicket  =   this.props.select;
        return (
            <ListGroup.Item action onClick={() => selectTicket(this.props.monthlyTicket)}> 
                <Card.Title>{this.props.monthlyTicket.month}</Card.Title>
                <Card>
                    <Card.Header>Routes</Card.Header>
                    <ListGroup>
                         {this.list}
                    </ListGroup>
                
                </Card>

            </ListGroup.Item>
        )
    }
}

export default MyMonthlyTicket

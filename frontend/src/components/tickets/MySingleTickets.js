import React, { Component } from 'react';
import MySingleTicket from './MySingleTicket'
import { Card, Form, Button, ListGroup } from "react-bootstrap";
class MySingleTickets extends Component {
    list = this.props.singleTickets.map((sticket)=>(
        <MySingleTicket key={sticket.id} singleTicket={sticket}/>
    ));
    render(){
            return (
               <div>
                   <Card style={{ width: '18rem' }}>
                       <Card.Header>My Single Tickets</Card.Header>
                       <ListGroup variant="flush">
                            {this.list}
                        </ListGroup>
                   </Card>
                   
               </div>
            )
        
    }
}

export default MySingleTickets;

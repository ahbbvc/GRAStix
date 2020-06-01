import React, { Component } from 'react'
import {Card, ListGroup} from "react-bootstrap"
import MyMonthlyicket from "./MyMonthlyTicket"
export class MyMonthlyTickets extends Component {
    list = this.props.monthlyTickets.map((mticket)=>(
       
        <MyMonthlyicket key={mticket.id} monthlyTicket={mticket}/>
    ));
    render(){
            return (
               <div>
                   <Card style={{ width: '18rem' }}>
                       <Card.Header>My Monthly Tickets</Card.Header>
                       <ListGroup variant="flush">
                            {this.list}
                        </ListGroup>
                   </Card>
                   
               </div>
            )
        
    }
}

export default MyMonthlyTickets

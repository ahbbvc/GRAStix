import React, { Component } from 'react';
import MySingleTicket from './MySingleTicket'
class MySingleTickets extends Component {
    render(){
        return this.props.singleTickets.map((sticket)=>(
            <MySingleTicket key={sticket.id} singleTicket={sticket}/>
        ))
    }
}

export default MySingleTickets;

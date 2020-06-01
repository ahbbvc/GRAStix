import React, { Component } from 'react'
import {ListGroup} from "react-bootstrap"
export class MyMonthlyTicket extends Component {
    constructor(props){
        super(props);
        
    }
 
    render() {
        let validated = this.props.monthlyTicket.validated;
        let button;
        if(validated){
                 button =<button type="button" className="btn btn-outline-primary"> Validate</button>
        }
        else {
            button =<button type="button" className="btn btn-outline-secondary"> Validated </button>
        }
        return (
            <ListGroup.Item>
                <div>Route : {this.props.monthlyTicket.route_name}</div>
                <div> Transport type : {this.props.monthlyTicket.transport_type}</div>
                {button}

            </ListGroup.Item>
        )
    }
}

export default MyMonthlyTicket

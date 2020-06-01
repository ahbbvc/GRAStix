
import React, { Component } from 'react'
import { ListGroup } from 'react-bootstrap';
export class MySingleTicket extends Component {
    constructor(props){
        super(props);
        
    }
 
    render() {
        let validated = this.props.singleTicket.validated;
        let button;
        if(validated){
                 button =<button type="button" className="btn btn-outline-primary"> Validate</button>
        }
        else {
            button =<button type="button" className="btn btn-outline-secondary"> Validated </button>
        }
        return (
            <ListGroup.Item>
                <div>Route : {this.props.singleTicket.route.routeName}</div>
                <div> Transport type : {this.props.singleTicket.route.transportType}</div>
                {button}

            </ListGroup.Item>
        )
    }
}

export default MySingleTicket


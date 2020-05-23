
import React, { Component } from 'react'
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
            <div div className="container">
                <div className="col-12">Route : {this.props.singleTicket.route_name}</div>
                <div className="col-12"> Transport type : {this.props.singleTicket.transport_type}</div>
                {button}

            </div>
        )
    }
}

export default MySingleTicket


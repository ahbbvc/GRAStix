
import React, { Component } from 'react'
import { ListGroup, Button } from 'react-bootstrap';
export class MySingleTicket extends Component {
    constructor(props){
        super(props);
        
    }
    validate=()=>{
        fetch("http://localhost:8762/tickets/single_tickets/validate/" + this.props.singleTicket.id, {method:'PUT'})
        .then(res => res.json())
        .then(
          (result) => {
              console.log(result)
           /* this.setState({
              singleTickets: result,
              isLoaded :true
            });
            console.log(this.state.singleTickets)*/
          })
    }
    render() {
        let validated = this.props.singleTicket.validated;
        let button;
        if(!validated){
                 button =<Button  onClick={this.validate}> Validate</Button>
        }
        else {
            button =<Button disabled>  Validated </Button>
        }
        var selectTicket  =   this.props.select;
        return (
            <ListGroup.Item action onClick={() => selectTicket(this.props.singleTicket.id)}>
                <div>Route : {this.props.singleTicket.route.routeName}</div>
                <div> Transport type : {this.props.singleTicket.route.transportType}</div>
                {button}

            </ListGroup.Item>
        )
    }
}

export default MySingleTicket


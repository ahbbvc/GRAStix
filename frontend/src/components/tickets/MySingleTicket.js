
import React, { Component } from 'react'
import { ListGroup, Button, Alert } from 'react-bootstrap'
import axios from "axios"

export class MySingleTicket extends Component {
    constructor(props){
        super(props);
        this.state = {
            alertVisible: false,
            alertMessage: "",
            alertColor: ""
        }
        
    }
    toggle = () => {
        this.setState({ alertVisible: !this.state.alertVisible });
      };
  
    validate=()=>{
        axios.put("http://localhost:8762/tickets/single_tickets/validate/" + this.props.singleTicket.id,{}, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        })
        .then((result) => {
            this.setState({
                alertMessage: "Ticket validated",
                alertVisible: true,
                alertColor: "success",
            });
          }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request. " +  error.message,
                alertVisible: true,
                alertColor: "danger",
            });
        });
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
            <div>
            <Alert
                    className="alert-ticket"
                    variant={this.state.alertColor}
                    dismissible
                    show={this.state.alertVisible}
                    onClose={this.toggle}>
                    {this.state.alertMessage}
            </Alert>
            <ListGroup.Item action onClick={() => selectTicket(this.props.singleTicket.id)}>
                <div>Route : {this.props.singleTicket.route.routeName}</div>
                <div> Transport type : {this.props.singleTicket.route.transportType}</div>
                {button}

            </ListGroup.Item>
            </div>
        )
    }
}

export default MySingleTicket


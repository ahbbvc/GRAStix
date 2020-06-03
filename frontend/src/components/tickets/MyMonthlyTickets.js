import React, { Component } from 'react'
import {Card, ListGroup, Alert} from "react-bootstrap"
import MyMonthlyicket from "./MyMonthlyTicket"
import axios from "axios"
export class MyMonthlyTickets extends Component {
    state={
        monthlyTickets:[],
        isLoaded: false,
        alertVisible: false,
        alertMessage: "",
        alertColor: ""
        
    }
    componentDidMount() {
        axios.get("http://localhost:8762/tickets/monthly_tickets?user_id=" + sessionStorage.getItem('userId'), {
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem('access_token')}`
            }
        })
          .then((result) => {
                console.log(result)
              this.setState({
                monthlyTickets: result.data,
                isLoaded :true
              });
              console.log(this.state.singleTickets)
            }).catch((error) => {
                this.setState({
                    alertMessage: "There was an error while processing request. " +  error.message,
                    alertVisible: true,
                    alertColor: "danger",
                });
            });
      }
      toggle = () => {
        this.setState({ alertVisible: !this.state.alertVisible });
      };
    render(){
        /* if(this.state.isLoaded===false){
                return (<div>Loading ...</div>)
        }
        else{*/
        let list = this.state.monthlyTickets.map((mticket)=>(
            <MyMonthlyicket key={mticket.id} monthlyTicket={mticket}/>
        ));
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
               <div>
                   <Card className="card-ticket">
                       <Card.Header>My Monthly Tickets</Card.Header>
                       <ListGroup variant="flush">
                            {list}
                        </ListGroup>
                   </Card>
                 </div>
               </div>
            )
        
    }
}

export default MyMonthlyTickets

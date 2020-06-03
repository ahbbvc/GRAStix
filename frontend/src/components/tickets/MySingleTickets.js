import React, { Component } from 'react'
import QRCode from 'qrcode.react'
import MySingleTicket from './MySingleTicket'
import { Card, Alert,  ListGroup } from "react-bootstrap";
import axios from "axios"
class MySingleTickets extends Component {
    state={
        singleTickets:
        [
            {
                id:1,
                route :{
                routeName: "Ilidža-Vijećnica",
                transportType: "Tram"},
                validated: true
            },
            {
                id:2,
                route :{
                routeName: "Ilidža-Hrasnica",
                transportType: "Bus"
                },
                validated: false
            },
            {
                id:3,
                route :{
                routeName: "Skenderija-Dobrinja",
                transportType: "Bus"
                },
                validated: false
            } 
        ],
            selectedTicketId :null ,   
             isLoaded : false,  
             alertVisible: false,
             alertMessage: "",
             alertColor: "" 
    }
    selectTicket(id) {
        this.setState({
            selectedTicketId: id
        })
        console.log(this.state.selectedTicketId)
      }
    componentDidMount() {
        axios.get("http://localhost:8762/tickets/single_tickets?user_id=9")
          .then((result) => {
              this.setState({
                singleTickets: result,
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
        const downloadQR = () => {
            const canvas = document.getElementById("qrc");
            const pngUrl = canvas
              .toDataURL("image/png")
              .replace("image/png", "image/octet-stream");
            let downloadLink = document.createElement("a");
            downloadLink.href = pngUrl;
            downloadLink.download = "ticket_qrc.png";
            document.body.appendChild(downloadLink);
            downloadLink.click();
            document.body.removeChild(downloadLink);
          };
        let qr;
        if(this.state.selectedTicketId===null){
            qr=<div></div>
        }
        else{
            qr = 
            <Card className="card-ticket">
                <Card.Header>Ticket QR code</Card.Header>
                <div className="container-center">
                <QRCode
                    id="qrc"
                    value={"http://localhost:8762/tickets/single_tickets/"+this.state.selectedTicketId}
                    size={100}
                includeMargin={true}
                />
            
            </div>
            <a onClick={downloadQR} className="container-center"> Download QR </a>
        </Card>
        }
        /* if(this.state.isLoaded===false){
                return (<div>Loading ...</div>)
        }
        else{*/
            let list = this.state.singleTickets.map((sticket)=>(
                <MySingleTicket key={sticket.id} singleTicket={sticket} select = {this.selectTicket.bind(this)}/>
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
                
               <div className="row">
                   <Card className="card-ticket">
                       <Card.Header>My Single Tickets</Card.Header>
                       <ListGroup defaultActiveKey="" variant="flush">
                            {list}
                        </ListGroup>
                   </Card>
                   <div>
                       {qr}
                    </div>
                </div>
               </div>
            )
        
        
    }
}

export default MySingleTickets;

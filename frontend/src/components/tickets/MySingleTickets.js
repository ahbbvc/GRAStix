import React, { Component } from 'react'
import QRCode from 'qrcode.react'
import MySingleTicket from './MySingleTicket'
import { Card, Alert, Button, ListGroup } from "react-bootstrap";
import axios from "axios"
import jsPDF from "jspdf"
import logo from "./logo.png"
class MySingleTickets extends Component {
    state={
        singleTickets:[],
        selectedTicket :null ,   
        isLoaded : false,  
        alertVisible: false,
        alertMessage: "",
        alertColor: "" 
    }
    selectTicket(ticket) {
        this.setState({
            selectedTicket: ticket
        })
      }
      printDocument = () => {
        const canvas = document.getElementById('qrc');
        
            const imgData = canvas.toDataURL('image/png');
            const pdf = new jsPDF();
            pdf.addImage(imgData, 'JPEG', 150, 20);
            pdf.text("First name : " + this.state.selectedTicket.user.firstName ,10, 30);
            pdf.text("Last name : " + this.state.selectedTicket.user.lastName, 10, 40);
            pdf.text("Route: "+this.state.selectedTicket.route.routeName + "  " +this.state.selectedTicket.route.transportType, 10, 50)
            var time = new Date(this.state.selectedTicket.time);
            pdf.text("Time of purchase :" + time.toUTCString(), 10,60 )
            pdf.text("QR code", 165,70)
            var img = new Image();
            img.src = logo;
            pdf.addImage(img, 'png', 0,0)
            pdf.output('dataurlnewwindow');
            //pdf.save("download.pdf");
        
        
      }
    componentDidMount() {
        axios.get("http://localhost:8762/tickets/single_tickets?user_id=" +localStorage.getItem('userId'), {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        })
          .then((result) => {
              this.setState({
                singleTickets: result.data,
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
    downloadQR = () => {
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
    render(){
        
        let qr;
        if(this.state.selectedTicket===null){
            qr=<div></div>
        }
        else{
            qr = 
                <Card className="card-ticket">
                    <Card.Header>Ticket QR code</Card.Header>
                    <div className="container-center">
                    <QRCode
                        id="qrc"
                        value={"http://localhost:8762/tickets/single_tickets/"+this.state.selectedTicket.id}
                        size={180}
                    includeMargin={true}
                    />
                
                </div>
                <Button onClick={this.printDocument}>Get PDF</Button>
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
                   
                </div>
                <div>
                    {qr}
                </div>
               </div>
            )
            
        
    }
}

export default MySingleTickets;

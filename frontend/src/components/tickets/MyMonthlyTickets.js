import React, { Component } from 'react'
import {Card, ListGroup, Alert, Button} from "react-bootstrap"
import QRCode from 'qrcode.react'
import MyMonthlyicket from "./MyMonthlyTicket"
import axios from "axios"
import jsPDF from "jspdf"
import logo from "./logo.png"
export class MyMonthlyTickets extends Component {
    state={
        monthlyTickets:[],
        isLoaded: false,
        alertVisible: false,
        alertMessage: "",
        alertColor: "",
        selectedTicket: null
        
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
            pdf.text("Month : " + this.state.selectedTicket.month, 10,50)
            pdf.text("Routes: ", 10, 60)
            var h = 70
            this.state.selectedTicket.routes.map((route)=>{
                pdf.text(route.routeName + "  " + route.transportType, 20, h  );
                h= h +10;
            })
            pdf.text("QR code", 165,70)
            var img = new Image();
            img.src = logo;
            pdf.addImage(img, 'png', 0,0)
            pdf.output('dataurlnewwindow');
            //pdf.save("download.pdf");
        
        
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
                        value={"http://localhost:8762/tickets/monthly_tickets/"+this.state.selectedTicket.id}
                        size={180}
                    includeMargin={true}
                    />
                
                </div>
                <Button onClick={this.printDocument}>Get PDF</Button>
                </Card>
        }
        let list = this.state.monthlyTickets.map((mticket)=>(
            <MyMonthlyicket key={mticket.id} monthlyTicket={mticket} select = {this.selectTicket.bind(this)}/>
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

export default MyMonthlyTickets

import React, { Component } from 'react'
import DatePicker from "react-datepicker"
import { Card, Form, Button, Alert } from "react-bootstrap"
import axios from "axios";

export class MonthlyTicket extends Component {
    constructor(props){
        super(props);
        this.state={
            selectedMonth:"",
            routes:[ ],
            selectedRoutes:[],
            month:"",
            alertVisible: false,
            alertMessage: "",
            alertColor: "",
            price: 0,
            rnames: ""
        }
    }
    componentDidMount() {
        axios.get("http://localhost:8762/routes/routes", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        }).then((res) => {
          console.log(res);
          this.setState({ routes: res.data});
          console.log(this.state.routes)
        }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request. " + error.message,
                alertVisible: true,
                alertColor: "danger",
            });
        });
      }
    handleSelect = (date)=> {
    let monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    let y = date.getYear()+1900  
    this.setState({
            selectedMonth: date,
            month: monthNames[date.getMonth()+1] + "/" + y
        })
        console.log(this.state.month)
  
    }
    handleChange= (e) => {
        var options = e.target.options;
        var value = this.state.selectedRoutes;
        var label=""
        for (var i = 0, l = options.length; i < l; i++) {
          if (options[i].selected) {
            value.push(options[i].value);
            label = options[i].label + " "
          }
        }
        this.setState({
            selectedRoutes: value,
            price: this.state.price +10,
            rnames: this.state.rnames + label
        })
        console.log(this.state.selectedRoutes)
      }
    handlePost(){
        axios.post("http://localhost:8762/tickets/monthly_tickets",{
            userId: localStorage.getItem('userId'),
            routes: this.state.selectedRoutes,
            month: this.state.month
        }, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        }).then(() => {
            this.setState({
                alertMessage: "Monthly ticket bought",
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
    handleSubmit = (e) => {
          this.handlePost();
        }
    toggle = () => {
            this.setState({ alertVisible: !this.state.alertVisible });
          };
      
    render() {   
       let  options = this.state.routes.map((route)=>(
            <option value={route.id} key={route.id} label ={route.routeName}/>
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
                <Card className="card-ticket">
                    <Card.Header>Buy Monthly Ticket</Card.Header>
                    <Card.Body>
                    <Form>
                        <Form.Group controlId="newmt" className="flex-container-ticket">
                            <Form.Label>Month </Form.Label>
                            <DatePicker
                                className="datepicker-ticket"
                                dateFormat="MMMM/yyyy"
                                showMonthYearPicker
                                selected={this.state.selectedMonth}
                                onChange={this.handleSelect}
                            ></DatePicker>
                        </Form.Group>
                        <Form.Group>
                        <Form.Label>Select routes</Form.Label>
                        <Form.Control as="select" onChange={this.handleChange} multiple >
                            {options}
                        </Form.Control>
                        </Form.Group>
                        <Form.Group>
                               <Form.Label>Routes</Form.Label>
                               <Form.Control placeholder={this.state.rnames} type ="text" readOnly/>
                           </Form.Group>
                        <Form.Group>
                               <Form.Label>Price</Form.Label>
                               <Form.Control placeholder={this.state.price + " KM"} type ="text" readOnly/>
                           </Form.Group>

                        <Button onClick={this.handleSubmit}>Buy Ticket</Button>
                         </Form>
                    </Card.Body>
                 </Card>
            </div>
        )
    }
}

export default MonthlyTicket

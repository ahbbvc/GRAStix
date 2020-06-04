import React, { Component } from 'react'
import axios from "axios"
import { Card, Form, Button, Alert } from "react-bootstrap"
import { Typeahead } from "react-bootstrap-typeahead"

export class SingleTicket extends Component {
    constructor (props){
        super(props);
        this.state={
            routes:[],
            allroutes:[],
            stations: [],
            allstations:[],
            selectedRoute:"",
            selectedStationA:"",
            selectedStationB:"",
            transportType:"Bus",
            alertVisible: false,
            alertMessage: "",
            alertColor: ""
        }
    
    }
    componentDidMount() {
        axios.get("http://localhost:8762/routes/routes", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        }).then((res) => {
            var jsonString=res.data.filter(route => route.transportType===this.state.transportType);
            var allroutes = res.data;
            jsonString.map((x) => (x["label"] = x["routeName"]));
            this.setState({
                 routes: jsonString,
                 allroutes: allroutes
             });
        }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request. " +  error.message,
                alertVisible: true,
                alertColor: "danger",
            });
        });
        axios.get("http://localhost:8762/routes/routestations", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        }).then((res) => {
            var allstations =res.data;
            this.setState({ allstations: allstations });
         }).catch((error) => {
            this.setState({
                alertMessage: "There was an error while processing request. "  + error.message,
                alertVisible: true,
                alertColor: "danger",
            });
        });
    }
    loadRoutes(){
        var allroutes = this.state.allroutes;
        var jsonString=allroutes.filter(route => route.transportType===this.state.transportType);
        jsonString.map((x) => (x["label"] = x["routeName"]));
        this.setState({
             routes: jsonString
         });
    }
    loadStations(){
        var allstations=this.state.allstations;
        var jsonString=allstations.filter(station => station.route.id===this.state.selectedRoute.id);
        jsonString.map((x) => (x["label"] = x.station.stationName));
        this.setState({
             stations: jsonString
         });
    }
    handleTTChange = (e) => {
        var options = e.target.options;
        for (var i = 0, l = options.length; i < l; i++) {
          if (options[i].selected) {
            var value=options[i].value;
            this.setState({
                transportType: value
            },
                this.loadRoutes
            )
            break;
          }
        }
       
      }
    handleRouteChange = (e) =>{
        var selectedRoute = e[0]
        this.setState({
            selectedRoute: selectedRoute
        },
            this.loadStations
        )
        
    }
    handleSubmit=()=>{
        axios.post("http://localhost:8762/tickets/single_tickets",{
            userId: localStorage.getItem('userId'),
            routeId: this.state.selectedRoute.id
        }, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('access_token')}`
            }
        }).then(() => {
            this.setState({
                alertMessage: "Single ticket bought",
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
    toggle = () => {
        this.setState({ alertVisible: !this.state.alertVisible });
      };
    render() {
        
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
                    <Card.Header>Buy Single Ticket</Card.Header>
                    <Card.Body>
                        <Form>
                            <Form.Group>
                            <Form.Label>Transport Type</Form.Label>
                            <Form.Control as="select" onChange={this.handleTTChange}>
                                <option key="1" value="Bus">Bus</option>
                                <option key="2" value="Tram">Tram</option>
                                <option key="3" value="Troley">Troley</option>
                                
                            </Form.Control>
                            </Form.Group>
                           <Form.Group>
                           <Form.Label>Route</Form.Label>
                           <Typeahead className="typehead-ticket" id="routes-select" onChange={this.handleRouteChange} placeholder="Choose route" options={this.state.routes}></Typeahead>
                           </Form.Group>
                           <Form.Group>
                           <Form.Label>From</Form.Label>
                           <Typeahead className="typehead-ticket" id="stationA-select" onChange={(selectedStationA) => this.setState({ selectedStationA }) } placeholder="Choose station" options={this.state.stations}></Typeahead>
                           </Form.Group>
                           <Form.Group>
                           <Form.Label>To</Form.Label>
                           <Typeahead className="typehead-ticket" id="stationB-select" onChange={(selectedStationB) => this.setState({ selectedStationB }) } placeholder="Choose station" options={this.state.stations}></Typeahead>
                           </Form.Group>
                           <Button onClick={this.handleSubmit}>Buy Ticket</Button>
                        </Form>
                        
                    </Card.Body>
                </Card>
            </div>
        )
    }
}

export default SingleTicket

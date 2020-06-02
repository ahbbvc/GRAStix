import React, { Component } from 'react'
import {Card, ListGroup} from "react-bootstrap"
import MyMonthlyicket from "./MyMonthlyTicket"
export class MyMonthlyTickets extends Component {
    state={
        monthlyTickets:
        [
            {
                id:1,
                month: "June",
                routes:[
                    {
                        id:1,
                        routeName: "Ilidža-Vijećnica",
                        transportType: "Tram",
                    },
                    {
                        id:2,
                        routeName: "Ilidža-Hrasnica",
                        transportType: "Bus",
                    }

                ]
            },
            {
                id:2,
                month: "June",
                routes:[
                    {
                        id:1,
                        routeName: "Ilidža-Vijećnica",
                        transportType: "Tram",
                    },
                    {
                        id:3,
                        routeName: "Skenderija-Dobrinja",
                        transportType: "Bus",
                    }

                ]
            }
        ],
        isLoaded: false
    }
    componentDidMount() {
        fetch("http://localhost:8762/tickets/monthly_tickets?user_id=11")
          .then(res => res.json())
          .then(
            (result) => {
                console.log(result)
              this.setState({
                monthlyTickets: result,
                isLoaded :true
              });
              console.log(this.state.singleTickets)
            })
      }
    
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
                   <Card className="card-ticket">
                       <Card.Header>My Monthly Tickets</Card.Header>
                       <ListGroup variant="flush">
                            {list}
                        </ListGroup>
                   </Card>
                   
               </div>
            )
        
    }
}

export default MyMonthlyTickets

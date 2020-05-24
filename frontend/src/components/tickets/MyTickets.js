
import React, { Component } from 'react';
import MySingleTickets from './MySingleTickets'

class MyTickets extends Component {
    state={
        singleTickets:
        [
            {
                id:1,
                route_name: "Ilidža-Vijećnica",
                transport_type: "Tram",
                validated: true
            },
            {
                id:2,
                route_name: "Ilidža-Hrasnica",
                transport_type: "Bus",
                validated: false
            },
            {
                id:3,
                route_name: "Skenderija-Dobrinja",
                transport_type: "Bus",
                validated: false
            }
            
            
        ]
    }
    componentDidMount() {
        fetch("https://localhost:8082/single_tickets?user_id=11")
          .then(res => res.json())
          .then(
            (result) => {
                console.log(result)
              this.setState({
                singleTickets: result
              });
            })
      }
    
     
    render(){
        return (
            <div className="MyTcikets">
            <h1>MyTcikets</h1>
            <MySingleTickets singleTickets={this.state.singleTickets}/>
            </div>
        );
    }
}

export default MyTickets;
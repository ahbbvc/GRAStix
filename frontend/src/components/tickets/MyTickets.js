
import React, { Component } from 'react';
import MySingleTickets from './MySingleTickets'
import MyMonthlyTickets from './MyMonthlyTickets';

class MyTickets extends Component {
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
        monthlyTickets:
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
         
        ],
        isLoaded : false  
    }
    componentDidMount() {
        fetch("http://localhost:8762/tickets/single_tickets?user_id=9")
          .then(res => res.json())
          .then(
            (result) => {
                console.log(result)
              this.setState({
                singleTickets: result,
                isLoaded :true
              });
              console.log(this.state.singleTickets)
            })
      }
    
     
    render(){
        
      /*  if(this.state.isLoaded===false){
                return (<div>Loading ...</div>)
        }
        else{*/
        return (
            
            <div >
            <h1>MyTickets</h1>
            <div className="row">
                <MySingleTickets singleTickets={this.state.singleTickets}/>
            
            
                <MyMonthlyTickets monthlyTickets={this.state.monthlyTickets}/>
            </div>
            </div>
        );
       // }
    }
}

export default MyTickets;
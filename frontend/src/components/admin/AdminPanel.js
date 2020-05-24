import React, { Component } from 'react'
import { Nav, Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'

class AdminPanel extends Component {
    render() {
        return (
            <div>
                <Nav className="justify-content-center" variant="tabs" defaultActiveKey="/routes">
                    <Nav.Item>
                        <Nav.Link as={Link} to="/routes" eventKey = "link-1">Routes</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link as={Link} to="/stations" eventKey = "link-2">Stations</Nav.Link>
                    </Nav.Item> 
                    <Nav.Item>
                        <Nav.Link as={Link} to="/timetables" eventKey = "link-3">Timetables</Nav.Link>
                    </Nav.Item>
                    </Nav>
            </div>
        )
    }
}

export default AdminPanel;

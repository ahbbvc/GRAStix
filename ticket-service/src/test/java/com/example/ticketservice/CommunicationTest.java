package com.example.ticketservice;

import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Model.SingleTicket;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CommunicationTest {
    @Autowired
    private MockMvc mvc;

    /*
   U bazama drugi mikroservisa ima:
   Rute:

   {
       "id": 1,
       "routeName": "A-B",
       "transportType": "Bus",
       "user": null
   }

   User :
   {
       "id": 11,
       "firstName": "Naida",
       "lastName": "Hanjalic",
       "birthDate": "1998-02-22T00:00:00.000+0000",
       "email": "nhanjalic@mail.com",
       "password": "password111",
       "cardNumber": "123456789",
       "cvv": "123",
       "expiryDate": "2020-03-28T00:17:13.417+0000",
       "status": "student"
   }


    */
    @Order(1)
    @Test
    public void getUserSTickets() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/single_tickets?user_id=11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.id", is(11)))
                .andExpect(jsonPath("$[0].user.firstName", is("Naida")))
                .andExpect(jsonPath("$[0].user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$[0].route.id", is(1)))
                .andExpect(jsonPath("$[0].route.routeName", is("A-B")))
                .andExpect(jsonPath("$[0].route.transportType", is("Bus")));

    }
    @Order(1)
    @Test
    void getUserMTickets() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/monthly_tickets?user_id=11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.id", is(11)))
                .andExpect(jsonPath("$[0].user.firstName", is("Naida")))
                .andExpect(jsonPath("$[0].user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$[0].routes[0].id", is(1)))
                .andExpect(jsonPath("$[0].routes[0].routeName", is("A-B")))
                .andExpect(jsonPath("$[0].routes[0].transportType", is("Bus")))
                .andExpect(jsonPath("$[0].month", is("May")));

    }

    @Order(0)
    @Test
    public void createSTicket() throws Exception{
        String s = "{\"userId\" : 11 , \"routeId\" : 1}";
        mvc.perform(MockMvcRequestBuilders.post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(11)))
                .andExpect(jsonPath("$.user.firstName", is("Naida")))
                .andExpect(jsonPath("$.user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$.route.id", is(1)))
                .andExpect(jsonPath("$.route.routeName", is("A-B")))
                .andExpect(jsonPath("$.route.transportType", is("Bus")));

    }
    @Order(0)
    @Test
    public void createMTicket() throws Exception{
        String s = "{\"userId\" : 11 , \"routes\" : [1], \"month\" : \"May\" }";
        mvc.perform(MockMvcRequestBuilders.post("/monthly_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month", is("May")))
                .andExpect(jsonPath("$.user.id", is(11)))
                .andExpect(jsonPath("$.user.firstName", is("Naida")))
                .andExpect(jsonPath("$.user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$.routes[0].id", is(1)))
                .andExpect(jsonPath("$.routes[0].routeName", is("A-B")))
                .andExpect(jsonPath("$.routes[0].transportType", is("Bus")));

    }
}


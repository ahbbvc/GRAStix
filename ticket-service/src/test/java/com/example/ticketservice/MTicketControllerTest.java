package com.example.ticketservice;

import com.example.ticketservice.Wrappers.MTicketResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
@SpringBootTest
@AutoConfigureMockMvc
public class MTicketControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    static Integer id;


    U bazama drugi mikroservisa ima:
    Rute:
    {
        "id": 1,
        "routeName": "A-B",
        "transportType": "Bus",
        "user": null
    },


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




    @Order(0)
    @Test
    public void createMTicket() throws Exception{
        String s = "{\"userId\" : 11 , \"routes\" : [1], \"month\" : \"June\" }";
        MvcResult response = mvc.perform(MockMvcRequestBuilders.post("/monthly_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month", is("June")))
                .andExpect(jsonPath("$.user.id", is(11)))
                .andExpect(jsonPath("$.user.firstName", is("Naida")))
                .andExpect(jsonPath("$.user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$.routes[0].id", is(1)))
                .andExpect(jsonPath("$.routes[0].routeName", is("A-B")))
                .andExpect(jsonPath("$.routes[0].transportType", is("Bus")))
                .andReturn();
        String object = response.getResponse().getContentAsString();
        MTicketResponseWrapper mt = objectMapper.readValue(object, MTicketResponseWrapper.class);
        id= mt.getId();

    }
    @Order(1)
    @Test
    public void findMTicketById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/monthly_tickets/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(11)))
                .andExpect(jsonPath("$.routes[0].route_id", is(1)))
                .andExpect(jsonPath("$.month", is("June")));
    }

    @Order(1)
    @Test
    public void findMTicketByIdNotFound() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/monthly_tickets/1000" )
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @Order(1)
    void validateMTicket() throws Exception{
        mvc.perform(put("/monthly_tickets/validate/"+ id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validated", is (true)));
    }
    @Test
    @Order(1)
    void validateMTicketNotFound() throws Exception{
        mvc.perform(put("/monthly_tickets/validate/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Order(3)
    public void findAllMTickets() throws Exception{
        mvc.perform(get("/monthly_tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}*/

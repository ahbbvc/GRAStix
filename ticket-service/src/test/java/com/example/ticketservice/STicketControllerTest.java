package com.example.ticketservice;


import com.example.ticketservice.Wrappers.STicketResponseWraper;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class STicketControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    static Integer id ;

    /*
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


    */
    @Test
    @Order(0)
    public void createSTicket() throws Exception{

        String s = "{\"userId\" : 11 , \"routeId\" : 1}";
        MvcResult response =  mvc.perform(post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(11)))
                .andExpect(jsonPath("$.user.firstName", is("Naida")))
                .andExpect(jsonPath("$.user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$.route.id", is(1)))
                .andExpect(jsonPath("$.route.routeName", is("A-B")))
                .andExpect(jsonPath("$.route.transportType", is("Bus")))
                .andReturn();
        String object = response.getResponse().getContentAsString();
        STicketResponseWraper st = objectMapper.readValue(object, STicketResponseWraper.class);
        id= st.getId();

    }


    @Test
    @Order(1)
    public void findSTicketById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/single_tickets/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(11)))
                .andExpect(jsonPath("$.routeId", is(1)));
    }
    @Test
    @Order(1)
    public void findSTicketByIdNotFound() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/single_tickets/1000" )
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(1)
    void alidateSTicket() throws Exception{
        mvc.perform(put("/single_tickets/validate/"+ id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validated", is (true)));
    }

    @Test
    @Order(1)
    void validateSTicketNotFound() throws Exception{
        mvc.perform(put("/single_tickets/validate/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void deleteSTicket() throws Exception{
        mvc.perform(delete("/single_tickets/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void deleteSTicketNotFound() throws Exception{
        mvc.perform(delete("/single_tickets/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Order(3)
    public void findAllSTickets() throws Exception{
        mvc.perform(get("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}


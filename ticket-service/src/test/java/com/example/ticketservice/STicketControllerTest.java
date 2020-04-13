package com.example.ticketservice;


import com.example.ticketservice.Controller.STicketController;
import com.example.ticketservice.Exeption.ExeptionHandler;
import com.example.ticketservice.Model.STicketResponseWraper;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Service.STicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
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

    @Test
    @Order(0)
    public void createSTicket() throws Exception{

        String s = "{\"userId\" : 11 , \"routeId\" : 3}";
        MvcResult response =  mvc.perform(post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(11)))
                .andExpect(jsonPath("$.user.firstName", is("Naida")))
                .andExpect(jsonPath("$.user.lastName", is("Hanjalic")))
                .andExpect(jsonPath("$.route.id", is(3)))
                .andExpect(jsonPath("$.route.routeName", is("A-B")))
                .andExpect(jsonPath("$.route.transportType", is("Tram")))
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
                .andExpect(jsonPath("$.routeId", is(3)));
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
    @Order(4)
    void deleteSTicket() throws Exception{
        mvc.perform(delete("/single_tickets/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @Order(3)
    public void findAllSTickets() throws Exception{
        mvc.perform(get("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    /*
    @Test
    public void findSTicketById() throws Exception{
        SingleTicket st = new SingleTicket(1, 1);
        st.setId(1);
        given(sTicketController.findById(st.getId())).willReturn(st);
        String url = "/single_tickets/" + st.getId();
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(st.getId())))
                .andExpect(jsonPath("$.userId", is(st.getUserId())));
    }*/
   /* @Test
    public void addSTicket() throws Exception{
        JSONObject jo = new JSONObject();
        jo.put("userId", 1);
        jo.put("routeId", 1);
        mvc.perform(post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jo)))
                .andExpect(status().isOk());

    }*/
  /*  @Test
    void deleteSTicket() throws Exception{
        SingleTicket st = new SingleTicket(1, 1);
        st.setId(1);
        given(sTicketController.findById(st.getId())).willReturn(st);
        mvc.perform(delete("/single_tickets/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    void validateSTicket() throws Exception{
        SingleTicket st = new SingleTicket(1, 1);
        st.setId(1);
        given(sTicketController.findById(st.getId())).willReturn(st);
        mvc.perform(put("/single_tickets/validate/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void newTicketParameterMissingError() throws Exception{
        JSONObject jo = new JSONObject();
        jo.put("userId", 1);
        mvc.perform(post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jo)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void findSTicketByIdError() throws Exception{
        given(sTicketController.findById(1)).willReturn(null);
        mvc.perform(get("/single_ticktets/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }*/

}


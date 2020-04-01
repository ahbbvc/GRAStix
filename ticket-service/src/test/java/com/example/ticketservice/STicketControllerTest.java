package com.example.ticketservice;


import com.example.ticketservice.Controller.STicketController;
import com.example.ticketservice.Exeption.ExeptionHandler;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Service.STicketService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    @MockBean
    private STicketService sTicketController;

    private SingleTicket st1 = new SingleTicket(1,1);

    @Test
    public void findAllSTickets() throws Exception{
        SingleTicket st = new SingleTicket(1, 1);
        List<SingleTicket> stickets = Arrays.asList(st);
        given(sTicketController.findAll()).willReturn(stickets);
        mvc.perform(get("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(st.getUserId())));

    }
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
    }
    @Test
    public void addSTicket() throws Exception{
        JSONObject jo = new JSONObject();
        jo.put("userId", 1);
        jo.put("routeId", 1);
        mvc.perform(post("/single_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jo)))
                .andExpect(status().isOk());

    }
    @Test
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
    }

}


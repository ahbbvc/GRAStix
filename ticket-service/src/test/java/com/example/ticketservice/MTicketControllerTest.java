package com.example.ticketservice;

import com.example.ticketservice.Controller.MTicketController;
import com.example.ticketservice.Controller.STicketController;
import com.example.ticketservice.Model.MonthlyTicket;
import com.example.ticketservice.Model.SingleTicket;
import com.example.ticketservice.Service.MTicketService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MTicketControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private MTicketService mTicketController;
    @Test

    public void findAllMTickets() throws Exception{
        MonthlyTicket mt = new MonthlyTicket(1, "March");
        List<MonthlyTicket> mtickets = Arrays.asList(mt);
        given(mTicketController.findAll()).willReturn(mtickets);
        mvc.perform(get("/monthly_tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(mt.getUserId())))
                .andExpect(jsonPath("$[0].month", is(mt.getMonth())));

    }
    @Test
    public void findSTicketById() throws Exception{
        MonthlyTicket mt = new MonthlyTicket(1, "March");
        mt.setId(1);
        given(mTicketController.findById(mt.getId())).willReturn(mt);
        String url = "/monthly_tickets/" + mt.getId();
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mt.getId())))
                .andExpect(jsonPath("$.userId", is(mt.getUserId())));
    }

    @Test
    void MethodNotSupported() throws Exception{

        mvc.perform(delete("/monthly_tickets/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void newTicketParameterMissingError() throws Exception{
        JSONObject jo = new JSONObject();
        jo.put("userId", 1);
        mvc.perform(post("/monthly_tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jo)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void validateMTicket() throws Exception{
        MonthlyTicket mt = new MonthlyTicket(1, "March");
        mt.setId(1);
        given(mTicketController.findById(mt.getId())).willReturn(mt);
        mvc.perform(put("/monthly_tickets/validate/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    public void findMTicketByIdError() throws Exception{
        given(mTicketController.findById(1)).willReturn(null);
        mvc.perform(get("/monthly_ticktets/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

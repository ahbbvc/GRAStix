package com.example.routeservice;

import com.example.routeservice.model.Station;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(2)
    public void getAllStations() throws Exception {
        mvc.perform( MockMvcRequestBuilders.get("/stations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    @Order(3)
    public void getStationById() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/stations/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(1)
    public void createStation() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/stations")
                .content(asJsonString(new Station(1,"Ilidza")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.stationName").value("Ilidza"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void updateStation() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .put("/stations/{id}", 1)
                .content(asJsonString(new Station(1, "Bascarsija")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationName").value("Bascarsija"));
    }

    @Test
    @Order(5)
    public void deleteStation() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("/stations/{id}", 1) )
                .andExpect(status().isOk());
    }

}
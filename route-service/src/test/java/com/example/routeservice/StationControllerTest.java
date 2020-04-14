package com.example.routeservice;

import com.example.routeservice.model.Station;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllStations() throws Exception {
        newStation();
        mvc.perform( MockMvcRequestBuilders.get("/stations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getStationById() throws Exception {
        Station station = newStation();
        mvc.perform( MockMvcRequestBuilders
                .get("/stations/{id}", station.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(station.getId()));
    }

    @Test
    public void getStationByIdNoValue() throws Exception {
        Station station = deleteStation();
        mvc.perform( MockMvcRequestBuilders
                .get("/stations/{id}", station.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getStationByIdBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/stations/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createStation() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/stations")
                .content(asJsonString(new Station("Ilidza")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.stationName").value("Ilidza"));
    }

    @Test
    public void createStationBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/stations")
                .content(asJsonString(new Station("")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStation() throws Exception {
        Station station = newStation();
        mvc.perform( MockMvcRequestBuilders
                .put("/stations/{id}", station.getId())
                .content(asJsonString(new Station("Bascarsija")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationName").value("Bascarsija"));
    }

    @Test
    public void updateStationBadRequest() throws Exception {
        Station station = newStation();
        mvc.perform( MockMvcRequestBuilders
                .put("/stations/{id}", station.getId())
                .content(asJsonString(new Station("")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public Station deleteStation() throws Exception {
        Station station = newStation();
        mvc.perform( MockMvcRequestBuilders.delete("/stations/{id}", station.getId()) )
                .andExpect(status().isOk());
        return station;
    }

    @Test
    public void deleteStationNoValue() throws Exception {
        Station station = deleteStation();
        mvc.perform( MockMvcRequestBuilders.delete("/stations/{id}", station.getId()) )
                .andExpect(status().isInternalServerError());
    }

    public Station newStation() throws Exception {
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/stations")
                .content(asJsonString(new Station("Ilidza")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, Station.class);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
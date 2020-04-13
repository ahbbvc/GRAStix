package com.example.routeservice;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.RouteStation;
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
public class RouteStationControllerTest {
/*
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllRouteStations() throws Exception {
        createRouteStation();
        mvc.perform( MockMvcRequestBuilders.get("/routestations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getRouteStationById() throws Exception {
        RouteStation routeStation = createRouteStation();
        mvc.perform( MockMvcRequestBuilders
                .get("/routestations/{id}", routeStation.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routeStation.getId()));
    }

    @Test
    public void getRouteStationByIdNoValue() throws Exception {
        RouteStation routeStation = deleteRouteStation();
        mvc.perform( MockMvcRequestBuilders
                .get("/routestations/{id}", routeStation.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getRouteStationByIdBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/routestations/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public RouteStation createRouteStation() throws Exception {
        MvcResult result1 = mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route("A-B", "Bus")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json1 = result1.getResponse().getContentAsString();
        Route route = new ObjectMapper().readValue(json1, Route.class);

        MvcResult result2 = mvc.perform( MockMvcRequestBuilders
                .post("/stations")
                .content(asJsonString(new Station("Ilidza")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json2 = result2.getResponse().getContentAsString();
        Station station = new ObjectMapper().readValue(json2, Station.class);

        String url = "/routestations?route=7&station=3";// + route.getId() + "&station=" + station.getId();
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new RouteStation(route, station)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                //.andExpect(jsonPath("$.route_id").value(route.getId()))
                //.andExpect(jsonPath("$.station_id").value(station.getId()))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, RouteStation.class);
    }

    @Test
    public RouteStation deleteRouteStation() throws Exception {
        RouteStation routeStation = createRouteStation();
        mvc.perform( MockMvcRequestBuilders.delete("/routestations/{id}", routeStation.getId()) )
                .andExpect(status().isOk());
        return routeStation;
    }

    @Test
    public void deleteRouteStationNoValue() throws Exception {
        RouteStation routeStation = deleteRouteStation();
        mvc.perform( MockMvcRequestBuilders.delete("/routeStations/{id}", routeStation.getId()) )
                .andExpect(status().isInternalServerError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/
}
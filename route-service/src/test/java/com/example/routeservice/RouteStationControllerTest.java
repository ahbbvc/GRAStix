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

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllRouteStations() throws Exception {
        newRouteStation();
        mvc.perform( MockMvcRequestBuilders.get("/routestations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getRouteStationById() throws Exception {
        RouteStation routeStation = newRouteStation();
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
    public void createRouteStation() throws Exception {
        Route route = newRoute();
        Station station = newStation();
        String url = "/routestations?route=" + route.getId() + "&station=" + station.getId();
        mvc.perform( MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new RouteStation(route, station)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.route.id").value(route.getId()))
                .andExpect(jsonPath("$.station.id").value(station.getId()));
    }

    @Test
    public void createRouteStationBadRequest() throws Exception {
        String url = "/routestations?route=abc&station=abc";
        mvc.perform( MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new RouteStation(null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public RouteStation deleteRouteStation() throws Exception {
        RouteStation routeStation = newRouteStation();
        mvc.perform( MockMvcRequestBuilders.delete("/routestations/{id}", routeStation.getId()) )
                .andExpect(status().isOk());
        return routeStation;
    }

    @Test
    public void deleteRouteStationNoValue() throws Exception {
        RouteStation routeStation = deleteRouteStation();
        mvc.perform( MockMvcRequestBuilders.delete("/routestations/{id}", routeStation.getId()) )
                .andExpect(status().isInternalServerError());
    }

    public RouteStation newRouteStation() throws Exception {
        Route route = newRoute();
        Station station = newStation();
        String url = "/routestations?route=" + route.getId() + "&station=" + station.getId();
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new RouteStation(route, station)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, RouteStation.class);
    }

    public Route newRoute() throws Exception {
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route("A-B", "Bus")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, Route.class);
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
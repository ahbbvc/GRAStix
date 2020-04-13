package com.example.routeservice;

import com.example.routeservice.model.Route;
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
public class RouteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllRoutes() throws Exception {
        createRoute();
        mvc.perform( MockMvcRequestBuilders.get("/routes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getRouteById() throws Exception {
        Route route = createRoute();
        mvc.perform( MockMvcRequestBuilders
                .get("/routes/{id}", route.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(route.getId()));
    }

    @Test
    public void getRouteByIdNoValue() throws Exception {
        Route route = deleteRoute();
        mvc.perform( MockMvcRequestBuilders
                .get("/routes/{id}", route.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getRouteByIdBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/routes/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRoutesByNameAndType() throws Exception {
        Route route = createRoute();
        String url = "/routes/search?name=" + route.getRouteName() + "&type=" + route.getTransportType();
        mvc.perform( MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getRoutesByNameAndTypeBadRequest() throws Exception {
        String url = "/routes/search?name=bla&abc=bla";
        mvc.perform( MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public Route createRoute() throws Exception {
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route("A-B", "Bus")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.routeName").value("A-B"))
                .andExpect(jsonPath("$.transportType").value("Bus"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, Route.class);
    }

    @Test
    public void createRouteBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route("A-B", "")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoute() throws Exception {
        Route route = createRoute();
        mvc.perform( MockMvcRequestBuilders
                .put("/routes/{id}", route.getId())
                .content(asJsonString(new Route("B-C", "Tram")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeName").value("B-C"))
                .andExpect(jsonPath("$.transportType").value("Tram"));
    }

    @Test
    public void updateRouteBadRequest() throws Exception {
        Route route = createRoute();
        mvc.perform( MockMvcRequestBuilders
                .put("/routes/{id}", route.getId())
                .content(asJsonString(new Route("", "")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public Route deleteRoute() throws Exception {
        Route route = createRoute();
        mvc.perform( MockMvcRequestBuilders.delete("/routes/{id}", route.getId()) )
                .andExpect(status().isOk());
        return route;
    }

    @Test
    public void deleteRouteNoValue() throws Exception {
        Route route = deleteRoute();
        mvc.perform( MockMvcRequestBuilders.delete("/routes/{id}", route.getId()) )
                .andExpect(status().isInternalServerError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
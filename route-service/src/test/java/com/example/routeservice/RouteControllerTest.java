package com.example.routeservice;

import com.example.routeservice.model.Route;
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
public class RouteControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(2)
    public void getAllRoutes() throws Exception {
        mvc.perform( MockMvcRequestBuilders.get("/routes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    @Order(3)
    public void getRouteById() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/routes/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(1)
    public void createRoute() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route(1,"A-B", "Bus")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.routeName").value("A-B"))
                .andExpect(jsonPath("$.transportType").value("Bus"));
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
    public void updateRoute() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .put("/routes/{id}", 1)
                .content(asJsonString(new Route(1, "B-C", "Tram")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeName").value("B-C"))
                .andExpect(jsonPath("$.transportType").value("Tram"));
    }

    @Test
    @Order(5)
    public void deleteRoute() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("/routes/{id}", 1) )
                .andExpect(status().isOk());
    }

}

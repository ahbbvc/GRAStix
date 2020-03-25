package com.example.routeservice;

import com.example.routeservice.model.Route;
import com.example.routeservice.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RouteService routeService;

    @Test
    public void getAllRoutes() throws Exception {
        Route newRoute = new Route("A-B", "Bus");
        List<Route> allRoutes = Arrays.asList(newRoute);

        given(routeService.findAll()).willReturn(allRoutes);

        mvc.perform(get("/routes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].routeName", is(newRoute.getRouteName())));
    }

    @Test
    public void getRouteById() throws Exception {
        Route newRoute = new Route("A-B", "Bus");
        newRoute.setId(1);
        given(routeService.findById(newRoute.getId())).willReturn(newRoute);

        mvc.perform(get("/routes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(newRoute.getId())))
                .andExpect(jsonPath("$.routeName", is(newRoute.getRouteName())));
    }

    @Test
    public void getRouteByNameAndTransportType() throws Exception {
        /*
        Route newRoute = new Route("A-B", "Bus");
        List<Route> allRoutes = Arrays.asList(newRoute);
        given(routeService.findByRouteNameAndTransportType(newRoute.getRouteName(), newRoute.getTransportType()))
                .willReturn(allRoutes);
        String url = "/routes?name=" + newRoute.getRouteName() + "&type="+newRoute.getTransportType();
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].routeName", is(newRoute.getRouteName())));
         */
    }

    @Test
    public void createRoute() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .post("/routes")
                .content(asJsonString(new Route("A-B", "Bus")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void deleteRouteById() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.delete("/routes/{id}", 1) )
                .andExpect(status().isOk());
    }
}

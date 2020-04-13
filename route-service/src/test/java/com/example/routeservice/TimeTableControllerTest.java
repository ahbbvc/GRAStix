package com.example.routeservice;

import com.example.routeservice.model.TimeTable;
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

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeTableControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(2)
    public void getAllTimeTables() throws Exception {
        mvc.perform( MockMvcRequestBuilders.get("/timetables")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    @Order(3)
    public void getTimeTableById() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/timetables/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(1)
    public void createTimeTable() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2020);
        cal1.set(Calendar.MONTH, Calendar.DECEMBER);
        cal1.set(Calendar.DAY_OF_MONTH, 31);
        cal1.set(Calendar.HOUR_OF_DAY, 8);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2020);
        cal2.set(Calendar.MONTH, Calendar.DECEMBER);
        cal2.set(Calendar.DAY_OF_MONTH, 31);
        cal2.set(Calendar.HOUR_OF_DAY, 8);
        cal2.set(Calendar.MINUTE, 10);
        cal2.set(Calendar.SECOND, 0);

        mvc.perform( MockMvcRequestBuilders
                .post("/timetables")
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(),true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                //popraviti - problem sa vremenskim zonama
                .andExpect(jsonPath("$.timeOfArrival").value("31-12-2020 08:00:00"))
                .andExpect(jsonPath("$.timeOfDeparture").value("31-12-2020 08:10:00"))
                .andExpect(jsonPath("$.regular").value(true));
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
    public void updateTimeTable() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2020);
        cal1.set(Calendar.MONTH, Calendar.DECEMBER);
        cal1.set(Calendar.DAY_OF_MONTH, 31);
        cal1.set(Calendar.HOUR_OF_DAY, 12);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2020);
        cal2.set(Calendar.MONTH, Calendar.DECEMBER);
        cal2.set(Calendar.DAY_OF_MONTH, 31);
        cal2.set(Calendar.HOUR_OF_DAY, 12);
        cal2.set(Calendar.MINUTE, 10);
        cal2.set(Calendar.SECOND, 0);

        mvc.perform( MockMvcRequestBuilders
                .put("/timetables/{id}", 1)
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(), true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //popraviti - problem sa vremenskim zonama
                .andExpect(jsonPath("$.timeOfArrival").value("31-12-2020 12:00:00"))
                .andExpect(jsonPath("$.timeOfDeparture").value("31-12-2020 12:10:00"))
                .andExpect(jsonPath("$.regular").value(true));
    }

    @Test
    @Order(5)
    public void deleteTimeTable() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("/timetables/{id}", 1) )
                .andExpect(status().isOk());
    }

}
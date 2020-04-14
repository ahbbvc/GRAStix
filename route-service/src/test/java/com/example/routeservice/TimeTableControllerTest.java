package com.example.routeservice;

import com.example.routeservice.model.Route;
import com.example.routeservice.model.RouteStation;
import com.example.routeservice.model.Station;
import com.example.routeservice.model.TimeTable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TimeTableControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllTimeTables() throws Exception {
        newTimeTable();
        mvc.perform( MockMvcRequestBuilders.get("/timetables")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getTimeTableById() throws Exception {
        TimeTable timeTable = newTimeTable();
        mvc.perform( MockMvcRequestBuilders
                .get("/timetables/{id}", timeTable.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(timeTable.getId()));
    }

    @Test
    public void getTimeTableByIdNoValue() throws Exception {
        TimeTable timeTable = deleteTimeTable();
        mvc.perform( MockMvcRequestBuilders
                .get("/timetables/{id}", timeTable.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getTimeTableByIdBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/timetables/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTimeTable() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal1.setTime(sdf1.parse("31-05-2022 08:00:00+0000"));

        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal2.setTime(sdf2.parse("31-05-2022 08:10:00+0000"));

        mvc.perform( MockMvcRequestBuilders
                .post("/timetables")
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(),true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.timeOfArrival").value("31-05-2022 08:00:00+0000"))
                .andExpect(jsonPath("$.timeOfDeparture").value("31-05-2022 08:10:00+0000"))
                .andExpect(jsonPath("$.regular").value(true));
    }

    @Test
    public void createTimeTableTimeOfArrivalAfterTimeOfDeparture() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal1.setTime(sdf1.parse("31-05-2022 08:20:00+0000"));

        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal2.setTime(sdf2.parse("31-05-2022 08:10:00+0000"));

        mvc.perform( MockMvcRequestBuilders
                .post("/timetables")
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(),true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message")
                        .value("Time of arrival must be before time of departure"));
    }

    @Test
    public void createTimeTableInvalidDateFormat() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal1.setTime(sdf1.parse("31-05-2022 08:00:00+0000"));

        mvc.perform( MockMvcRequestBuilders
                .post("/timetables")
                .content(asJsonString(new TimeTable(cal1.getTime(), null,true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message")
                        .value("Invalid date format"));
    }

    @Test
    public void addTimeTable() throws Exception {
        TimeTable timeTable = newTimeTable();
        RouteStation routeStation = newRouteStation();
        String url = "/timetables/addtimetable?timetable=" + timeTable.getId() + "&routestation=" + routeStation.getId();
        mvc.perform( MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.routeStation").exists());
    }

    @Test
    public void addTimeTableBadRequest() throws Exception {
        TimeTable timeTable = newTimeTable();
        String url = "/timetables/addtimetable?timetable=" + timeTable.getId() + "?routestation=abc";
        mvc.perform( MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTimeTable() throws Exception {
        TimeTable timeTable = newTimeTable();
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal1.setTime(sdf1.parse("31-07-2022 18:00:00+0000"));

        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal2.setTime(sdf2.parse("31-07-2022 18:10:00+0000"));

        mvc.perform( MockMvcRequestBuilders
                .put("/timetables/{id}", timeTable.getId())
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(), true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeOfArrival").value("31-07-2022 18:00:00+0000"))
                .andExpect(jsonPath("$.timeOfDeparture").value("31-07-2022 18:10:00+0000"))
                .andExpect(jsonPath("$.regular").value(true));
    }

    @Test
    public void updateTimeTableBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .put("/timetables/{id}", "abc")
                .content(asJsonString(new TimeTable(null, null, false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public TimeTable deleteTimeTable() throws Exception {
        TimeTable timeTable = newTimeTable();
        mvc.perform( MockMvcRequestBuilders
                .delete("/timetables/{id}", timeTable.getId()) )
                .andExpect(status().isOk());
        return timeTable;
    }

    @Test
    public void deleteTimeTableNoValue() throws Exception {
        TimeTable timeTable = deleteTimeTable();
        mvc.perform( MockMvcRequestBuilders
                .delete("/timetables/{id}", timeTable.getId()) )
                .andExpect(status().isInternalServerError());
    }

    public TimeTable newTimeTable() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal1.setTime(sdf1.parse("31-05-2022 08:00:00+0000"));

        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ", Locale.ENGLISH);
        cal2.setTime(sdf2.parse("31-05-2022 08:10:00+0000"));

        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/timetables")
                .content(asJsonString(new TimeTable(cal1.getTime(),
                        cal2.getTime(),true)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(json, TimeTable.class);
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
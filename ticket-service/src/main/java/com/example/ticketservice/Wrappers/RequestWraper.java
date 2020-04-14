package com.example.ticketservice.Wrappers;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RequestWraper {
    @NotNull(message = "User can not be null")
    private Integer userId;
    List<Integer> routes;
    String month;

    public RequestWraper(@NotNull(message = "User can not be null") Integer userId, List<Integer> routes, String month) {
        this.userId = userId;
        this.routes = routes;
        this.month = month;
    }

    public List<Integer> getRoutes() {
        return routes;
    }

    public String getMonth() {
        return month;
    }

    public Integer getUserId() {
        return userId;
    }
}

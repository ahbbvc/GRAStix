package com.example.routeservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull(message = "Time of arrival cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ssZ")
    @JsonDeserialize(using = CustomJsonDeserializer.class)
    @Future(message = "Time of arrival must be in the future")
    private Date timeOfArrival;

    @Column
    @NotNull(message = "Time of departure cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ssZ")
    @JsonDeserialize(using = CustomJsonDeserializer.class)
    @Future(message = "Time of departure must be in the future")
    private Date timeOfDeparture;

    private boolean regular;

    @ManyToOne
    @JoinColumn(name = "routeStation_id")
    private RouteStation routeStation;

    public TimeTable() {
    }

    public TimeTable(Date timeOfArrival, Date timeOfDeparture, boolean regular) {
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.regular = regular;
    }

    public Integer getId() {
        return id;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public Date getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public boolean isRegular() {
        return regular;
    }

    public RouteStation getRouteStation() {
        return routeStation;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeOfArrival(Date timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public void setTimeOfDeparture(Date timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public void setRouteStation(RouteStation routeStation) {
        this.routeStation = routeStation;
    }

}

package com.example.systemevents.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String timeStamp;
    private String microservice;
    private String action;
    private String resource;
    private String response;

    public Event() {

    }

    public Event(String timeStamp, String microservice, String action, String resource, String response) {
        this.timeStamp = timeStamp;
        this.microservice = microservice;
        this.action = action;
        this.resource = resource;
        this.response = response;
    }
}

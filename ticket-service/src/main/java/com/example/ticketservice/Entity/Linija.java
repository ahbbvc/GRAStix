package com.example.ticketservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Linija{
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer linijaId;
    private String nazivLinije;
    private String vrstaPrevoza;
}

package com.example.ticketservice.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class PojedinacnaKarta{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer kartaId;
    private Integer korisnikId;
    private Date vrijemeIDatum;
    




}

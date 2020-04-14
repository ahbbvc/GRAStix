# GRAStix

Online prodaja karata za gradski prevoz

### Servisi

* user-service
* ticket-service
* route-service

### Preduslovi

* Java verzija 8+
* Samo jedan korisnik može biti povezan na bazu
* Potrebno je pokrenuti sve servise prije pokretanja aplikacije i testova u ticket-service

### Pokretanje aplikacije

Izvršiti komandu: ```mvn spring-boot:run``` u folderu servisa

Prvo pokrenuti eureka-server

### Pokretanje testova

Izvršiti komandu: ```mvn test``` u folderu servisa

Napomena: Prilikom pokretanja testova za ticket-service potrebno je pokrenuti i sve ostale servise. Također, testove za route-service treba pokrenuti prije testova za ticket-service (zbog dodavanja unsa u bazu)


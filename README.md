# 🎫 GRAStix 

Online prodaja karata za gradski prevoz 

### Servisi

* user-service
* ticket-service
* route-service

### Preduslovi

* Java verzija 8+
* Samo jedan korisnik može biti povezan na bazu
* Potrebno je pokrenuti sve servise prije pokretanja aplikacije i testova u ticket-service
* Testove za route-service treba pokrenuti prije testova za ticket-service (zbog dodavanja unosa u bazu)

### Pokretanje aplikacije

Izvršiti komandu: ```mvn spring-boot:run``` u folderu servisa

Prvo pokrenuti eureka-server

### Pokretanje testova

Izvršiti komandu: ```mvn test``` u folderu servisa

### Napomena za dugi dio projekta
* Treba biti instaliran rabbitmq
* Prvo se porekce eureka-server, pa system-events pa tek onda preostali servisi
* Sa rolom admin se login podacima mail: admin2@mail.com password: admin


### Demo snimci
https://drive.google.com/drive/folders/1UpAbdVip71abq4ymrrr_Bt5hxrvasr1j?usp=sharing



# 2021 Syksy - OTP R21 - Fotos
Jüri Tihane, Petri Immonen, Kalle Voutilainen ja Janne Kaukua

# Key product goals:
Tuote tuo tuloja "yhtiölle", kun premium-käyttäjät maksavat tuotteesta kuukausimaksua (free-käyttäjille mainoksia? -> mainostulot / Rajallinen määrä tilaa).

Tuote lisää yhtiön tunnettuutta ja tuo brändiä asiakkaiden tietoisuuteen mahdollisesti lisäten tulevaisuudessa asiakkaita muille yhtiön tuotteille.
# Customer:
Valokuvaajat ja henkilöt, jotka haluavat jakaa kuvia kavereille tai vain saada kuvansa parempaan talteen kuin omalle tietokoneelle.
# Need:
Paikallisesti tietokoneelle tallennetut kuvat ovat vaarassa tuhoutua esim. kovalevyn hajotessa, tulipalossa, murtovarkaudessa jne. Pilvipalvelussa kuvat olisivat tallennettuna useaan fyysiseen paikkaan. Käyttäjällä ei välttämättä ole myöskään tilaa kaikille kuvilleen.

Paikallisesti tallennetut kuvat ovat saatavilla vain kyseisellä koneella fyysisesti yhdessä paikassa. Pilvipalvelun kautta kuvia voi katsella miltä vain laitteelta eri paikoissa ja niitä on helppo jakaa muiden katseltaviksi. 
# Critical features for user:
Kuvien lisääminen ja siirtäminen kansiosta toiseen. Kansioiden luonti. Kuvien poistaminen. Kuvien siirtäminen pilveen antaen käyttäjän laitteelle lisää tilaa. Enemmän tästä “Kuvapalvelun ominaisuuksia” kohdassa.
# Competition:
Google kuvat
iCloud
# Primary differentiation:
Google kuvat jakaa ilmaisen tilan muiden googlen pilvipalvelujen kanssa. Meidän Kuvapalvelussa kaikki käytettävissä oleva tila on kuville.
# Kuvapalvelun ominaisuuksia:
Käyttäjä voi luoda uudet käyttäjätunnukset palveluun päästäkseen tallentamaan kuvia.

Käyttäjä voi kirjautua palveluun päästäkseen tallentamaan/poistamaan/katselemaan kuvia.

Käyttäjä voi kirjautua ulos palvelusta jotteivat muut tietokoneen käyttäjät pääse katsomaan hänen kuviaan.

Käyttäjä voi poistaa tilinsä jos ei halua enää käyttää palvelua.

Käyttäjä voi lisätä monta kuvaa kerralla kansioon jotta saa tärkeät kuvansa talteen pilvipalveluun.

Käyttäjä voi poistaa kuvia kansiosta, jotta pääsee eroon ei-toivotuista kuvista.

Käyttäjä voi lisätä albumiinsa kansion jotta voi organisoida kuvansa selkeämpään järjestykseen.

Käyttäjä voi poistaa kansion albumistansa päästäkseen eroon turhaksi käyneestä kansiosta.

Käyttäjä voi jakaa kuvien katseluoikeuksia muille käyttäjille näyttääkseen kuvia muille.

Käyttäjä voi poistaa kuvien katseluoikeuksia jos haluaa, etteivät muut enää pääse katsomaan kuvia.

Käyttäjä voi hakea albumistaan kuvia hakukriteerien avulla (esim päivämäärä jolloin kuva otettu), jotta voi katsella kuvia tietystä tilanteesta tai järjestellä niitä uudelleen.

Käyttäjä voi tilata kuukausimaksulla lisää tilaa kuvillensa (Premium-käyttäjä) jotta saa tallennettua enemmän kuvia.

Käyttäjä voi asettaa kuvia julkiseksi, jolloin ne näkyvät kaikille, jotta voisi jakaa kuvia muiden katseltavaksi määrittelemättä erikseen eri käyttäjiä.

Rekisteröimätön käyttäjä voi katsella julkisia kuvia palvelussa / sovelluksessa.

Rekisteröimätön käyttäjä ei voi ladata kuvia palveluun / sovellukseen.

Käyttäjä voi vaihtaa salasanansa jotta tietoturva.

Käyttäjä voi tarkastella yksittäisiä kuvia tarkemmin jotta näkee upeat kuvansa.

Käyttäjä voi ladata kuviansa omalle tietokoneelleen jotta hän voi katsella niitä myös ilman internetyhteyttä tai jakaa muille esim muistitikulla.

Ylläpitäjä voi luoda ylläpito tilin auttaakseen asiakkaita ongelmien ratkaisemisessa.

Ylläpitäjä voi resetoida käyttäjän salasanan esim. jos on epäilys, että salasana on vuotanut ulkopuolisille.

Ylläpitäjä voi poistaa käyttäjän tilin jos käyttäjä niin haluaa eikä pysty sitä itse tekemään.

# Tarkoitus

Tarkoituksena on tehdä pilvipalvelusovellus, johon käyttäjät voivat ladata
kuvia. Palvelussa käyttäjä voi tarkastella omia kuviaan, ladata niitä pilvestä omalle tietokoneelle
ja lajitella kuvia eri kansioihin joita käyttäjä voi tehdä.

# Kehitysympäristö ja projektin asennusohjeet Intellij Ideaan.

1. Hae Intellij lisenssi osoitteesta https://www.jetbrains.com/community/education/#students, joka on ilmainen Metropolia opiskelijoille ja opettajille.
2. Asenna Intellij Idea koneelle
3. Käynnistä Intellij Idea ja luo java 11 projekti
4. Kun java projekti on luotu, paina ylä-valikosta Git -> Enable Version Control
5. Git -> Manage Remotes -> Lisää uusi remote, jonka URL on https://gitlab.metropolia.fi/jurit/otp-r21-fotos
6. Terminaali -> git pull origin master -> Seuraa ohjeita, jotka tulee terminaaliin
7. Paina “Load Maven Project” ponnahdusikkunasta.
8. Kun saat projektin kehitysympäristöön, käynnistä Fotos.java luokka jolloin se kaatuu, mutta luo automaattisesti konfiguraation.
9. Paina ylä oikeasta kulmasta "Fotos" pudotus valikko nappi -> “Edit configurations” -> Kirjoita “Environment variables” kohtaan -> APP_DB_USERNAME=otpdb;APP_DB_PASSWORD=Qwertyuiop0987654321;APP_DB_URL=jdbc:mysql://10.114.32.13:3306/ -> Apply -> OK
10. Aja Fotos.java luokka uusiksi, jolloin ohjelma käynnistyy oikein yhdistämällä tietokantaan.

# JDK versio, käytetyt kirjastot ja niiden versionumerot

Java JDK 11, JavaFX 11.0.2, JUnit 5.8.0, mysql 8.0.26, jacoco 0.8.7

package nl.hu.dp.ovchip.Domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;




@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @Column (name = "adres_id")
    private  int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    public  Adres(){}
    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.id = id;
        this.reiziger = reiziger;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    @Override
    public String toString() {
        return "Adres{" + String.valueOf(this.id) + this.postcode + " " + this.huisnummer + " "+ this.straat  + " " + this.woonplaats + "} " +
                "Reiziger{"  +String.valueOf(this.reiziger.getId()) + " " + this.reiziger.getVoorletters()  + " " +
                (this.reiziger.getTussenvoegsel() == null ? "" : this.reiziger.getTussenvoegsel())  + " " + this.reiziger.getAchternaam() + " " + this.reiziger.getGeboorteDatum() + "}";
    }

}

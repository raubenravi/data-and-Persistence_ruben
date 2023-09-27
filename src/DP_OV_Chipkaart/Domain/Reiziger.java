package DP_OV_Chipkaart.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegel() {
        return tussenvoegel;
    }

    public void setTussenvoegel(String tussenvoegel) {
        this.tussenvoegel = tussenvoegel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public List<OvChipKaart> getOvkaarten() {
        return ovkaarten;
    }

    public void setOvkaarten(List<OvChipKaart> ovkaarten) {
        this.ovkaarten = ovkaarten;
    }

    private int id;
    private String voorletters;
    private String tussenvoegel;
    private String achternaam;
    private  java.sql.Date geboorteDatum;

    private   Adress adress;

    private List<OvChipKaart> ovkaarten;


    public Reiziger(int id, String voorletters, String tussenvoegel, String achternaam, java.sql.Date geboorteDatum, Adress adress){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegel = tussenvoegel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adress = adress;
        this.ovkaarten = new ArrayList<OvChipKaart>();
    }
    public Reiziger(int id, String voorletters, String tussenvoegel, String achternaam, java.sql.Date geboorteDatum, Adress adress,  List<OvChipKaart> ovkaarten) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegel = tussenvoegel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adress = adress;
        this.ovkaarten = ovkaarten;
    }

    public String toString(){
        return String.valueOf(this.id) + " " + this.voorletters  + " " + this.tussenvoegel + " " + this.achternaam + " " + this.geboorteDatum;

    }
}


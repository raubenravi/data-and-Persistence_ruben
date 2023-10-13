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

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
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

    public Adres getAdress() {
        return adres;
    }

    public void setAdress(Adres adres) {
        this.adres = adres;
    }

    public List<OvChipKaart> getOvkaarten() {
        return ovkaarten;
    }

    public void setOvkaarten(List<OvChipKaart> ovkaarten) {
        this.ovkaarten = ovkaarten;
    }

    public boolean verwijderOVChipkaart (OvChipKaart ovKaart){
        try {
            for(OvChipKaart huidigeKaart : this.ovkaarten ){
                if (ovKaart.getId() == huidigeKaart.getId() ){
                    ovkaarten.remove(huidigeKaart);
                    return true;
                }
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean voegToeOVChipkaart  (OvChipKaart ovKaart){
        try {
            ovkaarten.add(ovKaart);
            return true;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private  java.sql.Date geboorteDatum;

    private Adres adres;

    private List<OvChipKaart> ovkaarten;


    public Reiziger(int id, String voorletters, String tussenvoegel, String achternaam, java.sql.Date geboorteDatum, Adres adres){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adres = adres;
        this.ovkaarten = new ArrayList<OvChipKaart>();
    }
    public Reiziger(int id, String voorletters, String tussenvoegel, String achternaam, java.sql.Date geboorteDatum, Adres adres, List<OvChipKaart> ovkaarten) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adres = adres;
        this.ovkaarten = ovkaarten;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Reiziger{")
                .append(id).append(" ")
                .append(voorletters).append(" ")
                .append(tussenvoegsel != null ? tussenvoegsel + " " : "")
                .append(achternaam).append(" ")
                .append(geboorteDatum).append("}");

        if (adres != null) {
            builder.append(" Adres{")
                    .append(adres.getPostcode()).append(" ")
                    .append(adres.getHuisnummer()).append(" ")
                    .append(adres.getStraat()).append(" ")
                    .append(adres.getWoonplaats()).append("}");
        }

        return builder.toString();
    }


}


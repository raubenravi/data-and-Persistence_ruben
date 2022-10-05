package DP_OV_Chipkaart.Domain;

public class Adress {
    public  int adress_id;
    public Reiziger reiziger;
    public String postcode;
    public String huisnummer;
    public String straat;
    public String woonplaats;

    public Adress(int adress_id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.adress_id = adress_id;
        this.reiziger = reiziger;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

}

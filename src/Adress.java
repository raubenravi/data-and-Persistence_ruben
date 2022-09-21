public class Adress {
    public  int adress_id;
    public int reiziger_id;
    public String postcode;
    public String huisnummer;
    public String straat;
    public String woonplaats;

    public Adress(int reiziger_id, String postcode, String huisnummer, String straat, String woonplaats){
        this.reiziger_id = reiziger_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;

    }

}

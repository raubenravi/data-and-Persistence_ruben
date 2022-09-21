public class Reiziger {
    public Integer reiziger_id;
    public String voorletters;
    public String tussenvoegel;
    public String achternaam;
    public  java.sql.Date geboorteDatum;

    public   Adress adress;


    public Reiziger(Integer reiziger_id, String voorletters, String tussenvoegel, String achternaam, java.sql.Date geboorteDatum, Adress adress){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegel = tussenvoegel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
        this.adress = adress;
    }

    public String toString(){
        return String.valueOf(this.reiziger_id) + " " + this.voorletters  + " " + this.tussenvoegel + " " + this.achternaam + " " + this.geboorteDatum;

    }
}


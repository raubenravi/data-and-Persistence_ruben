package DP_OV_Chipkaart.Domain;

import java.util.List;

public class OvChipKaart {
    public int kaart_nummer;
    public  int klasse;
    public java.sql.Date getlig_tot;
    public int saldo;
    public Reiziger reiziger;

    public List<Product> products;

    public OvChipKaart(int kaartNummer, java.sql.Date getlig_tot, Reiziger reiziger){
    }
}

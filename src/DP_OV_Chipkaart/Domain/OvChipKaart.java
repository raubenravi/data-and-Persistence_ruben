package DP_OV_Chipkaart.Domain;

import java.sql.Date;
import java.util.List;

public class OvChipKaart {
    private int id;
    private  int klasse;
    private java.sql.Date geldigTot;
    private double saldo;
    private Reiziger reiziger;

    public List<Product> products;

    public OvChipKaart(int id, int klasse, java.sql.Date geldigTot, double saldo, Reiziger reiziger){
        this.id = id;
        this.klasse = klasse;
        this.geldigTot = geldigTot;
        this.saldo = saldo;
        this.reiziger = reiziger;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

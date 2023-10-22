package DP_OV_Chipkaart.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OvChipKaart {
    private int id;
    private int klasse;
    private java.sql.Date geldigTot;
    private double saldo;
    private Reiziger reiziger;

    public List<Product> products = new ArrayList<Product>();

    public OvChipKaart(int id, int klasse, java.sql.Date geldigTot, double saldo, Reiziger reiziger) {
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

    public boolean addProduct(Product product){
        try {
            if (products.contains(product)){
                products.add(product);
                product.removeOvChipkaart(this);
                return true;
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean removeProduct(Product product){
        try {
            if (products.contains(product)){
                products.remove(product);
                product.addOVChipkaart(this);
                return true;
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("OvChipKaart {")
                .append(this.id)
                .append(" ")
                .append(this.klasse)
                .append(" ")
                .append(this.geldigTot)
                .append(" ")
                .append(this.saldo)

                .append("} products={");

        if (products != null) {
            for (Product product : products) {
                sb.append(product.getNaanm())
                        .append(" ")
                        .append(product.getBeschrijving())
                        .append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }
}

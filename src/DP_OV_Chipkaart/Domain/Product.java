package DP_OV_Chipkaart.Domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productNummer;
    private String naanm;
    private String Beschrijving;
    private int prijs;
    private List<OvChipKaart> ovchipkaarten = new ArrayList<OvChipKaart>();

    public Product(int productNummer, String naam, String beschrijving, int prijs) {
        this.productNummer = productNummer;
        this.naanm = naam;
        this.Beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public boolean addOVChipkaart(OvChipKaart ovKaart){
        try {
            if (!ovchipkaarten.contains(ovKaart)){
                ovchipkaarten.add(ovKaart);
                ovKaart.addProduct(this);
                return true;
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean removeOvChipkaart(OvChipKaart ovKaart){
        try {
            if (ovchipkaarten.contains(ovKaart)){
                ovchipkaarten.remove(ovKaart);
                ovKaart.removeProduct(this);
                return true;
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public int getProductNummer() {
        return productNummer;
    }
    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }
    public String getNaanm() {
        return naanm;
    }
    public void setNaanm(String naanm) {
        this.naanm = naanm;
    }
    public String getBeschrijving() {
        return Beschrijving;
    }
    public void setBeschrijving(String beschrijving) {
        Beschrijving = beschrijving;
    }
    public int getPrijs() {
        return prijs;
    }
    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
    public List<OvChipKaart> getOvchipkaarts() {
        return ovchipkaarten;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{")
                .append(productNummer)
                .append(" ")
                .append(naanm)
                .append(", beschrijving=")
                .append(Beschrijving)
                .append(" ")
                .append(prijs)
                .append("} ovChipKaaren{");

        if (ovchipkaarten != null) {
            for (OvChipKaart ovKaart : ovchipkaarten) {
                sb.append(ovKaart.getId())
                        .append(", ")
                        .append(ovKaart.getReiziger().getId())
                        .append(", ")
                        .append(ovKaart.getReiziger().getAchternaam())
                        .append(", ");;
            }
        }

        sb.append("}");

        return sb.toString();
    }


}

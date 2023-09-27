package DP_OV_Chipkaart.Domain;

import java.util.List;

public class Product {
    private int productNummer;
    private String naanm;
    private String Beschrijving;
    private int prijs;
    private List<OvChipKaart> ovchipkaarts;

    public Product(int productNummer, String naam, String beschrijving, int prijs) {
        this.productNummer = productNummer;
        this.naanm = naam;
        this.Beschrijving = beschrijving;
        this.prijs = prijs;
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
        return ovchipkaarts;
    }

    public void setOvchipkaarts(List<OvChipKaart> ovchipkaarts) {
        this.ovchipkaarts = ovchipkaarts;
    }
}

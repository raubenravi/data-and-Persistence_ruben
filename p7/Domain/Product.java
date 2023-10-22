package nl.hu.dp.ovchip.Domain;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column (name = "product_nummer")
    private int productNummer;

    private String naam;
    private String Beschrijving;
    private int prijs;

    //bron: https://www.baeldung.com/hibernate-many-to-many
    @ManyToMany(mappedBy = "products")
    private List<OvChipKaart> ovchipkaarten = new ArrayList<OvChipKaart>();;

    public Product(){}
    public Product(int productNummer, String naam, String beschrijving, int prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.Beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public boolean voegToeOVChipkaart  (OvChipKaart ovKaart){
        try {
            this.ovchipkaarten.add(ovKaart);
            return true;
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
    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
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
                .append(naam)
                .append(", beschrijving=")
                .append(Beschrijving)
                .append(" ")
                .append(prijs)
                .append("} ovChipKaaren{");

        if (this.ovchipkaarten != null) {
            for (OvChipKaart ovKaart : this.ovchipkaarten) {
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

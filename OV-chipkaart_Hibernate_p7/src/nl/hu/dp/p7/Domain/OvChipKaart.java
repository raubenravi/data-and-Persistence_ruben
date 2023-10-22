package nl.hu.dp.ovchip.Domain;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table (name = "ov_chipkaart")
public class OvChipKaart {
    @Id
    @Column(name = "kaart_nummer")
    private int id;
    private int klasse;
    @Column(name = "geldig_tot")
    private java.sql.Date geldigTot;
    private double saldo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;
    @ManyToMany(mappedBy = "ovchipkaarten")
    private List<Product> products;

    public  OvChipKaart(){}
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

    public boolean voegToeProduct  (Product product){
        try {
            this.products.add(product);
            return true;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
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
                .append(" ")
                .append(this.reiziger.getAchternaam())

                .append("} products={");

        if (this.products != null) {
            for (Product product : this.products) {
                sb.append(product.getNaam())
                        .append(" ");
            }
        }

        sb.append("}");

        return sb.toString();
    }
}


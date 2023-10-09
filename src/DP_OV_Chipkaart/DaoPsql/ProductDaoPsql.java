package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoPsql implements ProductDao {

    private Connection connection;

    public void setOvChipKaartDao(OvChipkaartDao ovChipKaartDao) {
        this.ovChipKaartDao = ovChipKaartDao;
    }

    OvChipkaartDao ovChipKaartDao;

    public ProductDaoPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection,  OvChipkaartDao ovChipKaartDao){
        this.connection = connection.getConnection();
        this.ovChipKaartDao = ovChipKaartDao;
    }
    public boolean save(Product product) throws SQLException {
        String q = "INSERT INTO public.product(product_nummer , naam, beschrijving , prijs " +
                "VALUES(?, ?, ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, product.getProductNummer());
        pst.setString(2, product.getNaanm());
        pst.setString(3, product.getBeschrijving());
        pst.setInt(4, product.getPrijs());
        pst.execute();
        pst.close();

        /*
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        for(OvChipKaart ovChipKaart : product.getOvchipkaarts()){
            q = "INSERT INTO public.produc (product_nummer , naam , beschrijving , prijs )"  +
                    "VALUES(?, ?, ?, ?) ;" ;
            pst.setInt(1,  ovChipKaart.getId());
            pst.setInt(2, product.getProductNummer());
            pst.setString(3, "saved");
            pst.setDate(4, sqlDate );

        } */
        return true;
    }
    public boolean update(Product product) throws SQLException {
        String q = "UPDATE product " +
                "SET  naam = ?, beschrijving = ? , prijs = ?" +
                "WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setString(1, product.getNaanm());
        pst.setString(2, product.getBeschrijving());
        pst.setInt(3, product.getPrijs());
        pst.setInt(4, product.getProductNummer());
        pst.execute();

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        for(OvChipKaart ovChipKaart : product.getOvchipkaarts()){
            q = " UPDATE ov_chipkaart_product SET kaart_nummer = ? , status = ? , last_update = ? "  +
                    "WHERE product_nummer = ?;";
            pst = connection.prepareStatement(q);
            pst.setInt(1,  ovChipKaart.getId());
            pst.setString(3, "actief");
            pst.setDate(4, sqlDate );
            pst.execute();
        }
        pst.close();
        return true;
    }
    public boolean delete(Product product) throws SQLException {
        String q = "DELETE FROM product WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1,product.getProductNummer());
        pst.execute();
        q = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?;";
        pst.setInt(1,product.getProductNummer());
        pst.execute();
        pst.close();
        return true;
    }

    public List<Product> findByOVchipkaart(OvChipKaart ovChipkaart) throws Exception {
        try {
            String q = "SELECT product.product_nummer, ov_chipkaart_product.kaart_nummer , product.naam, product..beschrijving , product..prijs" +
                    "FROM product" +
                    "JOIN ov_chipkaart_product" +
                    "ON ov_chipkaart_product.product_nummer = product.product_nummer" +
                    "where ov_chipkaart_product.kaart_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, ovChipkaart.getId());
            ResultSet resultSet = pst.executeQuery();
            List<Product> lijst = new ArrayList<>();
            while (resultSet.next()) {
                int productNummer = resultSet.getInt(1);
                int ovChipkaartNummer = resultSet.getInt(2);
                OvChipKaart ovChipKaart = ovChipKaartDao.findByNR(ovChipkaartNummer);
                String naam = resultSet.getString(3);
                String beschrijving = resultSet.getString(4);
                int prijs = resultSet.getInt(5);

                Product product = new Product(productNummer, naam, beschrijving, prijs);
                product.voegToeOVChipkaart(ovChipKaart);
                lijst.add(product);
            }

            pst.close();

            return lijst;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public List<Product> findAll() throws SQLException{
        try {
            String q = "SELECT product.product_nummer, product.naam, product.beschrijving , product.prijs" +
                    "FROM product";
            PreparedStatement pst = connection.prepareStatement(q);
            ResultSet resultSet = pst.executeQuery();
            List<Product> lijst = new ArrayList<>();
            while (resultSet.next()) {
                int productNummer = resultSet.getInt(1);
                String naam = resultSet.getString(3);
                String beschrijving = resultSet.getString(4);
                int prijs = resultSet.getInt(5);

                Product product = new Product(productNummer, naam, beschrijving, prijs);


                lijst.add(product);
            }

            for(Product product : lijst){
                q = "SELECT ov_chipkaart_product.kaart_nummer " +
                        "FROM product" +
                        "JOIN ov_chipkaart_product" +
                        "ON ov_chipkaart_product.product_nummer = product.product_nummer" +
                        "where ov_chipkaart_product.product_nummer = ?";

                pst.setInt(1, product.getProductNummer());
                resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    int ovkaartNummer = resultSet.getInt(1);
                    OvChipKaart ovChipKaart = ovChipKaartDao.findByNR(ovkaartNummer);
                    product.voegToeOVChipkaart(ovChipKaart);
                }



            }
            pst.close();

            return lijst;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}

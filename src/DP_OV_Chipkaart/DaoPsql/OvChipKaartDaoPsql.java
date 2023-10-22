package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OvChipKaartDaoPsql implements OvChipkaartDao {

    private Connection connection;
    private ReizigerDAOPsql reizigerDAO;
    private  ProductDao productDao;


    public void setReizigerDAOPsql(ReizigerDAOPsql reizigerDAO){
        this.reizigerDAO = reizigerDAO;
    }
    public OvChipKaartDaoPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection, ProductDao productDao, ReizigerDAOPsql reizigerDAO) throws SQLException {
        this.connection = connection.getConnection();
        this.productDao = productDao;
        this.reizigerDAO = reizigerDAO;
    }


    public boolean save(OvChipKaart ovKaart) throws Exception {
        String q = "INSERT INTO ov_chipkaart( kaart_nummer ,geldig_tot ,klasse , saldo, reiziger_id) VALUES(?, ?, ?, ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, ovKaart.getId());
        pst.setDate(2, ovKaart.getGeldigTot());
        pst.setInt(3, ovKaart.getKlasse());
        pst.setDouble(4, ovKaart.getSaldo());
        pst.setInt(5, ovKaart.getReiziger().getId());
        pst.execute();
        pst.close();


        q = "INSERT INTO public.ov_chipkaart_product(kaart_nummer,product_nummer, status, last_update" +
                "VALUES(?, ?, ?, ?) ;" ;
        if(ovKaart.products != null){
            for(Product product : ovKaart.products){
                pst = connection.prepareStatement(q);
                pst.setInt(1, ovKaart.getId());
                pst.setInt(2, product.getProductNummer());
                pst.setString(3, product.getBeschrijving());
                pst.setDate(4, (java.sql.Date) Calendar.getInstance().getTime());
                pst.execute();
                productDao.save(product);
            }
        }
        //ov_chip_product
        pst.close();
        return true;
    }


    public boolean update(OvChipKaart ovKaart) throws SQLException {
        try {
            String q = "UPDATE ov_chipkaart " +
                    "SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? " +
                    "WHERE kaart_nummer = ?";

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setDate(1, ovKaart.getGeldigTot());
            pst.setInt(2, ovKaart.getKlasse());
            pst.setDouble(3, ovKaart.getSaldo());
            pst.setInt(4, ovKaart.getReiziger().getId());
            pst.close();
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(OvChipKaart ovKaart) throws SQLException {
        try {
            String q = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,ovKaart.getId());
            pst.execute();
            q = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;";
            pst = connection.prepareStatement(q);
            pst.setInt(1,ovKaart.getId());
            pst.execute();
            pst.close();
            return true;
        } catch (Exception e){
            return  false;
        }

    }

    public OvChipKaart findByNR(int id) throws Exception {
        String q = "Select * FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        id = resultSet.getInt(1);
        java.sql.Date geldigTot = resultSet.getDate(2);
        int klasse = resultSet.getInt(3);
        double saldo = resultSet.getDouble(4);
        int reizigerId = resultSet.getInt(5);
        Reiziger reiziger = reizigerDAO.findById(reizigerId);
        OvChipKaart ovkaart = new OvChipKaart(id, klasse, geldigTot, saldo, reiziger );
        for(Product product : productDao.findByOVchipkaart(ovkaart)){
            ovkaart.addProduct(product);
        }
        pst.close();
        return ovkaart;
    }

    public List<OvChipKaart> findByReiziger(Reiziger reiziger) throws Exception {
        List<OvChipKaart> lijst = new ArrayList<>();
        String q = "Select * FROM ov_chipkaart WHERE reiziger_id = ? " ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1,reiziger.getId());
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            java.sql.Date geldigTot = resultSet.getDate(2);
            int klasse = resultSet.getInt(3);
            double saldo = resultSet.getDouble(4);
            int reizigerId = resultSet.getInt(5);
            OvChipKaart ovkaart = new OvChipKaart(id, klasse, geldigTot, saldo, reiziger );

            List<Product> products = productDao.findByOVchipkaart(ovkaart);
            if (products != null) {
                for (Product product : products) {
                    ovkaart.addProduct(product);
                }
            }

            lijst.add(ovkaart);
        }
        pst.close();
        return lijst;
    }

    public List<OvChipKaart> findAll() throws Exception {
        List<OvChipKaart> lijst = new ArrayList<>();
        String q = "Select * FROM ov_chipkaart  ";
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            java.sql.Date geldigTot = resultSet.getDate(2);
            int klasse = resultSet.getInt(3);
            double saldo = resultSet.getDouble(4);
            int reizigerId = resultSet.getInt(5);
            Reiziger reiziger = reizigerDAO.findById(reizigerId);
            OvChipKaart ovkaart = new OvChipKaart(id, klasse, geldigTot, saldo, reiziger );
            List<Product> producten =  productDao.findByOVchipkaart(ovkaart);
            if(producten != null){
                for(Product product : producten){
                    ovkaart.addProduct(product);
                }
            }

            lijst.add(ovkaart);
        }
        pst.close();
        return lijst;
    }
}

package DP_OV_Chipkaart;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Dao.AdresDao;
import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Dao.ReizigerDao;
import DP_OV_Chipkaart.DaoPsql.AdresDAOPsql;
import DP_OV_Chipkaart.DaoPsql.OvChipKaartDaoPsql;
import DP_OV_Chipkaart.DaoPsql.ProductDaoPsql;
import DP_OV_Chipkaart.DaoPsql.ReizigerDAOPsql;
import DP_OV_Chipkaart.Domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




public class Main {
    public static void main(String [] args) throws Exception {

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        System.out.println(sqlDate);

        //P1
        ConnectionDatabase.ConnectionDatabaseIsntance connection = new  ConnectionDatabase.ConnectionDatabaseIsntance();
        String q = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger";
        PreparedStatement pst = connection.getConnection().prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            int reiziger_id = resultSet.getInt(1);
            String voorletters = resultSet.getString(2);
            String tussenvoegsel = resultSet.getString(3);
            String achternaam = resultSet.getString(4);
            java.sql.Date geboorteDatum = resultSet.getDate(5);
            System.out.println(reiziger_id + " " + voorletters + " " + (tussenvoegsel == null ? "" : tussenvoegsel)  + " " + achternaam + " (" + geboorteDatum + ")");
        }
        pst.close();
        ProductDaoPsql productDao = new ProductDaoPsql(connection, null);
        OvChipKaartDaoPsql ovChipkaartDao = new OvChipKaartDaoPsql(connection, productDao, null);
        AdresDAOPsql adressDAOPsql = new AdresDAOPsql(connection);
        ReizigerDAOPsql daoReiziger = new ReizigerDAOPsql(connection, adressDAOPsql, ovChipkaartDao);
        productDao.setOvChipKaartDao(ovChipkaartDao);
        adressDAOPsql.setReizigerDAOPsql(daoReiziger);

      //  testReizigerDAO(daoReiziger);
        //testReizigerP3(daoReiziger, adressDAOPsql);
        ovChipkaartDao.setReizigerDAOPsql(daoReiziger);
        testp5(productDao, ovChipkaartDao);
        connection.getConnection().close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDao rdao) throws Exception {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum), null);
        System.out.println(sietske.toString());
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.println("het is zijn trouwdag en sietske krijgt een extra achernaam ");
        sietske.setAchternaam("Broers-Oranje");
        rdao.update(sietske);
        System.out.println(rdao.findById(sietske.getId()));


        reizigers = rdao.findAll();
        System.out.println("reizigers voor ReizigerDAO.delete(): " + reizigers.size()  + "\n");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(" reizigers na ReizigerDAO.delete() : "+ reizigers.size() + " reizigers\n");
        System.out.println(rdao.findById(sietske.getId()));
    }


    private static void testReizigerP3(ReizigerDao rdao, AdresDao adao) throws Exception {
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r.toString());
        }
        System.out.println();

        Reiziger reiziger = rdao.findById(1);
        System.out.println(reiziger);
        Adres backupAdres = reiziger.getAdress();
        System.out.println("De reiziger gaat naar Groot Britanie verhuizen");
        Adres adres = new Adres(25, "1122BB", "22", "Diagon Alley", "London", reiziger);
        reiziger.setAdress(adres);
        rdao.update(reiziger);
        reiziger = rdao.findById(1);
        System.out.println(reiziger.toString());
        System.out.print("De reiziger gaat wordt premie: ");
        adres.setStraat("Downing street");
        adao.update(adres);
        reiziger = rdao.findById(1);
        System.out.println(reiziger);
        System.out.println("test adao.findByReiziger: " + adao.findByReiziger(reiziger));
        //brexit, terug naar nederland dan maar weer
        reiziger.setAdress(backupAdres);
        rdao.update(reiziger);

        System.out.println();

        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a.toString());
        }
        System.out.println();


    }


    private  static  void testp5(ProductDao pdao, OvChipkaartDao ovChipkaartDao) throws Exception {

        for(OvChipKaart ovChipKaart : ovChipkaartDao.findAll()){
            System.out.println(ovChipKaart.toString());
        }


        for(Product product : pdao.findAll()){
            System.out.println(product.toString());
        }

    }

}


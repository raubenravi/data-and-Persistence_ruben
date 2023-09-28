package DP_OV_Chipkaart;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Dao.AdresDao;
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
        ProductDaoPsql productDao = new ProductDaoPsql(connection);
        OvChipKaartDaoPsql ovChipkaartDao = new OvChipKaartDaoPsql(connection, productDao, null);
        AdresDAOPsql adressDAOPsql = new AdresDAOPsql(connection);
        ReizigerDAOPsql daoReiziger = new ReizigerDAOPsql(connection, adressDAOPsql, ovChipkaartDao);
        adressDAOPsql.setReizigerDAOPsql(daoReiziger);
        testReizigerDAO(daoReiziger);
        testReizigerP3(daoReiziger, adressDAOPsql);
        connection.getConnection().close();
        /*
        String gbdatum = "1981-03-14";
        Adress adress = new Adress(25, "1122BB", "22a", "KorteStraat", "London", null);
        Reiziger sietske = new Reiziger(80, "S", "a", "Boers", java.sql.Date.valueOf(gbdatum), adress);


        Adress adressJan = new Adress(22, "1133AA", "23", "LangeStraat", "China", null);
        Reiziger jan = new Reiziger(72, "j", "met de", "LangePet", java.sql.Date.valueOf(gbdatum), adressJan);
        adressJan.setReiziger(jan);




        //Reiziger sietske = new Reiziger(77, "S", "a", "Boers", java.sql.Date.valueOf(gbdatum), adress);
        adressJan.setReiziger(sietske);
        ProductDaoPsql productDao = new ProductDaoPsql(connection);
        OvChipKaartDaoPsql ovChipkaartDao = new OvChipKaartDaoPsql(connection, productDao, null);
        AdressDAOPsql adressDAOPsql = new AdressDAOPsql(connection);
        ReizigerDAOPsql daoReiziger = new ReizigerDAOPsql(connection, adressDAOPsql, ovChipkaartDao);
        ovChipkaartDao.setReizigerDAOPsql(daoReiziger);
        System.out.println(ovChipkaartDao.findById(35283).getSaldo());
        daoReiziger.save(jan);
        jan.setOvkaarten( ovChipkaartDao.findByReiziger(jan)) ;
        for(OvChipKaart ovkaart : jan.getOvkaarten()){
            System.out.println(ovkaart);
        }

        String geldigTot = "2025-05-25";
        OvChipKaart ovKaart = new OvChipKaart(54, 2,java.sql.Date.valueOf(geldigTot), 22.25D, jan );
        ovChipkaartDao.delete(ovKaart);
        ovChipkaartDao.save(ovKaart);

        //System.out.println(sietske.toString());


      //  daoReiziger.save(sietske);
       // adressDAOPsql.save(adress);
       // daoReiziger.save(jan);
      //  adressDAOPsql.save(adressJan);
        daoReiziger.delete(jan);


        List<Reiziger> list2 = daoReiziger.findAll();
        for (Reiziger reizigerOut : list2) {
            System.out.println(reizigerOut.toString());
        }
        jan.setAchternaam("Lange Pet");
       // daoReiziger.update(jan);
        daoReiziger.delete(sietske);
        List<Reiziger> list = daoReiziger.findAll();
        for (Reiziger reizigerOut : list) {
            System.out.println(reizigerOut.toString());
        }
        daoReiziger.delete(jan);

        //daoReiziger.findAll();
         */

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
        Adres adres = new Adres(25, "1122BB", "22", "Korte Straat", "London", reiziger);
        reiziger.setAdress(adres);
        rdao.update(reiziger);
        reiziger = rdao.findById(1);
        System.out.println(reiziger);
        adres.setStraat("Lange Straat");
        adao.update(adres);
        reiziger = rdao.findById(1);
        System.out.println(reiziger);
        reiziger.setAdress(backupAdres);
        rdao.update(reiziger);

        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a.toString());
        }
        System.out.println();


    }

}


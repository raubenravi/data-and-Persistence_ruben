package DP_OV_Chipkaart;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.DaoPsql.AdressDAOPsql;
import DP_OV_Chipkaart.DaoPsql.OvChipKaartDaoPsql;
import DP_OV_Chipkaart.DaoPsql.ProductDaoPsql;
import DP_OV_Chipkaart.DaoPsql.ReizigerDAOPsql;
import DP_OV_Chipkaart.Domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    }
}


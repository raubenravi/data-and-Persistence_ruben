package DP_OV_Chipkaart;

import DP_OV_Chipkaart.DaoPsql.AdressDAOPsql;
import DP_OV_Chipkaart.DaoPsql.ReizigerDAOPsql;
import DP_OV_Chipkaart.Domain.Adress;
import DP_OV_Chipkaart.Domain.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Main {



    public static void main(String [] args) throws SQLException {

        String gbdatum = "1981-03-14";
        Adress adress = new Adress(25, "1122BB", "22a", "KorteStraat", "London", null);
        Reiziger sietske = new Reiziger(77, "S", "a", "Boers", java.sql.Date.valueOf(gbdatum), adress);


        Adress adressJan = new Adress(22, "1133AA", "23", "LangeStraat", "China", null);
        Reiziger jan = new Reiziger(72, "j", "met de", "LangePet", java.sql.Date.valueOf(gbdatum), adressJan);
        adressJan.reiziger = jan;


       // Reiziger sietske = new Reiziger(77, "S", "a", "Boers", java.sql.Date.valueOf(gbdatum), adress);
        adress.reiziger = sietske;

        ConnectionDatabase.ConnectionDatabaseIsntance connenction = new  ConnectionDatabase.ConnectionDatabaseIsntance();

        //System.out.println(sietske.toString());
        AdressDAOPsql adressDAOPsql = new AdressDAOPsql(connenction);



        ReizigerDAOPsql daoReiziger = new ReizigerDAOPsql(connenction, adressDAOPsql);
        daoReiziger.save(sietske);
        adressDAOPsql.save(adress);
        daoReiziger.save(jan);
        adressDAOPsql.save(adressJan);


        List<Reiziger> list2 = daoReiziger.findAll();
        for (Reiziger reizigerOut : list2) {
            System.out.println(reizigerOut.toString());
        }
        jan.achternaam = "Lange Pet";
        daoReiziger.update(jan);
        daoReiziger.delete(sietske);
        List<Reiziger> list = daoReiziger.findAll();
        for (Reiziger reizigerOut : list) {
            System.out.println(reizigerOut.toString());
        }
        daoReiziger.delete(jan);

        //daoReiziger.findAll();
    }


}


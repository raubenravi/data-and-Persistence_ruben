package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Domain.Adres;
import DP_OV_Chipkaart.Dao.ReizigerDao;
import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.OvChipKaart;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDao {


    AdresDAOPsql adressDAOPsql;
    OvChipkaartDao ovChipKaartDao;
    Connection connection;

    public ReizigerDAOPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection, AdresDAOPsql adressDAOPsql, OvChipkaartDao ovChipKaartDao) throws SQLException {
        this.connection = connection.getConnection();
        this.adressDAOPsql = adressDAOPsql;
        this.ovChipKaartDao = ovChipKaartDao;
    }

    public boolean save(Reiziger reiziger) throws SQLException {
        try {
            String q = "INSERT INTO public.reiziger(reiziger_id , voorletters, tussenvoegsel , achternaam, geboortedatum) " +
                    "VALUES(?, ?, ? , ?, ?) ;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboorteDatum());
            pst.execute();

            //adres controleren al het bestaat, zo ja update anders save
            //TODO controleren met printen enzo
            if(adressDAOPsql.findByReiziger(reiziger) != null){
                adressDAOPsql.save(reiziger.getAdress());
            }

            //adres controleren al het bestaat, zo ja update anders save
            pst.close();
        }catch(Exception e) {
         System.out.println(e);
         return false;
        }

        return true;
    }

    public boolean update(Reiziger reiziger) throws SQLException {
        try {

            Adres oudAdres = adressDAOPsql.findByReiziger(reiziger);

            String q = "UPDATE reiziger " +
                    "SET reiziger_id = ? , voorletters = ?, tussenvoegsel = ? , achternaam = ?, geboortedatum = ?" +
                    "WHERE reiziger_id = ?;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboorteDatum());
            pst.setInt(6, reiziger.getId());
            pst.execute();
            pst.close();

            Adres nieuwAdres = reiziger.getAdress();
            if(oudAdres != null){
                if (oudAdres.getId() != nieuwAdres.getId()  ) {
                    adressDAOPsql.delete(oudAdres);
                    adressDAOPsql.save(nieuwAdres);
                }else {
                    adressDAOPsql.update(nieuwAdres);
                }
            }



            return true;
        }catch (Exception e){
            System.out.println(e);
            return  false;
        }
    }

    public boolean delete(Reiziger reiziger) throws SQLException {

        if(reiziger.getAdress() != null){
            adressDAOPsql.delete(reiziger.getAdress());
        }


        if(reiziger.getOvkaarten().isEmpty() == false){
            for(OvChipKaart ovkaart : reiziger.getOvkaarten()){
                ovChipKaartDao.delete(ovkaart);
            }
        }


        try {
            String q = "DELETE  FROM reiziger WHERE reiziger_id = ?;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            pst.execute();
            pst.close();
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
        return true;

}
    public Reiziger findById(int id) throws Exception {
        try {
            String q = "Select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Reiziger niet gevonden!");
                return null;
            }
            //resultSet.next();
            int reiziger_id = resultSet.getInt(1);
            String voorletters = resultSet.getString(2);
            String tussenvoegel = resultSet.getString(3);
            String achternaam = resultSet.getString(4);
            java.sql.Date geboorteDatum = resultSet.getDate(5);
            Reiziger reiziger = new Reiziger(reiziger_id, voorletters,tussenvoegel, achternaam, geboorteDatum, null);
            Adres adres = adressDAOPsql.findByReiziger(reiziger);
            reiziger.setAdress(adres);

            List<OvChipKaart> ovkaarten = ovChipKaartDao.findByReiziger(reiziger);
            reiziger.setOvkaarten(ovkaarten);
            pst.close();
            return reiziger;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public Reiziger findByGbdatum(java.sql.Date gbDatum) throws SQLException {
        String q = "Select * FROM reiziger WHERE = geboortedatum = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setDate(1, gbDatum);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        int reiziger_id = resultSet.getInt(1);
        String voorletters = resultSet.getString(2);
        String tussenvoegel = resultSet.getString(3);
        String achternaam = resultSet.getString(4);
        java.sql.Date geboorteDatum = resultSet.getDate(5);
        Reiziger reiziger = new Reiziger(reiziger_id, voorletters,tussenvoegel, achternaam, geboorteDatum, null);
        Adres adres = adressDAOPsql.findByReiziger(reiziger);
        reiziger.setAdress(adres);
        pst.close();
        return reiziger;
    }

    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> lijst = new ArrayList<>();
        String q = "Select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger" ;
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            int reiziger_id = resultSet.getInt(1);
            String voorletters = resultSet.getString(2);
            String tussenvoegel = resultSet.getString(3);
            String achternaam = resultSet.getString(4);
            java.sql.Date geboorteDatum = resultSet.getDate(5);
            Reiziger reiziger = new Reiziger(reiziger_id, voorletters,tussenvoegel, achternaam, geboorteDatum, null);
            Adres adres = adressDAOPsql.findByReiziger(reiziger);
            reiziger.setAdress(adres);
            lijst.add(reiziger);
        }
        pst.close();
        return lijst;
    }
}

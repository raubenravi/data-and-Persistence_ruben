package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.AdresDao;
import DP_OV_Chipkaart.Domain.Adres;
import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDao {

    public void setReizigerDAOPsql(ReizigerDAOPsql reizigerDAOPsql) {
        this.reizigerDAOPsql = reizigerDAOPsql;
    }

    ReizigerDAOPsql reizigerDAOPsql;
    static Connection connection;
    public AdresDAOPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection) throws SQLException {
         this.connection = connection.getConnection();
         this.reizigerDAOPsql = reizigerDAOPsql;
    }

    public boolean save(Adres adres) throws SQLException {
        String q = "INSERT INTO public.adres(adres_id , postcode, huisnummer , straat, woonplaats, reiziger_id) " +
                "VALUES(?, ?, ?, ? , ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adres.getId());
        pst.setString(2, adres.getPostcode());
        pst.setString(3, adres.getHuisnummer());
        pst.setString(4, adres.getStraat());
        pst.setString(5, adres.getWoonplaats());
        pst.setInt(6, adres.getReiziger().getId());
        pst.execute();
        pst.close();
        return true;
    }

    public boolean update(Adres adres) throws SQLException {
        try {
            String q = "UPDATE adres " +
                    "SET postcode = ? , huisnummer = ? , straat = ?, woonplaats = ? , reiziger_id = ?" +
                    "WHERE adres_id = ?;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, adres.getPostcode());
            pst.setString(2, adres.getHuisnummer());
            pst.setString(3, adres.getStraat());
            pst.setString(4, adres.getWoonplaats());
            pst.setInt(5, adres.getReiziger().getId());
            pst.setInt(6, adres.getId());
            pst.execute();
            pst.close();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(Adres adres) throws SQLException {
        try {
            String q = "DELETE FROM adres WHERE adres_id = ?;" ;
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, adres.getId());
            pst.execute();
            pst.close();
            return  true;
        } catch (Exception e){
            System.out.println(e);
            return  false;
        }
    }

    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            String q = "Select * FROM adres WHERE reiziger_id = ?;";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            //resultSet.next();
            //while(resultSet.next()){
            //  System.out.println(resultSet.getString(1));
            // }
            int adres_id = resultSet.getInt(1);
            String postcode = resultSet.getString(2);
            String huisnummer = resultSet.getString(3);
            String straat = resultSet.getString(4);
            String woonplaats = resultSet.getString(5);
           // int reiziger_id = resultSet.getInt(6);
            //}
            pst.close();
            return new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
        }catch (Exception e){
            return  null;
        }
    }


    public List<Adres> findAll() throws Exception {
        List<Adres> lijst = new ArrayList<>();
        String q = "Select * FROM adres" ;
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            int adress_id = resultSet.getInt(1);
            String postcode = resultSet.getString(2);
            String huisnummer = resultSet.getString(3);
            String straat = resultSet.getString(4);
            String woonplaats = resultSet.getString(5);
            int reiziger_id = resultSet.getInt(6);
            Reiziger reiziger = reizigerDAOPsql.findById(reiziger_id);
            lijst.add( new Adres(adress_id, postcode, huisnummer, straat, woonplaats, reiziger));
        }
        pst.close();
        return lijst;
        }
}

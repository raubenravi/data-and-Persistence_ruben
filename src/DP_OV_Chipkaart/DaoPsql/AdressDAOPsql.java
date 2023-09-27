package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.AdressDao;
import DP_OV_Chipkaart.Domain.Adress;
import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdressDAOPsql implements AdressDao {

    ReizigerDAOPsql reizigerDAOPsql;
    static Connection connection;
    public AdressDAOPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection) throws SQLException {
         this.connection = connection.getConnection();
         this.reizigerDAOPsql = reizigerDAOPsql;
    }

    public boolean save(Adress adress) throws SQLException {
        String q = "INSERT INTO public.adres(adres_id , postcode, huisnummer , straat, woonplaats, reiziger_id) " +
                "VALUES(?, ?, ?, ? , ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.getId());
        pst.setString(2, adress.getPostcode());
        pst.setString(3, adress.getHuisnummer());
        pst.setString(4, adress.getStraat());
        pst.setString(5, adress.getWoonplaats());
        pst.setInt(6, adress.getReiziger().getId());
        pst.execute();
        pst.close();
        return true;
    }

    public boolean update(Adress adress) throws SQLException {
        String q = "UPDATE reiziger" +
                "SET reiziger_id = ? , postcode = ?, huisnummer = ? , straat = ?, woonplaats = ?" +
                "WHERE reiziger_id = ?;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.getId());
        pst.setString(2, adress.getPostcode());
        pst.setString(3, adress.getHuisnummer());
        pst.setString(4, adress.getStraat());
        pst.setString(5, adress.getWoonplaats());
        pst.setInt(6, adress.getReiziger().getId());
        pst.execute();
        pst.close();
        return true;
    }

    public boolean delete(Adress adress) throws SQLException {
        try {
            String q = "DELETE FROM adres WHERE adres_id = ?;" ;
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,adress.getId());
            pst.execute();
            pst.close();
            return  true;
        } catch (Exception e){
            System.out.println(e);
            return  false;
        }
    }

    public Adress findByReiziger(Reiziger reiziger) throws SQLException {
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
            int reiziger_id = resultSet.getInt(6);
            //}
            pst.close();
            return new Adress(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
        }catch (Exception e){
            return  null;
        }
    }


    public List<Adress> findAll() throws Exception {
        List<Adress> lijst = new ArrayList<>();
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
            lijst.add( new Adress(adress_id, postcode, huisnummer, straat, woonplaats, reiziger));
        }
        pst.close();
        return lijst;
        }
}

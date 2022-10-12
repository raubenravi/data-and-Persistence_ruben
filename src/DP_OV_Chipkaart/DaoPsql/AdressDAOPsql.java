package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.AdressDao;
import DP_OV_Chipkaart.Domain.Adress;
import DP_OV_Chipkaart.Domain.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdressDAOPsql implements AdressDao {

    static Connection connection;
    public AdressDAOPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection) throws SQLException {
         this.connection = connection.getConnection();
    }

    public boolean save(Adress adress) throws SQLException {
        String q = "INSERT INTO public.adres(adres_id , postcode, huisnummer , straat, woonplaats, reiziger_id) " +
                "VALUES(?, ?, ?, ? , ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.adress_id);
        pst.setString(2, adress.postcode);
        pst.setString(3, adress.huisnummer);
        pst.setString(4, adress.straat);
        pst.setString(5, adress.woonplaats);
        pst.setInt(6, adress.reiziger.reiziger_id);
        pst.execute();
        return true;
    }

    public boolean update(Adress adress) throws SQLException {
        String q = "UPDATE reiziger" +
                "SET reiziger_id = ? , postcode = ?, huisnummer = ? , straat = ?, woonplaats = ?" +
                "WHERE reiziger_id = ?;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.adress_id);
        pst.setString(2, adress.postcode);
        pst.setString(3, adress.huisnummer);
        pst.setString(4, adress.straat);
        pst.setString(5, adress.woonplaats);
        pst.setInt(6, adress.reiziger.reiziger_id);
        pst.execute();
        return true;
    }

    public boolean delete(Adress adress) {
        return false;
    }

    public Adress findByReiziger(Reiziger reiziger) throws SQLException {
        String q = "Select * FROM adres WHERE reiziger_id = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, reiziger.reiziger_id);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        //resultSet.next();
        //while(resultSet.next()){
          //  System.out.println(resultSet.getString(1));
       // }
        int adres_id   = resultSet.getInt(1);
        String postcode   = resultSet.getString(2);
        String huisnummer   = resultSet.getString(3);
        String straat   = resultSet.getString(4);
        String woonplaats = resultSet.getString(5);
        //String reiziger_id = resultSet.getString(5);
        //}
        return new Adress(adres_id, postcode, huisnummer, straat, woonplaats, null);

    }


    public List<Adress> findAll() throws SQLException {
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

            lijst.add( new Adress(adress_id, postcode, huisnummer, straat, woonplaats, null));
        }
        return lijst;
        }
}

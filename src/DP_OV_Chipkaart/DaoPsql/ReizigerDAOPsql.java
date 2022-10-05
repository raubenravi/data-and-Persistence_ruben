package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Domain.Adress;
import DP_OV_Chipkaart.Dao.ReizigerDao;
import DP_OV_Chipkaart.Domain.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.Reiziger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDao {


    AdressDAOPsql adressDAOPsql;
    Connection connection;

    public ReizigerDAOPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection) throws SQLException {
        this.connection = connection.getConnection();
    }

    public boolean save(Reiziger reiziger) throws SQLException {
        String q = "INSERT INTO public.reiziger(reiziger_id , voorletters, tussenvoegsel , achternaam, geboortedatum) " +
                "VALUES(?, ?, ? , ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, reiziger.reiziger_id);
        pst.setString(2, reiziger.voorletters);
        pst.setString(3, reiziger.tussenvoegel);
        pst.setString(4, reiziger.achternaam);
        pst.setDate(5, reiziger.geboorteDatum);
        pst.execute();
        return true;
    }

    public boolean update(Reiziger reiziger) throws SQLException {
        String q = "UPDATE reiziger" +
                "SET reiziger_id = ? , voorletters = ?, tussenvoegsel = ? , achternaam = ?, geboortedatum = ?" +
                "WHERE reiziger_id = ?;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, reiziger.reiziger_id);
        pst.setString(2, reiziger.voorletters);
        pst.setString(3, reiziger.tussenvoegel);
        pst.setString(4, reiziger.achternaam);
        pst.setDate(5, reiziger.geboorteDatum);
        pst.setInt(6, reiziger.reiziger_id);
        pst.execute();
        return true;
    }

    public boolean delete(Reiziger reiziger) {
        return false;
    }

    public Reiziger findById(int id) throws Exception {
        String q = "Select * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
         resultSet.next();
             int reiziger_id = resultSet.getInt(1);
             String voorletters = resultSet.getString(2);
             String tussenvoegel = resultSet.getString(3);
             String achternaam = resultSet.getString(4);
             java.sql.Date geboorteDatum = resultSet.getDate(5);
             if (reiziger_id != resultSet.getInt(6)) {
                 throw new Exception("Search id en id in result from data base are not the same");
         }
            Reiziger reiziger = new Reiziger(reiziger_id, voorletters,tussenvoegel, achternaam, geboorteDatum, null);
            Adress adress = adressDAOPsql.findByReiziger(reiziger);
            reiziger.adress = adress;
        return reiziger;
    }

    public Reiziger findByGbdatum(int id) {
        return null;
    }

    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> lijst = new ArrayList<>();
        String q = "Select * FROM reiziger" ;
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString(4));
        }
        return lijst;
    }
}

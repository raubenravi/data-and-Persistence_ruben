import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDao {

    Connection connection;
    public ReizigerDAOPsql() throws SQLException {
         this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "borpe");
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

    public Reiziger findById(int id) {
        return null;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdressDAOPsql implements AdressDao {

    Connection connection;
    public AdressDAOPsql() throws SQLException {
         this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "borpe");
    }

    public boolean save(Adress adress) throws SQLException {
        String q = "INSERT INTO public.reiziger(reiziger_id , postcode, huisnummer , straat, woonplaats) " +
                "VALUES(?, ?, ? , ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.reiziger_id);
        pst.setString(2, adress.postcode);
        pst.setString(3, adress.huisnummer);
        pst.setString(4, adress.straat);
        pst.setString(5, adress.woonplaats);
        pst.execute();
        return true;
    }

    public boolean update(Adress adress) throws SQLException {
        String q = "UPDATE reiziger" +
                "SET reiziger_id = ? , postcode = ?, huisnummer = ? , straat = ?, woonplaats = ?" +
                "WHERE reiziger_id = ?;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, adress.reiziger_id);
        pst.setString(2, adress.postcode);
        pst.setString(3, adress.huisnummer);
        pst.setString(4, adress.straat);
        pst.setString(5, adress.woonplaats);
        pst.setInt(6, adress.reiziger_id);
        pst.execute();
        return true;
    }

    public boolean delete(Adress adress) {
        return false;
    }

    public Reiziger findById(int id) {
        return null;
    }

    public Reiziger findByGbdatum(int id) {
        return null;
    }

    public List<Adress> findAll(){
        String q = "Select * FROM reiziger" ;
            List<Adress> lijst = new ArrayList<>();
            return lijst;
        }
}

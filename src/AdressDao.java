import java.sql.SQLException;
import java.util.List;

public interface AdressDao {
    boolean save(Adress Adress) throws SQLException;
    boolean update(Adress Adress) throws SQLException;
    boolean delete(Adress Adress);
    Reiziger findById(int id);
    Reiziger findByGbdatum(int id);
    List<Adress> findAll();
}

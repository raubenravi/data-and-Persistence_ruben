import java.sql.SQLException;
import java.util.List;

public interface  ReizigerDao {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    Reiziger findByGbdatum(int id);
    List<Reiziger> findAll() throws SQLException;
}

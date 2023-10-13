package nl.hu.dp.ovchip.Dao;

import nl.hu.dp.ovchip.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface  ReizigerDao {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id) throws Exception;
    Reiziger findByGbdatum(java.sql.Date gbDatum) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
}

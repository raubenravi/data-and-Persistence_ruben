package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface  ReizigerDao {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id) throws Exception;
    Reiziger findByGbdatum(int id);
    List<Reiziger> findAll() throws SQLException;
}

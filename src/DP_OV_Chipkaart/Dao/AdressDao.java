package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.Adress;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdressDao {
    boolean save(Adress Adress) throws SQLException;
    boolean update(Adress Adress) throws SQLException;
    boolean delete(Adress Adress);
    Adress findByReiziger(Reiziger reiziger) throws SQLException;
    Reiziger findByGbdatum(int id);
    List<Adress> findAll();
}

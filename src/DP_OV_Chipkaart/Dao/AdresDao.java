package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.Adres;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDao {
    boolean save(Adres Adres) throws SQLException;
    boolean update(Adres Adres) throws SQLException;
    boolean delete(Adres Adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
    List<Adres> findAll() throws Exception;
}

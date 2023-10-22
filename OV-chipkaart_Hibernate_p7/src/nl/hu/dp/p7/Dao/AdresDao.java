package nl.hu.dp.ovchip.Dao;

import nl.hu.dp.ovchip.Domain.Adres;
import nl.hu.dp.ovchip.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDao {
    boolean save(Adres Adres) throws SQLException;
    boolean update(Adres Adres) throws SQLException;
    boolean delete(Adres Adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
    List<Adres> findAll() throws Exception;
}

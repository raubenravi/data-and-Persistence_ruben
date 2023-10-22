package nl.hu.dp.ovchip.Dao;

import nl.hu.dp.ovchip.Domain.OvChipKaart;
import nl.hu.dp.ovchip.Domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOVchipkaart(OvChipKaart ovChipkaart) throws Exception;
    List<Product> findAll() throws SQLException;
}

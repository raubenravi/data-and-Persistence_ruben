package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.OvChipKaart;
import DP_OV_Chipkaart.Domain.Product;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOVchipkaart(OvChipKaart ovChipkaart) throws SQLException;

    List<Product> findAll() throws SQLException;
}

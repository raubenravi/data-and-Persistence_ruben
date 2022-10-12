package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.Product;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;

public interface ProductDao {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
}

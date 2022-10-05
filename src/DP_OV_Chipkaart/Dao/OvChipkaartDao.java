package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.OvChipKaart;

import java.sql.SQLException;
import java.util.List;

public interface  OvChipkaartDao {
    boolean save(OvChipKaart ovKaart) throws SQLException;
    boolean update(OvChipKaart ovKaart) throws SQLException;
    boolean delete(OvChipKaart ovKaart);
    OvChipKaart findById(int id) throws Exception;
    OvChipKaart findByGbdatum(int id);
    List<OvChipKaart> findAll() throws SQLException;
}

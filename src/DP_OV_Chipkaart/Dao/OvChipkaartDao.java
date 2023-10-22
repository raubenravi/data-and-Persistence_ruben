package DP_OV_Chipkaart.Dao;

import DP_OV_Chipkaart.Domain.OvChipKaart;
import DP_OV_Chipkaart.Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface  OvChipkaartDao {
    boolean save(OvChipKaart ovKaart) throws Exception;
    boolean update(OvChipKaart ovKaart) throws SQLException;
    boolean delete(OvChipKaart ovKaart) throws SQLException;
    OvChipKaart findByNR(int id) throws Exception;
    List<OvChipKaart> findByReiziger(Reiziger reiziger) throws Exception;
    List<OvChipKaart> findAll() throws Exception;
}

package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Domain.OvChipKaart;

import java.sql.SQLException;
import java.util.List;

public class OvChipKaartDaoPsql implements OvChipkaartDao {
    public boolean save(OvChipKaart ovKaart) throws SQLException {
        return true;
    }


    public boolean update(OvChipKaart ovKaart) throws SQLException {
        return false;
    }

    public boolean delete(OvChipKaart ovKaart) {
        return false;
    }

    public OvChipKaart findById(int id) throws Exception {

        return null;
    }


    public OvChipKaart findByGbdatum(int id) {
        return null;
    }

    public List<OvChipKaart> findAll() throws SQLException {
        return null;
    }
}

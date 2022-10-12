package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.OvChipkaartDao;
import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Domain.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.OvChipKaart;
import DP_OV_Chipkaart.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OvChipKaartDaoPsql implements OvChipkaartDao {

    private Connection connection;

    ProductDao productDao;
    public OvChipKaartDaoPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection){
        this.connection = connection.getConnection();
    }

    public boolean save(OvChipKaart ovKaart) throws SQLException {
        String q = "INSERT INTO public.ov_chipkaart(kaart_nummer , getlig_tot, klasse , saldo, reiziger_id " +
                "VALUES(?, ?, ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, ovKaart.kaart_nummer);
        pst.setDate(1, ovKaart.getlig_tot);
        pst.setInt(2, ovKaart.klasse);
        pst.setInt(3, ovKaart.saldo);
        pst.setInt(4, ovKaart.reiziger.reiziger_id);
        pst.execute();

        q = "INSERT INTO public.ov_chipkaart_product(kaart_nummer , getlig_tot, klasse , saldo, reiziger_id " +
                "VALUES(?, ?, ?, ?) ;" ;
        for(Product product : ovKaart.products){
            pst = connection.prepareStatement(q);
            pst.setInt(1, ovKaart.kaart_nummer);
            pst.setInt(2, product.productNummer);
            pst.execute();

            productDao.save(product);
        }
        //ov_chip_product

        return true;
    }


    public boolean update(OvChipKaart ovKaart) throws SQLException {
        return true;
    }

    public boolean delete(OvChipKaart ovKaart) {
        return false;
    }

    public OvChipKaart findById(int id) throws Exception {

        return null;
    }



    public List<OvChipKaart> findAll() throws SQLException {
        return null;
    }
}

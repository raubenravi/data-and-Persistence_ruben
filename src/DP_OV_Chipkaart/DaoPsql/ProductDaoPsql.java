package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Domain.ConnectionDatabase;
import DP_OV_Chipkaart.Domain.OvChipKaart;
import DP_OV_Chipkaart.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoPsql implements ProductDao {

    private Connection connection;

    public ProductDaoPsql(ConnectionDatabase.ConnectionDatabaseIsntance connection){
        this.connection = connection.getConnection();
    }
    public boolean save(Product product) throws SQLException {
        String q = "INSERT INTO public.product(product_nummer , naam, beschrijving , prijs " +
                "VALUES(?, ?, ?, ?) ;" ;
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, product.productNummer);
        pst.setString(2, product.naanm);
        pst.setString(3, product.Beschrijving);
        pst.setInt(4, product.prijs);
        pst.execute();
        return true;
    }
    public boolean update(Product product) throws SQLException {
        String q = "UPDATE product " +
                "SET  naam = ?, beschrijving = ? , prijs = ?" +
                "WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setString(1, product.naanm);
        pst.setString(2, product.Beschrijving);
        pst.setInt(3, product.prijs);
        pst.setInt(4, product.productNummer);
        pst.execute();
        return true;
    }
    public boolean delete(Product product) throws SQLException {
        String q = "DELETE FROM product WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1,product.productNummer);
        pst.execute();
        return true;
    }
}

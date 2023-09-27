package DP_OV_Chipkaart.DaoPsql;

import DP_OV_Chipkaart.Connections.ConnectionDatabase;
import DP_OV_Chipkaart.Dao.ProductDao;
import DP_OV_Chipkaart.Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        pst.setInt(1, product.getProductNummer());
        pst.setString(2, product.getNaanm());
        pst.setString(3, product.getBeschrijving());
        pst.setInt(4, product.getPrijs());
        pst.execute();
        pst.close();
        return true;
    }
    public boolean update(Product product) throws SQLException {
        String q = "UPDATE product " +
                "SET  naam = ?, beschrijving = ? , prijs = ?" +
                "WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setString(1, product.getNaanm());
        pst.setString(2, product.getBeschrijving());
        pst.setInt(3, product.getPrijs());
        pst.setInt(4, product.getProductNummer());
        pst.execute();
        pst.close();
        return true;
    }
    public boolean delete(Product product) throws SQLException {
        String q = "DELETE FROM product WHERE product_nummer = ?;";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1,product.getProductNummer());
        pst.execute();
        pst.close();
        return true;
    }

    public List<Product> findByOVchipkaart(OvChipKaart ovChipkaart) throws SQLException{
        return  null;
    }

    public List<Product> findAll() throws SQLException{
        List<Product> lijst = new ArrayList<>();
        String q = "Select * FROM reiziger" ;
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            int productNummer = resultSet.getInt(4);
            String naam = resultSet.getString(1);
            String beschrijving = resultSet.getString(2);
            int prijs = resultSet.getInt(3);

            Product product = new Product(productNummer, naam, beschrijving,prijs);
            lijst.add(product);
        }
        pst.close();
        return lijst;
    }
}

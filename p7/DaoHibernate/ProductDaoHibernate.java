package nl.hu.dp.ovchip.DaoHibernate;

import nl.hu.dp.ovchip.Dao.ProductDao;
import nl.hu.dp.ovchip.Domain.Adres;
import nl.hu.dp.ovchip.Domain.OvChipKaart;
import nl.hu.dp.ovchip.Domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoHibernate implements ProductDao {
    private Session session;
    private Transaction tx;

    public ProductDaoHibernate(Session session){
        this.session = session;
    }

    public boolean save(Product product) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean update(Product product) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(Product product) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.delete(product);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    public List<Product> findByOVchipkaart(OvChipKaart ovChipkaart) throws Exception {
        try {
            String q = "select product from OvChipKaart product join product.OvChipKaart OvChipKaart WHERE OvChipKaart.kaart_nummer = " + ovChipkaart.getId();;
            return session.createQuery(q, Product.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public List<Product> findAll() throws SQLException {
        try {
            return session.createQuery("from product", Product.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}

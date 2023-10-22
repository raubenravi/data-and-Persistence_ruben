package nl.hu.dp.ovchip.DaoHibernate;
import java.sql.SQLException;
import java.util.List;

import nl.hu.dp.ovchip.Dao.ReizigerDao;
import nl.hu.dp.ovchip.Domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ReizigerDaoHibernate implements ReizigerDao {
    private Session session;
    private Transaction tx;

    public ReizigerDaoHibernate(Session session){
        this.session =session;
    }

    public boolean save(Reiziger reiziger) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(reiziger);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean update(Reiziger reiziger) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(reiziger);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(Reiziger reiziger) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public Reiziger findById(int id) throws Exception{

        try {
            Reiziger reiziger = (Reiziger) session.get(Reiziger.class, id);
            return reiziger;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public Reiziger findByGbdatum(java.sql.Date gbDatum) throws SQLException{
        try {
            Reiziger reiziger = (Reiziger) session.get(Reiziger.class, gbDatum);
            return reiziger;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public List<Reiziger> findAll() throws SQLException{
        try {
            return session.createQuery("from reiziger", Reiziger.class).getResultList();
        }catch (Exception e){
            return null;
        }
    }
}

package nl.hu.dp.ovchip.DaoHibernate;

import nl.hu.dp.ovchip.Dao.AdresDao;
import nl.hu.dp.ovchip.Dao.ReizigerDao;
import nl.hu.dp.ovchip.Domain.Adres;
import nl.hu.dp.ovchip.Domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;


public class AdresDaoHibernate implements AdresDao {
    private Session session;
    private Transaction tx;

    public AdresDaoHibernate(Session session){
        this.session =session;
    }

    public boolean save(Adres adres) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(adres);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean update(Adres adres) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(adres);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(Adres adres) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.delete(adres);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            Adres adres = (Adres) session.get(Adres.class, reiziger.getId());
            return adres;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Adres> findAll() throws Exception {
        try {
            return session.createQuery("from adres", Adres.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
        }
            return null;
        }

}

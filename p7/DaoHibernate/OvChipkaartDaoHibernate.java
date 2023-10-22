package nl.hu.dp.ovchip.DaoHibernate;

import nl.hu.dp.ovchip.Dao.OvChipkaartDao;
import nl.hu.dp.ovchip.Domain.Adres;
import nl.hu.dp.ovchip.Domain.OvChipKaart;
import nl.hu.dp.ovchip.Domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class OvChipkaartDaoHibernate implements OvChipkaartDao {
    private Session session;
    private Transaction tx;
    public OvChipkaartDaoHibernate(Session session){
        this.session = session;
    }
    public boolean save(OvChipKaart reiziger) throws SQLException{
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
    public boolean update(OvChipKaart ovChipKaart) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.save(ovChipKaart);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(OvChipKaart ovChipKaart) throws SQLException{
        try {
            tx = session.beginTransaction();
            session.delete(ovChipKaart);
            tx.commit();
            return true;
        }catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    public OvChipKaart findByNR(int id) throws Exception {
        try {
            OvChipKaart ovChipKaart = (OvChipKaart) session.get(OvChipKaart.class, id);
            return ovChipKaart;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    public List<OvChipKaart> findByReiziger(Reiziger reiziger) throws Exception {
        try {
            String q = "FROM OvChipKaart reiziger WHERE reiziger_id = "+ reiziger.getId();
            return session.createQuery(q, OvChipKaart.class).getResultList();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public List<OvChipKaart> findAll() throws Exception {
        try {
            return session.createQuery("from ov_chipkaart", OvChipKaart.class).getResultList();
        }catch (Exception e){
            return null;
        }
    }
}

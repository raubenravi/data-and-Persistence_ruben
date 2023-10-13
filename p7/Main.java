package nl.hu.dp.ovchip;


import nl.hu.dp.ovchip.Dao.AdresDao;
import nl.hu.dp.ovchip.Dao.OvChipkaartDao;
import nl.hu.dp.ovchip.Dao.ProductDao;
import nl.hu.dp.ovchip.Dao.ReizigerDao;
import nl.hu.dp.ovchip.DaoHibernate.AdresDaoHibernate;
import nl.hu.dp.ovchip.DaoHibernate.OvChipkaartDaoHibernate;
import nl.hu.dp.ovchip.DaoHibernate.ProductDaoHibernate;
import nl.hu.dp.ovchip.DaoHibernate.ReizigerDaoHibernate;
import nl.hu.dp.ovchip.Domain.Reiziger;
import nl.hu.dp.ovchip.Domain.OvChipKaart;
import nl.hu.dp.ovchip.Domain.Product;
import nl.hu.dp.ovchip.Domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;


    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws Exception {
        Session session = getSession();
        ReizigerDao rdao = new ReizigerDaoHibernate(session);
        AdresDao adao = new AdresDaoHibernate(session);
        OvChipkaartDao oVChipdao = new OvChipkaartDaoHibernate(session);
        ProductDao pdao = new ProductDaoHibernate(session);
        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println("reiziger 2: ");
        Reiziger reiziger = rdao.findById(2);
        System.out.println(rdao.findById(2));
        System.out.println(adao.findByReiziger(reiziger) );
        System.out.println(oVChipdao.findByReiziger(reiziger));
        OvChipKaart ovkaart = oVChipdao.findByNR(35283);
        System.out.println(pdao.findByOVchipkaart(ovkaart));

        //testFetchAll();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    if (o != null) {
                        System.out.println("  " + o);
                    }
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }
}

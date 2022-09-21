import java.sql.*;

public class Main {



    public static void main(String [] args) throws SQLException {

        String gbdatum = "1981-03-14";
        Adress adress = new Adress(78, "1122BB", "22a", "KorteStraat", "London");
        Reiziger sietske = new Reiziger(78, "S", "a", "Boers", java.sql.Date.valueOf(gbdatum), adress);

        System.out.println(sietske.toString());
        ReizigerDAOPsql dao = new ReizigerDAOPsql();
        //dao.save(sietske);
        dao.findAll();
    }


}


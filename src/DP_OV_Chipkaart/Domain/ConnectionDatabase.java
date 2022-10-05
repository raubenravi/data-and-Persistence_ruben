package DP_OV_Chipkaart.Domain;

import DP_OV_Chipkaart.DaoPsql.AdressDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    public static class ConnectionDatabaseIsntance{
        private Connection connection;

        public ConnectionDatabaseIsntance() throws SQLException {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ov_Database", "postgres", "borpe");
        }

        public Connection getConnection() {
            return this.connection;
        }
    }
 }

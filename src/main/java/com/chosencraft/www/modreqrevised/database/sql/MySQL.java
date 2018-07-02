package com.chosencraft.www.modreqrevised.database.sql;

import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.utils.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database
{

    private Logger log = ModReqRevisedMain.logger;

    private String username;
    private String password;

    private String connectionURL;
    private Connection connection;

    /**
     * MySQL Wrapper
     * @param username DB Username
     * @param password DB password
     * @param database DB name
     * @param host DB host address
     * @param port DB port
     */
    public MySQL(String username, String password, String database, String host, int port) {

        connectionURL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
        this.username = username;
        this.password = password;
    }


    /**
     * Attempts to creates a connection to a MySQL Server
     *
     * @return true if connected or false if it was unable to establish a
     *         connection
     */
    @Override
    public boolean connect() {

        try {
            // Presume nothing is ever done right and grab the driver class ourselves
            Class.forName("com.mysql.jdbc.Driver");
            // Creates the connection, then returns the instance of it
            this.connection = DriverManager.getConnection(connectionURL, username, password);
            return isConnected();

        }
        catch (ClassNotFoundException noClassException) {
            // No SQL driver is found, abort the mission
            log.logError("JDBC Driver not found! " + noClassException.getCause());
            noClassException.printStackTrace();
        }
        catch (SQLException sqlException) {
            // Couldn't connect so the server, time to debug
            log.logError("Could not connect to MySQL Database! " + sqlException.getErrorCode());
            sqlException.printStackTrace();
        }
        // Find why it could not connection
        return false;
    }


    /**
     * Attempts to retrieve an instance of the connection
     *
     * @return an instance of the connection, returns false if none can be found
     */
    @Override
    public Connection getConnection() {
        // Simple getter
        return connection;
    }

}

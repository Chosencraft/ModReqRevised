package com.chosencraft.www.modreqrevised.database.sql;

import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database abstraction
 */
public abstract class Database
{

    private Logger log = ModReqRevisedMain.logger;

    /**
     * Attempts to creates a connection to a MySQL Server
     *
     * @return true if connected or false if it was unable to establish a
     *         connection
     */
    public abstract boolean connect();

    /**
     * Attempts to retrieve an instance of the connection
     *
     * @return an instance of the connection
     */
    public abstract Connection getConnection();

    /**
     * Checks to see if a connection is established
     *
     * @return True if a connection is established
     */
    protected boolean isConnected() {
        // get the connection
        Connection connection = getConnection();

        try {
            // attempt to connect, has 10 second til timeout
            return !(connection == null || !connection.isValid(100));
        } catch (SQLException sqlException) {
            log.logError("Error connecting to SQL database! " + sqlException.getErrorCode() );
            sqlException.printStackTrace();
        }
        // Presumably connection failed, so return false
        return false;
    }

    /**
     * Creates a statement to interact with the Database
     *
     * @return An instance of the statement created
     */
    public Statement createStatement() {
        // check if a connection is already established
        if (isConnected()) {
            // get connection
            Connection connection = getConnection();
            try {
                // make the statement
                return connection.createStatement();
            } catch (SQLException sqlException) {
                log.logError("Error creating SQL Statement! " + sqlException.getErrorCode() );
                sqlException.printStackTrace();

            }
        }
        // statement failed, return null
        return null;
    }

    /**
     * Prepares statement to be pushed Used to efficiently execute statement
     * many times
     *
     * @param sql
     *            - an SQL statement that may contain one or more '?' IN
     *            parameter placeholders
     * @return a new default PreparedStatement object containing the
     *         pre-compiled SQL statement or null if something goes wrong
     */
    public PreparedStatement createPreparedStatement(String sql) {
        // check if connected
        if (isConnected()) {
            // retrieve connection
            Connection connection = getConnection();
            try {
                // prep the statement and return it
                return connection.prepareStatement(sql);
            } catch (SQLException sqlException) {
                log.logError("Error creating Prepared SQL Statement! " + sqlException.getErrorCode() );
                sqlException.printStackTrace();
            }
        }
        // something went wrong, so returning null
        return null;
    }

}


package com.project.repository;

import com.project.dto.Client;
import com.project.dto.dao.DAOConnection;
import com.project.dto.dao.Repository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The Client repository implementation.
 */
public class ClientRepository implements Repository<Client>
{
    final static Logger log = Logger.getLogger(ClientRepository.class);


    @Override
    public Client find(String id)
    {
        log.debug("Start method...");

        Client obj = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM client WHERE id=?");

            prepared.setString(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first())
            {
                obj = map(result);
            }

        } catch (SQLException e)
        {
            log.error("Error finding client : " + e);
        }

        log.debug("End method.");

        return obj;

    }

    @Override
    public ArrayList<Client> findAll()
    {
        log.debug("Start method...");

        ArrayList<Client> clients = new ArrayList<>();

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM client");

            ResultSet result = prepared.executeQuery();

            while (result.next())
            {
                clients.add(map(result));
            }

        } catch (SQLException e)
        {
            log.error("Error finding clients : " + e);
        }

        log.debug("End method.");

        return clients;
    }

    /**
     * Create new Object and return this new Object if success. Run only on
     * tables with auto_increment id column.
     */
    @Override
    public Client create(Client obj)
    {
        log.debug("Start method...");

        Client objectToReturn = null;
        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO client (Name, Address, Reference_person, Email) "
                    + " VALUES(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getName());
            prepared.setString(2, String.valueOf(obj.getAddress()));
            prepared.setString(3, obj.getReferencePerson());
            prepared.setString(4, obj.getEmail());


        } catch (SQLException e)
        {
            log.error("Error creating new client : " + e);
        }

        log.debug("End method.");

        return obj;
    }
    //

    @Override
    public Client update(Client obj)
    {
        log.debug("Start method...");

        obj = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE client "
                    + " SET Name=?, "
                    + " Address=?, "
                    + " Reference_person=?, "
                    + "Email=?"
                    + " WHERE ID=? ");

            prepared.setString(1, obj.getName());
            prepared.setString(2, String.valueOf(obj.getAddress()));
            prepared.setString(3, obj.getReferencePerson());
            prepared.setString(4, obj.getEmail());
            prepared.setString(5, obj.getClientID());


        } catch (SQLException e)
        {
            log.error("Error updating client : " + e);
        }

        log.debug("End method.");

        return obj;
    }

    /**
     * Delete a single record.
     */
    @Override
    public int delete(String id)
    {
        log.debug("Start method...");

        int affectedRows = 0;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM client "
                    + " WHERE id=? ");

            prepared.setString(1, id);

            // execute query and get the affected rows number :
            affectedRows = prepared.executeUpdate();

        } catch (SQLException e)
        {
            log.error("Error deleting client : " + e);
        }

        log.debug("End method.");

        return affectedRows;
    }

    /**
     * Map the current row of the given ResultSet to an Object.
     *
     * @param resultSet
     * @return The mapped Object from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Client map(ResultSet resultSet) throws SQLException
    {
        Client obj = new Client();

        obj.setClientID(resultSet.getString("ID"));
        obj.setName(resultSet.getString("Name"));
        obj.setAddress(resultSet.getString("Address"));
        obj.setReferencePerson(resultSet.getString("Person"));
        obj.setEmail(resultSet.getString("Email"));

        return obj;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.lsmb.jchat.connection;

import de.dhbw.lsmb.jchat.json.models.ChatProtocol;
import de.dhbw.lsmb.jchat.server.ServerConnection;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class ConnectionManager
{
    private final ArrayList<Connection> connections;
    private static ConnectionManager instance;
    
    private ConnectionManager() {
        this.connections = new ArrayList<>();
    }
    
    public static ConnectionManager getInstance() {
        if(instance == null) {
            instance = new ConnectionManager();
        }
        
        return instance;
    }
    
    public ServerConnection addConnection(Socket socket) {
        try
        {
            ServerConnection con = new ServerConnection(socket);
            connections.add(con);
            return con;
        }
        catch(IOException ex)
        {
            System.out.println("Connection not created");
            return null;
        }
    }
    
    public void closeAllConnections() {
        for(Connection connection : connections)
        {
            connection.close();
        }
    }
    
    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }
    
    public void write(ChatProtocol protocol, Connection connection) {
        for(Connection con : connections)
        {
            if(! con.equals(connection)) {
                con.write(protocol);
            }
        }
    }
}

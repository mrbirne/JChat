/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.lsmb.jchat.client;

import de.dhbw.lsmb.jchat.connection.Connection;
import de.dhbw.lsmb.jchat.json.models.Action;
import de.dhbw.lsmb.jchat.json.models.ChatProtocol;
import de.dhbw.lsmb.jchat.json.models.JsonMessage;
import de.dhbw.lsmb.jchat.json.models.JsonStatus;
import de.dhbw.lsmb.jchat.server.ServerConnection;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class ClientConnection extends Connection
{
    private String verification = null;
    private final LoginListener loginListener;
    private final MessageListener messageListener;
    
    public ClientConnection(Socket socket, LoginListener loginListener, MessageListener messageListener) throws IOException
    {
        super(socket);
        this.loginListener = loginListener;
        this.messageListener = messageListener;
    }
    
    public String getVerification() {
        return verification;
    }

    @Override
    protected void doAction(ChatProtocol protocol)
    {
        if(protocol.getAction().equals(Action.STATUS.toString())) {
            showStatus(protocol.getStatus());
        } else if(protocol.getAction().equals(Action.MESSAGE.toString())) {
            showMessage(protocol.getMessage());
        } else if(protocol.getAction().equals(Action.HISTORY_SAVE.toString())) {
            showHistory(protocol.getMessages());
        }
        if(verification == null && protocol.getVerification() != null && !protocol.getVerification().equals(ServerConnection.SERVER_VERIFIC)) {
            verification = protocol.getVerification();
            loginListener.logedin(this);
        }
    }
    
    private void showStatus(JsonStatus status)
    {
        if(status != null)
        {
            System.out.println(status.toString());
        }
    }
    
    private void showMessage(JsonMessage message) {
        if(message != null) {
            System.out.println(message.toString());
            messageListener.message(message);
        }
    }
    
    private void showHistory(ArrayList<JsonMessage> messages) {
        if(! messages.isEmpty()) {
            for(JsonMessage message : messages)
            {
                messageListener.message(message);
            }
        }
    }
    
    public void requestHistory() {
        ChatProtocol prot = new ChatProtocol(Action.HISTORY_SEND);
        
        this.write(prot);
    }
    
    public interface LoginListener {
        public void logedin(ClientConnection con);
    }
    
    public interface MessageListener {
        public void message(JsonMessage message);
    }
    
}

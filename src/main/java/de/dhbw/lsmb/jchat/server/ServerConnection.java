/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.lsmb.jchat.server;

import de.dhbw.lsmb.jchat.connection.Connection;
import de.dhbw.lsmb.jchat.json.models.Action;
import de.dhbw.lsmb.jchat.json.models.ChatProtocol;
import de.dhbw.lsmb.jchat.server.actions.RegisterAction;
import de.dhbw.lsmb.jchat.server.actions.ServerAction;
import de.dhbw.lsmb.jchat.server.actions.LoginAction;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class ServerConnection extends Connection
{
    private String verification;
    
    public ServerConnection(Socket socket) throws IOException
    {
        super(socket);
    }

    @Override
    protected void doAction(ChatProtocol protocol)
    {
        ServerAction action = getAction(protocol);
        if(action == null)
        {
            System.out.println("Action not converted.");
            return;
        }
        
        ChatProtocol actionReturn = action.doAction(protocol);
        write(actionReturn);
        if(verification == null && actionReturn.getVerification() != null) {
            this.verification = actionReturn.getVerification();
        }
    }
    
    private ServerAction getAction(ChatProtocol protocol) {
        if(protocol.getAction().equals(Action.REGISTER.toString())) {
            return new RegisterAction();
        } else if(protocol.getAction().equals(Action.LOGIN.toString())) {
            return new LoginAction();
        } else if(protocol.getAction().equals(Action.LOGOUT.toString()) && hasPermission(protocol)) {
            close();
        }
        
        return null;
    }
    
    private boolean hasPermission(ChatProtocol protocol) {
        if(verification == null) {
            return false;
        }
        return verification.equals(protocol.getVerification());
    }
    
}

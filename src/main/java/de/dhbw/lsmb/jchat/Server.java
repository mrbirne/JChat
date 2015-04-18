/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.lsmb.jchat;

import com.google.gson.Gson;
import de.dhbw.lsmb.jchat.json.models.Message;
import de.dhbw.lsmb.jchat.server.ConnectionManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Server
{
    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("Server start.");
        ServerSocket socket = new ServerSocket(1234);
        Socket skt = socket.accept();
        System.out.println("Connected!");

        ConnectionManager.getInstance().addConnection(skt);

        Message msg = new Message("Sender", "Message", new Date());
        Gson gson = new Gson();
        String data = gson.toJson(msg);

        ConnectionManager.getInstance().write(data);

        Thread.sleep(2000);

        ConnectionManager.getInstance().closeAllConnections();
   }
}

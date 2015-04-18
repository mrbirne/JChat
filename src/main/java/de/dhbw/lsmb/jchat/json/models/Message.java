/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.lsmb.jchat.json.models;

import java.util.Date;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Message
{
    private String sender;
    private String message;
    private Date date;
    
    public Message(String sender, String message, Date date)
    {
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    /**
     * @return the sender
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender)
    {
        this.sender = sender;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "Message from "+ sender +" : "+ message ;
    }
}

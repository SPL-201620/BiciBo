package controllers;

import play.*;
import play.libs.Mail;
import play.mvc.*;
import java.util.*;
import org.apache.commons.mail.*;

public class Mails extends Mailer {

    public static void message(String address, String message) {
        try
        {
            SimpleEmail email = new SimpleEmail();
            email.setFrom(address);
            email.addTo("juniorfigti5@gmail.com");
            email.setSubject("Message Bicibo");
            email.setMsg(message);
            Mail.send(email);
            System.out.println("Send message was successful");
        }
        catch (Exception e)
        {
            System.out.println("Error...");
            System.out.println(e);
        }
    }
}
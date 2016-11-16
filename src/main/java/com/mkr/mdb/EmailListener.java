package com.mkr.mdb;

import com.mkr.dao.ClientsDAO;
import com.mkr.entity.Client;
import com.mkr.entity.Post;
import org.apache.logging.log4j.LogManager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(name = "emailHandler",
        activationConfig = {
        @ActivationConfigProperty( propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty( propertyName = "destination",
                propertyValue ="/topic/news")
})

public class EmailListener implements MessageListener {

    @EJB
    ClientsDAO clientsDAO;

    public void onMessage(Message message) {
        try {
            Post p = (Post)((ObjectMessage)message).getObject();

            if ((p.getTarget() & Post.TARGET_EMAIL) > 0) {
                for (Client c : clientsDAO.getAll()) {
                    LogManager.getLogger("emails").info(String.format("Sending email with theme \"%s\" to %s", p.getTitle(), c.getEmail()));
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

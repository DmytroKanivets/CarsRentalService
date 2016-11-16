package com.mkr.mdb;

import com.mkr.dao.PostsDAO;
import com.mkr.entity.Post;
import org.apache.logging.log4j.LogManager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(name = "siteHandler",
        activationConfig = {
        @ActivationConfigProperty( propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty( propertyName = "destination",
                propertyValue ="/topic/news")
})

public class SiteListener implements MessageListener {

    @EJB
    PostsDAO postsDAO;

    public void onMessage(Message message) {

        try {
            Post p = (Post)((ObjectMessage)message).getObject();

            if ((p.getTarget() & Post.TARGET_BLOG) > 0)
                postsDAO.add(p);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

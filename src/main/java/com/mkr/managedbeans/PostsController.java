package com.mkr.managedbeans;

import com.mkr.dao.PostsDAO;
import com.mkr.entity.Post;
import org.apache.logging.log4j.LogManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import java.util.List;

@ManagedBean(name="posts")
@RequestScoped
public class PostsController {

    @EJB
    PostsDAO postsDAO;

    TopicConnection connection;
    TopicPublisher publisher;
    TopicSession session;

    @PostConstruct
    private void init() {
        try {
            InitialContext ctx = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");;
            connection = factory.createTopicConnection();
            session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic) ctx.lookup("/topic/news");
            publisher = session.createPublisher(topic);
        } catch (JMSException | NamingException e) {
            throw new EJBException(e);
        }
    }

    @PreDestroy
    private void free() {
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String postTitle;
    private String postContent;
    private boolean sendToEmail;
    private boolean postInBlog;

    public void newPost() {
        Post p = new Post();
        p.setTitle(postTitle);
        p.setContent(postContent);

        if (isPostInBlog())
            p.setTarget(p.getTarget() | Post.TARGET_BLOG);
        if (isSendToEmail())
            p.setTarget(p.getTarget() | Post.TARGET_EMAIL);

        try {
            publisher.send(session.createObjectMessage(p));
        } catch (JMSException e) {
            throw new EJBException(e);
        }
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public boolean isSendToEmail() {
        return sendToEmail;
    }

    public void setSendToEmail(boolean sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public boolean isPostInBlog() {
        return postInBlog;
    }

    public void setPostInBlog(boolean postInBlog) {
        this.postInBlog = postInBlog;
    }

    public List<Post> getPosts() {
        List<Post> result = postsDAO.getAll();
        Collections.reverse(result);
        return result;
    }
}

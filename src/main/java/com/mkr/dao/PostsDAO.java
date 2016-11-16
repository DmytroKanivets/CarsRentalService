package com.mkr.dao;

import com.mkr.entity.Post;

import javax.ejb.Stateless;

@Stateless
public class PostsDAO extends AbstractDAO<Post> {
    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }
}

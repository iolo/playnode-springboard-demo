package kr.iolo.springboard.thrift;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import java.util.List;

@ThriftService("Springboard")
public interface TSpringboard {

    //-----------------------------------------------------

    @ThriftMethod("getUsers")
    List<TUser> getUsers();

    @ThriftMethod("getUserByUsernameAndPassword")
    TUser getUserByUsernameAndPassword(String username, String password);

    @ThriftMethod("getUser")
    TUser getUser(int userId);

    @ThriftMethod("createUser")
    int createUser(TUser user);

    @ThriftMethod("updateUser")
    boolean updateUser(TUser user);

    @ThriftMethod("deleteUser")
    boolean deleteUser(int userId);

    //---------------------------------------------------------

    @ThriftMethod("getForums")
    List<TForum> getForums();

    @ThriftMethod("getForum")
    TForum getForum(int forumId);

    @ThriftMethod("createForum")
    int createForum(TForum forum);

    @ThriftMethod("updateForum")
    boolean updateForum(TForum forum);

    @ThriftMethod("deleteForum")
    boolean deleteForum(int forumId);

    //---------------------------------------------------------

    @ThriftMethod("getPosts")
    List<TPost> getPosts(int forumId);

    @ThriftMethod("getPost")
    TPost getPost(int postId);

    @ThriftMethod("createPost")
    int createPost(TPost post);

    @ThriftMethod("updatePost")
    boolean updatePost(TPost post);

    @ThriftMethod("deletePost")
    void deletePost(int postId);

    //---------------------------------------------------------

    @ThriftMethod("getComments")
    List<TComment> getComments(int postId);

    @ThriftMethod("getComment")
    TComment getComment(int commentId);

    @ThriftMethod("createComment")
    int createComment(TComment post);

    @ThriftMethod("updateComment")
    boolean updateComment(TComment post);

    @ThriftMethod("deleteComment")
    boolean deleteComment(int commentId);

}

package kr.iolo.springboard;

import java.util.List;

public interface Springboard {

    //-----------------------------------------------------

    List<User> getUsers();

    User getUser(int id);

    User getUserByUsernameAndPassword(String username, String password);

    int createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    //-----------------------------------------------------

    List<Forum> getForums();

    Forum getForum(int id);

    int createForum(Forum forum);

    boolean updateForum(Forum forum);

    boolean deleteForum(int id);

    //-----------------------------------------------------

    List<Post> getPosts(int forumId);

    Post getPost(int id);

    int createPost(Post post);

    boolean updatePost(Post post);

    boolean deletePost(int id);

    //-----------------------------------------------------

    List<Comment> getComments(int postId);

    Comment getComment(int id);

    int createComment(Comment post);

    boolean updateComment(Comment post);

    boolean deleteComment(int id);

}

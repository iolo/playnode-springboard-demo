package kr.iolo.springboard.thrift;

import kr.iolo.springboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TSpringboardImpl implements TSpringboard {

    @Autowired
    private Springboard springboard;

    //-----------------------------------------------------

    public List<TUser> getUsers() {
        return wrapUsers(springboard.getUsers());
    }

    public TUser getUserByUsernameAndPassword(String username, String password) {
        return wrapUser(springboard.getUserByUsernameAndPassword(username, password));
    }

    public TUser getUser(int userId) {
        return wrapUser(springboard.getUser(userId));
    }

    public int createUser(TUser user) {
        return springboard.createUser(unwrapUser(user));
    }

    public boolean updateUser(TUser user) {
        return springboard.updateUser(unwrapUser(user));
    }

    public boolean deleteUser(int userId) {
        return springboard.deleteUser(userId);
    }

    //---------------------------------------------------------

    public List<TForum> getForums() {
        return wrapForums(springboard.getForums());
    }

    public TForum getForum(int forumId) {
        return wrapForum(springboard.getForum(forumId));
    }

    public int createForum(TForum forum) {
        return springboard.createForum(unwrapForum(forum));
    }

    public boolean updateForum(TForum forum) {
        return springboard.updateForum(unwrapForum(forum));
    }

    public boolean deleteForum(int forumId) {
        return springboard.deleteForum(forumId);
    }

    //---------------------------------------------------------

    public List<TPost> getPosts(int forumId) {
        return wrapPosts(springboard.getPosts(forumId));
    }

    public TPost getPost(int postId) {
        return wrapPost(springboard.getPost(postId));
    }

    public int createPost(TPost post) {
        return springboard.createPost(unwrapPost(post));
    }

    public boolean updatePost(TPost post) {
        return springboard.updatePost(unwrapPost(post));
    }

    public void deletePost(int postId) {
        springboard.deletePost(postId);
    }

    //---------------------------------------------------------

    public List<TComment> getComments(int postId) {
        return wrapComments(springboard.getComments(postId));
    }

    public TComment getComment(int commentId) {
        return wrapComment(springboard.getComment(commentId));
    }

    public int createComment(TComment post) {
        return springboard.createComment(unwrapComment(post));
    }

    public boolean updateComment(TComment post) {
        return springboard.updateComment(unwrapComment(post));
    }

    public boolean deleteComment(int commentId) {
        return springboard.deleteComment(commentId);
    }

    //---------------------------------------------------------

    private List<TUser> wrapUsers(List<User> users) {
        return users.stream().map(this::wrapUser).collect(Collectors.toList());
    }

    private TUser wrapUser(User user) {
        TUser u = new TUser();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        return u;
    }

    private User unwrapUser(TUser user) {
        User u = new User();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        return u;
    }

    private List<TForum> wrapForums(List<Forum> forums) {
        return forums.stream().map(this::wrapForum).collect(Collectors.toList());
    }

    private TForum wrapForum(Forum forum) {
        TForum f = new TForum();
        f.setId(forum.getId());
        f.setTitle(forum.getTitle());
        return f;
    }

    private Forum unwrapForum(TForum forum) {
        Forum f = new Forum();
        f.setId(forum.getId());
        f.setTitle(forum.getTitle());
        return f;
    }

    private List<TPost> wrapPosts(List<Post> posts) {
        return posts.stream().map(this::wrapPost).collect(Collectors.toList());
    }

    private TPost wrapPost(Post post) {
        TPost p = new TPost();
        p.setId(post.getId());
        p.setUserId(post.getUserId());
        p.setForumId(post.getForumId());
        p.setTitle(post.getTitle());
        p.setContent(post.getContent());
        p.setCreatedAt(toString(post.getCreatedAt()));
        return p;
    }

    private Post unwrapPost(TPost post) {
        Post p = new Post();
        p.setId(post.getId());
        p.setUserId(post.getUserId());
        p.setForumId(post.getForumId());
        p.setTitle(post.getTitle());
        p.setContent(post.getContent());
        p.setCreatedAt(toTimestamp(post.getCreatedAt()));
        return p;
    }

    private List<TComment> wrapComments(List<Comment> comments) {
        return comments.stream().map(this::wrapComment).collect(Collectors.toList());
    }

    private TComment wrapComment(Comment comment) {
        TComment c = new TComment();
        c.setId(comment.getId());
        c.setUserId(comment.getUserId());
        c.setPostId(comment.getPostId());
        c.setContent(comment.getContent());
        c.setCreatedAt(toString(comment.getCreatedAt()));
        return c;
    }

    private Comment unwrapComment(TComment comment) {
        Comment c = new Comment();
        c.setId(comment.getId());
        c.setUserId(comment.getUserId());
        c.setPostId(comment.getPostId());
        c.setContent(comment.getContent());
        c.setCreatedAt(toTimestamp(comment.getCreatedAt()));
        return c;
    }

    private static String toString(Timestamp s) {
        try {
            return s.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static Timestamp toTimestamp(String s) {
        try {
            return Timestamp.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }
}

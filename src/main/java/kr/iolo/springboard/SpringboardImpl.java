package kr.iolo.springboard;

import com.mysql.jdbc.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component("springboard")
public class SpringboardImpl implements Springboard {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //-----------------------------------------------------

    @Override
    public List<User> getUsers() {
        __delay(.9, 100);
        return jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword("");//invisible!
            return user;
        });
    }

    @Override
    public User getUser(int id) {
        __delay(.9, 10);
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword("");//invisible!
            return user;
        }, id);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        __delay(.9, 100);
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=? AND password=?", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword("");//invisible!
            return user;
        }, username, password);
    }

    @Override
    public int createUser(User user) {
        __delay(.9, 10);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users(username,passsword) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            return stmt;
        }, keyHolder) == 1) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    @Override
    public boolean updateUser(User user) {
        __delay(.9, 10);
        return jdbcTemplate.update(
                "UPDATE users SET username=?,password=? WHERE id=?",
                user.getUsername(), user.getPassword(), user.getId()) == 1;
    }

    @Override
    public boolean deleteUser(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) == 1;
    }

    //-----------------------------------------------------

    @Override
    public List<Forum> getForums() {
        __delay(.9, 100);
        return jdbcTemplate.query("SELECT * FROM forums", (rs, rowNum) -> {
            Forum forum = new Forum();
            forum.setId(rs.getInt("id"));
            forum.setTitle(rs.getString("title"));
            return forum;
        });
    }

    @Override
    public Forum getForum(int id) {
        __delay(.9, 10);
        return jdbcTemplate.queryForObject("SELECT * FROM forums WHERE id=?", (rs, rowNum) -> {
            Forum forum = new Forum();
            forum.setId(rs.getInt("id"));
            forum.setTitle(rs.getString("title"));
            return forum;
        }, id);
    }

    @Override
    public int createForum(Forum forum) {
        __delay(.9, 10);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO forums(title) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, forum.getTitle());
            return stmt;
        }, keyHolder) == 1) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    @Override
    public boolean updateForum(Forum forum) {
        __delay(.9, 10);
        return jdbcTemplate.update(
                "UPDATE forums SET title=? WHERE id=?",
                forum.getTitle(), forum.getId()) == 1;
    }

    @Override
    public boolean deleteForum(int id) {
        __delay(.9, 10);
        return jdbcTemplate.update("DELETE FROM forums WHERE id=?", id) == 1;
    }

    //-----------------------------------------------------

    @Override
    public List<Post> getPosts(int forumId) {
        __delay(.9, 100);
        return jdbcTemplate.query("SELECT * FROM posts WHERE forumId=?", (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUserId(rs.getInt("userId"));
            post.setForumId(rs.getInt("forumId"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreatedAt(rs.getTimestamp("createdAt"));
            return post;
        }, forumId);
    }

    @Override
    public Post getPost(int id) {
        __delay(.9, 10);
        return jdbcTemplate.queryForObject("SELECT * FROM posts WHERE id=?", (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUserId(rs.getInt("userId"));
            post.setForumId(rs.getInt("forumId"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreatedAt(rs.getTimestamp("createdAt"));
            return post;
        }, id);
    }

    @Override
    public int createPost(Post post) {
        __delay(.9, 10);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO posts(userId,forumId,title,content) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, post.getUserId());
            stmt.setInt(2, post.getForumId());
            stmt.setString(3, post.getTitle());
            stmt.setString(4, post.getContent());
            return stmt;
        }, keyHolder) == 1) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    @Override
    public boolean updatePost(Post post) {
        __delay(.9, 10);
        return jdbcTemplate.update(
                "UPDATE posts SET userId=?,forumId=?,title=?,content=? WHERE id=?",
                post.getUserId(), post.getForumId(), post.getTitle(), post.getContent(), post.getId()) == 1;
    }

    @Override
    public boolean deletePost(int id) {
        __delay(.9, 10);
        return jdbcTemplate.update("DELETE FROM posts WHERE id=?", id) == 1;
    }

    //-----------------------------------------------------

    @Override
    public List<Comment> getComments(int postId) {
        __delay(.9, 100);
        return jdbcTemplate.query("SELECT * FROM comments WHERE postId=?", (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setUserId(rs.getInt("userId"));
            comment.setPostId(rs.getInt("postId"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedAt(rs.getTimestamp("createdAt"));
            return comment;
        }, postId);
    }

    @Override
    public Comment getComment(int id) {
        __delay(.9, 10);
        return jdbcTemplate.queryForObject("SELECT * FROM comments WHERE id=?", (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setUserId(rs.getInt("userId"));
            comment.setPostId(rs.getInt("postId"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedAt(rs.getTimestamp("createdAt"));
            return comment;
        }, id);
    }

    @Override
    public int createComment(Comment comment) {
        __delay(.9, 10);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO comments(userId,postId,content) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, comment.getUserId());
            stmt.setInt(2, comment.getPostId());
            stmt.setString(3, comment.getContent());
            return stmt;
        }, keyHolder) == 1) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    @Override
    public boolean updateComment(Comment comment) {
        __delay(.9, 10);
        return jdbcTemplate.update(
                "UPDATE comments SET userId=?,forumId=?,title=?,content=? WHERE id=?",
                comment.getUserId(), comment.getPostId(), comment.getContent(), comment.getId()) == 1;
    }

    @Override
    public boolean deleteComment(int id) {
        __delay(.9, 10);
        return jdbcTemplate.update("DELETE FROM comments WHERE id=?", id) == 1;
    }

    //---------------------------------------------------------

    private void __delay(double pct, long ms) {
        if (Math.random() > pct) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
            }
        }
    }
}

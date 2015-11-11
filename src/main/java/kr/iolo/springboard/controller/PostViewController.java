package kr.iolo.springboard.controller;

import kr.iolo.springboard.Comment;
import kr.iolo.springboard.Post;
import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.User;
import kr.iolo.springboard.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PostViewController {
    @Autowired
    private Springboard springboard;

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/post_view")
    public String postView(
            @RequestParam("postId") int postId,
            Model model,
            HttpSession session) {
        model
                .addAttribute("postId", postId)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        Post post = cacheService.getOrSet("p:" + postId, Post.class, () -> springboard.getPost(postId));
        if (post == null) {
            model.addAttribute("error", "post not found!");
        } else {
            User postUser = cacheService.getOrSet("u:" + post.getUserId(), User.class, () -> springboard.getUser(post.getUserId()));
            List<Comment> comments = cacheService.getListOrSet("[c:p:" + postId, Comment.class, () -> springboard.getComments(postId));
            Map<Integer, User> commentUsers = comments.stream()
                    .map(Comment::getUserId)
                    .distinct()
                    .map((userId) -> cacheService.getOrSet("u:" + userId, User.class, () -> springboard.getUser(userId)))
                    .collect(Collectors.toMap(User::getId, Function.identity()));
            model
                    .addAttribute("post", post)
                    .addAttribute("postUser", postUser)
                    .addAttribute("comments", comments)
                    .addAttribute("commentUsers", commentUsers);
        }
        return "post_view";
    }

}

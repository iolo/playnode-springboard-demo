package kr.iolo.springboard.controller;

import kr.iolo.springboard.Forum;
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
public class PostListController {
    @Autowired
    private Springboard springboard;

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/post_list")
    public String postList(
            @RequestParam("forumId") int forumId,
            Model model,
            HttpSession session) {
        model
                .addAttribute("forumId", forumId)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        Forum forum = cacheService.getOrSet("f:" + forumId, Forum.class, () -> springboard.getForum(forumId));
        if (forum == null) {
            model.addAttribute("error", "forum not found");
        } else {
            List<Post> posts = cacheService.getListOrSet("[p:f:" + forumId, Post.class, () -> springboard.getPosts(forumId));
            Map<Integer, User> postUsers = posts.stream()
                    .map(Post::getUserId)
                    .distinct()
                    .map((userId) -> cacheService.getOrSet("u:" + userId, User.class, () -> springboard.getUser(userId)))
                    .collect(Collectors.toMap(User::getId, Function.identity()));
            model.addAttribute("forum", forum)
                    .addAttribute("posts", posts)
                    .addAttribute("postUsers", postUsers);
        }
        return "post_list";
    }

}

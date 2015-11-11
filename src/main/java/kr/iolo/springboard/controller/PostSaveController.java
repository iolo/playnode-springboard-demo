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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PostSaveController {
    @Autowired
    private Springboard springboard;

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/post_save", method = RequestMethod.POST)
    public String postSave(
            @RequestParam("forumId") int forumId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:login";
        }
        model
                .addAttribute("forumId", forumId)
                .addAttribute("currentUser", currentUser);
        Forum forum = springboard.getForum(forumId);
        if (forum == null) {
            model.addAttribute("error", "invalid forum!");
            return "post_form";
        }
        model.addAttribute("forum", forum);
        Post post = new Post();
        post.setUserId(currentUser.getId());
        post.setForumId(forumId);
        post.setTitle(title);
        post.setContent(content);
        post.setId(springboard.createPost(post));
        if (post.getId() < 0) {
            model.addAttribute("error", "post save error");
            return "post_form";
        }
        cacheService.remove("[p:f:" + post.getForumId());
        return "redirect:/post_list?forumId=" + forumId + "#post_" + post.getId();
    }

}

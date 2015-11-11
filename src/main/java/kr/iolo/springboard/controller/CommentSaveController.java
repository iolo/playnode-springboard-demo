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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CommentSaveController {
    @Autowired
    private Springboard springboard;

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/comment_save", method = RequestMethod.POST)
    public String commentSave(
            @RequestParam("postId") int postId,
            @RequestParam("content") String content,
            Model model,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:login";
        }
        model
                .addAttribute("postId", postId)
                .addAttribute("currentUser", currentUser);
        Post post = springboard.getPost(postId);
        if (post == null) {
            model.addAttribute("error", "invalid post!");
            return "post_view";
        }
        model.addAttribute("post", post);
        Comment comment = new Comment();
        comment.setUserId(currentUser.getId());
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setId(springboard.createComment(comment));
        if (comment.getId() < 0) {
            model.addAttribute("error", "comment save error!");
            return "post_view";
        }
        cacheService.remove("[c:p:" + comment.getPostId());
        return "redirect:post_view?postId=" + comment.getPostId() + "#comment_" + comment.getId();
    }
}

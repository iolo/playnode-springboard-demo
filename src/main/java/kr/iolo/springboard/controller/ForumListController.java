package kr.iolo.springboard.controller;

import kr.iolo.springboard.Forum;
import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ForumListController {
    @Autowired
    private Springboard springboard;

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/forum_list")
    public String forumList(
            Model model, HttpSession session) {
        List<Forum> forums = cacheService.getListOrSet("[f", Forum.class, () -> springboard.getForums());
        model
                .addAttribute("forums", forums)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        return "forum_list";
    }

}

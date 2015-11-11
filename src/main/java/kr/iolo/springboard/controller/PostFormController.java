package kr.iolo.springboard.controller;

import kr.iolo.springboard.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PostFormController {

    @RequestMapping("/post_form")
    public String postForm(
            @RequestParam("forumId") int forumId,
            Model model,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:login";
        }
        model
                .addAttribute("forumId", forumId)
                .addAttribute("currentUser", currentUser);
        return "post_form";
    }

}

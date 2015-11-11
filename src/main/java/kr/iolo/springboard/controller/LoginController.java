package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private Springboard springboard;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "returnUrl", defaultValue = "/forum_list") String returnUrl,
            Model model) {
        model.addAttribute("returnUrl", returnUrl);
        return "login";
    }

    @RequestMapping(value = "/login_post", method = RequestMethod.POST)
    public String loginPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "returnUrl", defaultValue = "/forum_list") String returnUrl,
            Model model,
            HttpSession session) {
        User user = springboard.getUserByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:" + returnUrl;
        }
        session.removeAttribute("currentUser");
        model.addAttribute("error", "login error!");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(
            @RequestParam(value = "returnUrl", defaultValue = "/forum_list") String returnUrl,
            HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:" + returnUrl;
    }
}

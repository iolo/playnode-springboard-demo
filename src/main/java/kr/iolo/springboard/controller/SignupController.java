package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {
    @Autowired
    private Springboard springboard;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(
            @RequestParam(value = "returnUrl", defaultValue = "/forum_list") String returnUrl,
            Model model) {
        model.addAttribute("returnUrl", returnUrl);
        return "signup";
    }

    @RequestMapping(value = "/signup_post", method = RequestMethod.POST)
    public String signupPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "returnUrl", defaultValue = "/forum_list") String returnUrl,
            Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (springboard.createUser(user) > 0) {
            return "redirect:" + returnUrl;
        }
        model.addAttribute("error", "signup error!");
        return "signup";
    }
}

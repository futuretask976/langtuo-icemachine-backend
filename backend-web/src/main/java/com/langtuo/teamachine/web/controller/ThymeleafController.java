package com.langtuo.teamachine.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jiaqing
 */
@Controller
public class ThymeleafController {
    /**
     * url: http://localhost:8080/gxsp3demo/
     * @return
     */
    @GetMapping(value = "/")
    public String index(Model model) {
        try {
            System.out.println("ThymeleafController#index entering");
            model.addAttribute("msg","Thymeleaf入门案例...");
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail: " + e.toString();
        }
    }

    /**
     * url: http://localhost:8080/gxsp3demo/login
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, @RequestParam(name="error", required = false) String error) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome() {
        System.out.printf("$$$$$ ThymeleafController#welcome entering\n");
        // 处理登录逻辑
        return "welcome"; // 登录成功后跳转的页面
    }

    @GetMapping("/bye")
    public String bye(Model model) {
        System.out.printf("$$$$$ ThymeleafController#bye entering\n");
        model.addAttribute("msg", "see you next");
        return "bye"; // 登录成功后跳转的页面
    }
}

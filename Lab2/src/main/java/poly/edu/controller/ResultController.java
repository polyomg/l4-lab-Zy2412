package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResultController {

    // /a → hiển thị view poly/a.html
    @RequestMapping("/a")
    public String m1() {
        return "poly/a";
    }

    // /b → forward sang /a, giữ Model
    @RequestMapping("/b")
    public String m2(Model model) {
        model.addAttribute("message", "I come from b");
        return "forward:/a";  // ?1
    }

    // /c → redirect sang /a, giữ RedirectAttributes
    @RequestMapping("/c")
    public String m3(RedirectAttributes params) {
        params.addAttribute("message", "I come from c");
        return "redirect:/a";  // ?2
    }

    // /d → hiển thị thẳng kết quả, không qua view
    @ResponseBody  // ?3
    @RequestMapping("/d")
    public String m4() {
        return "I come from d";
    }
}

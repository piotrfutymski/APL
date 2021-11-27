package put.apl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", "/experiments", "experiments/sorting", "experiments/graph"})
    public String index() {
        return "index";
    }
}

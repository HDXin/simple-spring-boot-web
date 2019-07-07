package top.atstudy.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.atstudy.framework.controller.RestPrototypeController;

@RestPrototypeController
@RequestMapping("/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountVo.class);

    @PostMapping("")
    public AccountVo create(@RequestBody AccountVo req){
        logger.info(" ===>> account：{}", req.toString());
        return req;
    }

}

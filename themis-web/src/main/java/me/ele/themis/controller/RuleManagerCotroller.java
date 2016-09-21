package me.ele.themis.controller;

import me.ele.themis.ruleengine.api.RuleService;
import me.ele.themis.ruleengine.api.output.RuleScriptOut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 19/9/2016.
 */
@Controller
@RequestMapping("/rule")
public class RuleManagerCotroller {

    private RuleService ruleService ;

    @PostConstruct
    public void init() throws IOException {
        ruleService = ClientUtils.getClient(RuleService.class);
    }

    @RequestMapping(value = "/QUERYALL", method = RequestMethod.GET)
    @ResponseBody
    public List<RuleScriptOut> query(){
        return ruleService.queryAllRules();
    }

    @RequestMapping(value = "/RELOAD", method = RequestMethod.GET)
    @ResponseBody
    public String reload(@RequestParam(value="ruleIndex") int ruleIndex){
        return ruleService.reloadRule(ruleIndex);
    }

    @RequestMapping(value = "/ADD", method = RequestMethod.GET)
    @ResponseBody
    public int add(@RequestParam(value="ruleName")String ruleName , @RequestParam(value="script")String script){
        return ruleService.addRule(ruleName , script);
    }
}

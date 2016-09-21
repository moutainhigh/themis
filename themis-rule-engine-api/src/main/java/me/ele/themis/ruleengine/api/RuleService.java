package me.ele.themis.ruleengine.api;

import me.ele.themis.ruleengine.api.output.RuleScriptOut;

import java.util.List;

/**
 * Created by admin on 14/9/2016.
 */
public interface RuleService {

    public String testRule();

    public String startTest();

    /**
     * 新增规则
     * @param ruleName
     * @param ruleScript
     * @return
     */
    public int addRule(String ruleName , String ruleScript);

    /**
     * 加载规则
     * @param ruleIndex
     * @return
     */
    public String reloadRule(int ruleIndex);


    /**
     * 查询所有规则
     * @return
     */
    public List<RuleScriptOut> queryAllRules();

}

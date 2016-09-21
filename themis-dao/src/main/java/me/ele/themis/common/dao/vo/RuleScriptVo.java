package me.ele.themis.common.dao.vo;

/**
 * Created by admin on 19/9/2016.
 */
public class RuleScriptVo {

    /** 规则index*/
    private int ruleId;

    /** 规则名称*/
    private String ruleName;

    /** 规则脚本*/
    private String script;

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}

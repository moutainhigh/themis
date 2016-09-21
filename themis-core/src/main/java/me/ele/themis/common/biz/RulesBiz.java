package me.ele.themis.common.biz;

import me.ele.themis.common.dao.RulesDao;
import me.ele.themis.common.dao.vo.RuleScriptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 19/9/2016.
 */
@Service
public class RulesBiz {

    @Autowired
    private RulesDao rulesDao;

    public String queryRulesById(int ruleId){
        return rulesDao.queryRulesScript(ruleId);
    }

    public int addRule(String ruleName , String script){
        return rulesDao.addRule(ruleName , script);
    }

    public List<RuleScriptVo> queryAllRules(){
        return rulesDao.queryAllRules();
    }

}

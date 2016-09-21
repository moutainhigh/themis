package me.ele.themis.common.dao;

import me.ele.themis.common.dao.vo.RuleScriptVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 19/9/2016.
 */
@Service
public interface RulesDao {

    /**
     * 根据id查询规则脚本
     * @param ruleId
     * @return
     */
    String queryRulesScript(int ruleId);


    /**
     * 插入新规则
     * @param ruleName
     * @param script
     * @return
     */
    int addRule(@Param("ruleName")String ruleName , @Param("script")String script);

    /**
     * 查询全部规则
     * @return
     */
    List<RuleScriptVo> queryAllRules();
}

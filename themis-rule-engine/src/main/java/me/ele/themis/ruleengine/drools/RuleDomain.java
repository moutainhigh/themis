package me.ele.themis.ruleengine.drools;

import org.springframework.stereotype.Service;

/**
 * Created by admin on 8/9/2016.
 */
@Service
public class RuleDomain {

    /**
     * 规则名称
     */
    private String name;


    /**
     * 规则类型
     */
    private int type;

    /**
     * 规则描述
     */
    private String discription;

    /**
     * 规则金额
     */
    private int award;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }


    @Override
    public String toString(){
        return "name = " + name + "|type = " + type + "|discription = " + discription + "|award = " + award;
    }

}

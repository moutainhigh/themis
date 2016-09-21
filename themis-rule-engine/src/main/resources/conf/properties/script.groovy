import me.ele.themis.ruleengine.drools.RuleDomain;

class TestScript {
    static String excute(RuleDomain ruleDomain) {

        if (ruleDomain.getType() > 10) {
            ruleDomain.setDiscription("该数字大于10");
        }else {
            ruleDomain.setDiscription("  该数字小于 10");
        };
        return ruleDomain.toString();
    }
}

;//groovy script
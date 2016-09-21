package me.ele.themis.ruleengine.quartz;



import me.ele.themis.ruleengine.drools.RuleClient;
import me.ele.themis.ruleengine.drools.RuleDomain;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 14/9/2016.
 */
public class PullOrderJob{

    @Autowired
    private RuleDomain ruleDomain;

    @Autowired
    RuleClient ruleClient;


    public void execute() throws JobExecutionException {

        ruleDomain.setType(new Random().nextInt(30));
        System.out.println("Start: " + ruleDomain);

        ruleClient.ruleExcute(ruleDomain);

        System.out.println("   @@@" );
    }
}

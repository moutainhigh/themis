package me.ele.themis.ruleengine.drools;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by admin on 8/9/2016.
 */
@Service
public class RuleTestTask extends Thread {

    @Autowired
    private RuleDomain ruleDomain;


    @Autowired
    RuleClient ruleClient;

    @Override
    public void run() {

        ruleDomain.setAward(0);
        ruleDomain.setDiscription("init");
        ruleDomain.setName("init");
        ruleDomain.setType(0);

        System.out.println("ruleTest Task start!");
        while (true) {
            ruleDomain.setType(new Random().nextInt(30));

//            StatefulKnowledgeSession ksessionTemplate = ruleClient.getNewSession();
//            ksessionTemplate.insert(ruleDomain);
//            ksessionTemplate.fireAllRules();
//            ksessionTemplate.dispose();

            System.out.println(ruleDomain);

            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

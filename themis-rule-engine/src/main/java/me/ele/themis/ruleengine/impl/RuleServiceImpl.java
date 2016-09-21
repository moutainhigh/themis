package me.ele.themis.ruleengine.impl;


import me.ele.themis.common.biz.InqueryBiz;
import me.ele.themis.common.biz.RulesBiz;
import me.ele.themis.common.dao.vo.RuleScriptVo;
import me.ele.themis.ruleengine.api.RuleService;
import me.ele.themis.ruleengine.api.output.RuleScriptOut;
import me.ele.themis.ruleengine.drools.RuleClient;
import me.ele.themis.ruleengine.drools.RuleDomain;
import me.ele.themis.ruleengine.drools.RuleTestTask;
import org.drools.StatefulSession;
import org.drools.event.ObjectInsertedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.ObjectUpdatedEvent;
import org.drools.event.WorkingMemoryEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.ele.themis.ruleengine.rabbitmq.MessageProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 14/9/2016.
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private InqueryBiz inqueryBiz;

    @Autowired
    private RulesBiz rulesBiz;

    @Autowired
    private MessageProducer msgProducer;

    @Autowired
    private RuleTestTask ruleTestTask;

    @Autowired
    RuleClient ruleClient;


    @Autowired
    private RuleDomain ruleDomain;


    @Override
    public String testRule() {
        return test();
    }

    @Override
    public String startTest(){
        ruleTestTask.start();

        return "";
    }

    @Override
    public int addRule(String ruleName, String ruleScript) {
        return rulesBiz.addRule(ruleName , ruleScript);
    }

    @Override
    public String reloadRule(int ruleIndex) {
        return ruleClient.reloadGroovyRuleEngine(ruleIndex);
    }

    @Override
    public List<RuleScriptOut> queryAllRules() {
        List<RuleScriptOut> list = new ArrayList<>();

        List<RuleScriptVo> result = rulesBiz.queryAllRules();
        for(RuleScriptVo vo : result){
            RuleScriptOut out = new RuleScriptOut();
            out.setRuleId(vo.getRuleId());
            out.setRuleName(vo.getRuleName());
            out.setScript(vo.getScript());
            list.add(out);
        }
        return list;
    }

    public String test(){
        return ruleClient.ruleExcute(ruleDomain);
    }

    public String queryPrice(){
        StatefulSession ksessionTemplate = ruleClient.getNewStatefulSession();
        ksessionTemplate.insert(ruleDomain);
        ksessionTemplate.addEventListener(new WorkingMemoryEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent objectInsertedEvent) {

            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent objectUpdatedEvent) {

            }

            @Override
            public void objectRetracted(ObjectRetractedEvent objectRetractedEvent) {

            }
        });
        ksessionTemplate.fireAllRules();

        return ruleDomain.toString();
//        return inqueryDao.getPrice().toString()  +  redisClient.get("dist");
    }

    public void testMQ(){
//        msgProducer.sendMsg("测试手工消费");

//        singleProducerTest();

//        batchProducerTest();
    }


    private void singleProducerTest(){

        for(int i = 0 ; i<100000 ; i++){
            msgProducer.sendMsg(i);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void batchProducerTest(){
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        for (int index = 0 ; index < 5; index ++ ){
            pool.execute(
                    new Runnable(){
                        public void run(){
                            for(int i = 0 ; i<100000 ; i++){
                                msgProducer.sendMsg(i);
                                try {
                                    Thread.currentThread().sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
        }

        pool.shutdown();
    }

}

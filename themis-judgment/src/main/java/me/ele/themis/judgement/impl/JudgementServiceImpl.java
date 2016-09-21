package me.ele.themis.judgement.impl;

import me.ele.themis.common.biz.InqueryBiz;
import me.ele.themis.judgement.api.JudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by guoxin on 13/9/2016.
 */

@Service
public class JudgementServiceImpl implements JudgementService {


    @Autowired
    private InqueryBiz inqueryBiz;

    @Override
    public String queryJudgement() {
        return inqueryBiz.queryPrice() ;
    }

    @Override
    public String testJudgement() {
        return inqueryBiz.starTest();
    }



}

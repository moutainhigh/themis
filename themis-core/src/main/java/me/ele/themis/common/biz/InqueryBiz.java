package me.ele.themis.common.biz;

import me.ele.themis.common.dao.InqueryDao;
import me.ele.themis.common.redis.RedisClient;
import org.drools.event.rule.DefaultWorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by guoxin on 23/8/2016.
 */
@Service
public class InqueryBiz {

    @Autowired
    private InqueryDao inqueryDao;

    @Autowired
    private RedisClient redisClient;

    public String queryPrice() {
        return inqueryDao.getPrice().toString() + redisClient.get("aaa");
    }

    public String starTest() {
        return null;

    }

}

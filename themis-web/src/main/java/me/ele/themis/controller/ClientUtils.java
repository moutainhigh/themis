package me.ele.themis.controller;

import me.ele.arch.etrace.agent.config.AgentConfiguration;
import me.ele.contract.client.ClientUtil;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by guoxin on 22/8/2016.
 */
public class ClientUtils {

    private String collectorIp;

    public void setCollectorIp(String collectorIp) {
        this.collectorIp = collectorIp;
    }

    static {
		/* 获取系统当前路径 */
        String s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(s);
		/* 获取系统工程路径 */
        String rootPath = file.getParentFile().getParentFile().getPath();
		/* 保存系统配置文件路径 */
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath);
        sb.append("/WEB-INF/classes/conf/Client.json");
        ClientUtil.getContext().initClients(sb.toString());
    }

    @PostConstruct
    public void init() {
        initETrace();
    }

    public static <T> T getClient(Class<T> clazz) {
        return ClientUtil.getContext().getClient(clazz);
    }

    /**
     * 初始化ETrace
     */
    public void initETrace() {
        AgentConfiguration.setAppId("themis.webapi");
        AgentConfiguration.setCollectorIp(collectorIp);//替换对应环境的collector地址
    }
}

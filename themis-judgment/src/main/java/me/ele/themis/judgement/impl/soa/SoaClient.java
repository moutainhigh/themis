package me.ele.themis.judgement.impl.soa;

import me.ele.contract.client.ClientUtil;
import me.ele.contract.iface.IServiceChecker;

import java.io.File;

/**
 * Created by guoxin on 22/8/2016.
 */
public class SoaClient {
    private static boolean hasInit = false;

    public static <T> T getClient(Class<T> clazz) {
        return ClientUtil.getContext().getClient(clazz);
    }


    /**
     * 初始化soa客户端
     */
    public static void initClients() {
        if (!hasInit) {

            		/* 获取系统当前路径 */
            String s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File file = new File(s);
		/* 获取系统工程路径 */
            String rootPath = file.getParentFile().getParentFile().getPath();
		/* 保存系统配置文件路径 */
            StringBuilder sb = new StringBuilder();
            sb.append(rootPath);
            sb.append("/target/classes/conf/Configure.json");
            ClientUtil.getContext().initClients(sb.toString());
            hasInit = true;
        }
    }

    /**
     * 重新初始化soa客户端
     */
    public static void reInitClient() {
        ClientUtil.getContext().initClients("conf/Configure.json");
        hasInit = true;
    }
}

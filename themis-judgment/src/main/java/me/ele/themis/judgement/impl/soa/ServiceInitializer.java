package me.ele.themis.judgement.impl.soa;

import me.ele.contract.iface.IServiceChecker;
import me.ele.contract.iface.IServiceDumper;
import me.ele.contract.iface.IServiceInitializer;
import me.ele.themis.judgement.impl.soa.ServiceChecker;
import me.ele.themis.judgement.impl.soa.ServiceDump;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guoxin on 22/8/2016.
 */
public class ServiceInitializer implements IServiceInitializer {


    private ApplicationContext applicationContext;
    private ServiceChecker serviceChecker;
    private IServiceDumper serviceDumper;

    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("service.xml");         // 初始化注册实例
        serviceChecker = new ServiceChecker();                                          // 初始化服务检测对象
        serviceDumper = new ServiceDump();                                              // 初始化服务状态对象

    }

    public Object getImpl(Class<?> iface) {
        return applicationContext.getBean(iface);
    }


    public IServiceChecker getChecker() {
        return serviceChecker;
    }

    public IServiceDumper getDumper() {
        return serviceDumper;
    }
}

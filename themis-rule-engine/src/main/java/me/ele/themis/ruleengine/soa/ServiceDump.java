package me.ele.themis.ruleengine.soa;

import me.ele.contract.iface.IServiceDumper;

/**
 * Created by guoxin on 22/8/2016.
 */
public class ServiceDump implements IServiceDumper {

    public Object dumpInfo() {
        return new ServiceInfo();
    }

    private static class ServiceInfo{
        private String serviceName = "distribution.base";
        private boolean isWorking = true;

        public String getServiceName() {
            return serviceName;
        }

        public boolean isWorking() {
            return isWorking;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public void setWorking(boolean working) {
            isWorking = working;
        }
    }
}

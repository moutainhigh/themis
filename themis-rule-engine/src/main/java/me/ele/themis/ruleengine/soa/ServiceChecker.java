package me.ele.themis.ruleengine.soa;

import me.ele.contract.iface.IServiceChecker;

/**
 * Created by guoxin on 22/8/2016.
 */
public class ServiceChecker  implements IServiceChecker {

    public boolean isAvailable() {
        return true;
    }

}

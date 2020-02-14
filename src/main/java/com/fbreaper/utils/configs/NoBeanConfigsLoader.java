package com.fbreaper.utils.configs;

import org.aeonbits.owner.ConfigFactory;

public class NoBeanConfigsLoader {

    private static NoBeanConfigurations conf;

    public static NoBeanConfigurations load(){
        if (conf == null){
            conf = ConfigFactory.create(NoBeanConfigurations.class, System.getProperties());
        }
        return conf;
    }

}

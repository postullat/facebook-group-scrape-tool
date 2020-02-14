package com.fbreaper.utils.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:application.properties")
public interface NoBeanConfigurations extends Config {

    @Key("fb.big.images.load.timeout")
    @DefaultValue("2000")
    Long fbBigImagesLoadTimeout();

    @Key("fb.big.images.limit")
    @DefaultValue("15")
    Integer fbBigImagesLimit();


}

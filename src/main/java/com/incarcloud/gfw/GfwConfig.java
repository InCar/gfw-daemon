package com.incarcloud.gfw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GfwConfig {
    @Value("${gfw.ping}")
    public String ping;

    @Value("${gfw.vpn}")
    public String vpn;
}

package com.plant.fruit.strawberry.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sa-token.filter")
public class SaTokenProperties {

    private String[] includes = {"/**"};

    private String[] excludes = {};

}

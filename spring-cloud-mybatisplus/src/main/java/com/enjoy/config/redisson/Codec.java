package com.enjoy.config.redisson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: APIGateway
 * @description:
 * @author: LiZhaofu
 * @create: 2021-06-22 18:25
 **/

@Data
@Component
public class Codec implements Serializable {
    private static final long serialVersionUID = -8394853238388494359L;
    @JsonProperty(value = "class")
    @Value("${redisson.codec.class:org.redisson.codec.SnappyCodec}")
    String cls;
}

package com.hf.study.socket.web.controller;

import cn.hutool.core.util.RandomUtil;
import com.hf.tools.common.entity.ResponseData;
import com.hf.tools.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping("/create")
    public ResponseData<Pair<Long, String>> createRandomToken() {
        long userId = RandomUtil.randomLong(10000);
        String token = JwtUtils.generateToken(userId, List.of("JAVA"));
        log.info("create random: userId:{}, token:{}", userId, token);
        return ResponseData.success(Pair.of(userId, token));
    }

}

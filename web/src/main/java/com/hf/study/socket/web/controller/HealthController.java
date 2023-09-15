package com.hf.study.socket.web.controller;

import com.hf.tools.common.entity.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiehongfei
 * @description
 * @date 2023/8/27 15:56
 */
@RestController
public class HealthController {


    @GetMapping("/")
    private ResponseData<String> get() {
        return ResponseData.success("Hello world");
    }
}

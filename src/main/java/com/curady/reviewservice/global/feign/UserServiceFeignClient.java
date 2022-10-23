package com.curady.reviewservice.global.feign;


import com.curady.reviewservice.global.feign.dto.ResponseUserNicknameAndImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @GetMapping("/users/nickname/img")
    ResponseUserNicknameAndImage getUserNicknameAndImg(@RequestBody List<Long> list);
}


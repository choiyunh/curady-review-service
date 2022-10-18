package com.curady.reviewservice.global.feign;


import com.curady.reviewservice.global.feign.dto.ResponseUserNicknameAndImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @PostMapping("/users/nickname/img")
    ResponseUserNicknameAndImage getUserNicknameAndImg(@RequestBody List<Long> list);
}


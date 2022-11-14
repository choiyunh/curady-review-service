package com.curady.reviewservice.global.feign;

import com.curady.reviewservice.global.feign.dto.ResponseLecture;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "lecture-service")
public interface LectureServiceFeignClient {
    @PostMapping("/lecture/name/vendor")
    ResponseLecture getLectureNameAndVendor(@RequestBody List<Long> lectureIdList);
}
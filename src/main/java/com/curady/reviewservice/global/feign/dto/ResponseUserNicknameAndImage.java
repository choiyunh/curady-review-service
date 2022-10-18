package com.curady.reviewservice.global.feign.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseUserNicknameAndImage {
    List<Data> data;

    @Getter
    public static class Data {
        private String nickname;
        private String imageUrl;
    }
}

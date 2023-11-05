package com.offer.utils;

public class SliceUtils {
    /**
     * spring data jpa의 PageRequest는 page번호 0부터 시작하기 때문에 -1을 한 값을 기준으로 사용
     */
    public static int getSliceNumber(int page) {
        return Math.max(page - 1, 0);
    }
}

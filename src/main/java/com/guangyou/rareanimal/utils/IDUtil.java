package com.guangyou.rareanimal.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
@Slf4j
public class IDUtil {

    /**
     * 生成随机图片名
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

}


package com.guangyou.rareanimal.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;


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
        return millis + String.format("%03d", end3);
    }


    /**
     * 生成范围为 0~range 的 numbers 个 不同的 随机整数
     * @param
     * @param numbers 需要的 随机整数 个数
     * @param range 生成 随机整数 的范围
     * @return
     */
    public static List<Integer> getRandIntegerList(int numbers, int range){
        Random random = new Random();
        //使用 set 确保生成的整数不重复
        List<Integer> randIntegerList = new ArrayList<>();
        while (randIntegerList.size() < numbers){
            int randInteger = random.nextInt(range);
            //若 list 中不包含该随机数，则添加进 list 集合中
            if (!randIntegerList.contains(randInteger)){
                randIntegerList.add(randInteger);
            }
        }

        return randIntegerList;
    }

}


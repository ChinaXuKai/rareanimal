package com.guangyou.rareanimal.mapper;

import com.guangyou.rareanimal.pojo.Scheduled;
import org.springframework.stereotype.Repository;

/**
 * @author xukai
 * @create 2023-02-10 10:44
 */
@Repository
public interface ScheduledMapper {

    String selectScheduledById(Long cronId);
}

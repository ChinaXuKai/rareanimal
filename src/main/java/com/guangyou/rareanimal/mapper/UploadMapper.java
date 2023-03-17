package com.guangyou.rareanimal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xukai
 * @create 2023-03-14 11:18
 */
@Repository
public interface UploadMapper {

    int uploadImg(Integer isUserAvatarImg, Long userId,
                  Integer isArticleContentImg, Integer isArticleCoverImg, String imageUrl);
}

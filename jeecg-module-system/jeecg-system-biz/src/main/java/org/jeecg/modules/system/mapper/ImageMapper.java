package org.jeecg.modules.system.mapper;

import org.jeecg.modules.system.entity.Image;

import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/1 15:19
 */
public interface ImageMapper {
    List<Image> findAll(String uid);
    int insert(Image image);
}

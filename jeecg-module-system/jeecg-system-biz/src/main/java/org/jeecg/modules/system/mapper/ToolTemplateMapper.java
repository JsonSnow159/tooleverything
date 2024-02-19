package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.ToolTemplate;

/**
 * @Author:吴金才
 * @Date:2024/2/1 15:12
 */
public interface ToolTemplateMapper {
    ToolTemplate findByTool(@Param("toolCode") String toolCode, @Param("version") String version);
    int insert(ToolTemplate toolTemplate);
    int update(ToolTemplateMapper toolTemplateMapper);
}

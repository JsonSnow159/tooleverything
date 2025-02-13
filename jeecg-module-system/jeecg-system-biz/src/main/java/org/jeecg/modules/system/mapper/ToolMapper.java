package org.jeecg.modules.system.mapper;

import org.jeecg.modules.system.entity.Tool;
import org.jeecg.modules.system.entity.ToolInfo;

import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/1 14:41
 */

public interface ToolMapper {
    Tool findByToolCode(String toolCode, Integer version);
    List<ToolInfo> findAll(Integer offset, Integer pageSize);
    int count();
    List<Tool> findEnableTool();
    List<Tool> findAll1();
    ToolInfo findDetail(String toolCode, Integer version);
    int insert(Tool tool);
    int update(Tool tool);
    int delete(String toolCode, Integer version);
}

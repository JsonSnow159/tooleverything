package org.jeecg.modules.system.service;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.dto.tool.ToolConfigInDTO;
import org.jeecg.modules.system.dto.tool.ToolConfigOutDTO;
import org.jeecg.modules.system.dto.tool.ToolConfigQueryInDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/7 13:41
 */
public interface ToolConfigService {
    boolean addToolConfig(ToolConfigInDTO inDTO);
    boolean updateToolConfig(ToolConfigInDTO inDTO);
    PR findAll(ToolConfigQueryInDTO inDTO);
    ToolConfigOutDTO findDetail(ToolConfigInDTO inDTO);
    boolean deleteToolConfig(ToolConfigInDTO inDTO);
}

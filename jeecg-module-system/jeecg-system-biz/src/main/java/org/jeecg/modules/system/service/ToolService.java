package org.jeecg.modules.system.service;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.common.PageSearchDTO;
import org.jeecg.modules.system.dto.tool.ToolAddInDTO;
import org.jeecg.modules.system.dto.tool.ToolQueryInDTO;
import org.jeecg.modules.system.dto.tool.ToolQueryOutDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/7 13:41
 */
public interface ToolService {
    boolean addTool(ToolAddInDTO inDTO);
    boolean updateTool(ToolAddInDTO inDTO);
    PR findAll(PageSearchDTO pageSearchDTO);

    ToolQueryOutDTO findDetail(ToolQueryInDTO toolQueryInDTO);
    boolean deleteTool(ToolQueryInDTO inDTO);
}

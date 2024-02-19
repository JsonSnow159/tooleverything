package org.jeecg.modules.system.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.common.PageSearchDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/17 12:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolConfigQueryInDTO extends PageSearchDTO {
    /**
     * 工具编码
     */
    private String toolCode;
    /**
     * 工具版本
     */
    private Integer version;
}

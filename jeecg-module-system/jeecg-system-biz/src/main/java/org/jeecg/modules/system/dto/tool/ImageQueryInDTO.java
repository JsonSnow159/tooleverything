package org.jeecg.modules.system.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.common.PageSearchDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/7 16:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageQueryInDTO extends PageSearchDTO {
    /**
     * 用户id
     */
    private String uid;
}

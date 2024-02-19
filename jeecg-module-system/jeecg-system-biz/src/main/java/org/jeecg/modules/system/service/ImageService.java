package org.jeecg.modules.system.service;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.dto.tool.ImageQueryInDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/7 13:41
 */
public interface ImageService {
    PR findAll(ImageQueryInDTO inDTO);
}

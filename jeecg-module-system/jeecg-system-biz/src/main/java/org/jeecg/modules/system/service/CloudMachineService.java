package org.jeecg.modules.system.service;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.common.PageSearchDTO;
import org.jeecg.modules.system.dto.tool.CloudMachineInDTO;
import org.jeecg.modules.system.dto.tool.CloudMachineOutDTO;

/**
 * @Author:吴金才
 * @Date:2024/2/7 13:41
 */
public interface CloudMachineService {
    boolean add(CloudMachineInDTO inDTO);
    boolean update(CloudMachineInDTO inDTO);
    PR findAll(PageSearchDTO pageSearchDTO);
    CloudMachineOutDTO findDetail(Long id);
    boolean delete(Long id);
}

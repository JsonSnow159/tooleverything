package org.jeecg.modules.system.mapper;

import org.jeecg.modules.system.entity.CloudMachine;

import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/1 11:45
 */

public interface CloudMachineMapper {
    List<CloudMachine> findAll();
    List<CloudMachine> findByPage(Integer offset, Integer pageSize);
    int count();
    CloudMachine findDetail(Long id);
    int insert(CloudMachine cloudMachine);
    int update(CloudMachine cloudMachine);
    int delete(Long id);
}

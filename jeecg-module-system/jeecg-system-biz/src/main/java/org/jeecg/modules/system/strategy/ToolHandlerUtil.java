package org.jeecg.modules.system.strategy;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.dto.ToolRequestDTO;
import org.jeecg.modules.system.enums.ToolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:吴金才
 * @Date:2024/2/5 17:00
 */
@Slf4j
@Component
public class ToolHandlerUtil {
    private Map<String, ToolStrategy> strategyMap = new HashMap<>();

    @Autowired
    public ToolHandlerUtil(List<ToolStrategy> toolStrategies) {
        for (ToolStrategy strategy : toolStrategies) {
            strategyMap.put(strategy.getToolCode(), strategy);
        }
    }

    public void doHandle(ToolEnum toolEnum, ToolRequestDTO toolRequest) {
        ToolStrategy toolStrategy = strategyMap.get(toolEnum.getCode());
        toolStrategy.handler(toolRequest);
    }
}

package org.jeecg.modules.system.mapper;

import org.jeecg.modules.system.entity.Mission;

import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/1 14:06
 */
public interface MissionMapper {
    List<Mission> findByUser(String uid);
    Mission findByMissionId(String missionId);
    Mission findByPromptId(String promptId);
    int insert(Mission mission);
    int update(Mission mission);
}

package org.jeecg.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.common.CommonConstant;
import org.jeecg.modules.system.common.ICacheService;
import org.jeecg.modules.system.dto.HistoryByPromptIdResponse;
import org.jeecg.modules.system.dto.ViewQueueResponse;
import org.jeecg.modules.system.dto.WsMessage;
import org.jeecg.modules.system.entity.Mission;
import org.jeecg.modules.system.entity.MissionOutput;
import org.jeecg.modules.system.enums.ImageStatusEnum;
import org.jeecg.modules.system.enums.MissionStatusEnum;
import org.jeecg.modules.system.mapper.MissionMapper;
import org.jeecg.modules.system.mapper.MissionOutputMapper;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author:吴金才
 * @Date:2024/2/6 10:48
 */
@Slf4j
@Service
public class WebSocketMsgHandleService {
    @Resource
    private ComfyUIQueueServiceImpl comfyUIQueueService;
    @Resource
    private ICacheService cacheService;
    @Resource
    private ComfyUIQueryByPromptIdServiceImpl comfyUIQueryByPromptIdService;
    @Resource
    private MissionMapper missionMapper;
    @Resource
    private MissionOutputMapper missionOutputMapper;

    public void handleMessage(String machineAddress, String message) {
        ViewQueueResponse viewQueueResponse = comfyUIQueueService.viewQueue(machineAddress);
        List<List<Object>> queueRunning = viewQueueResponse.getQueueRunning();
        //全部执行完成
        String runningPromptId = null;
        if (!CollectionUtils.isEmpty(queueRunning)) {
            List<Object> queueRunningInfo = queueRunning.get(0);
            runningPromptId = (String) queueRunningInfo.get(1);
        }
        String runningMissionKey = String.format(CommonConstant.RUNNING_MISSION_KEY, machineAddress);
        Set<ZSetOperations.TypedTuple<String>> runningMissionSet = cacheService.zSort(runningMissionKey);
        for (ZSetOperations.TypedTuple<String> runningMission : runningMissionSet) {
            String promptId = runningMission.getValue();
            if (Objects.equals(runningPromptId, promptId)) {
                break;
            }
            Mission mission = missionMapper.findByPromptId(promptId);
            try {
                //查询任务情况
                HistoryByPromptIdResponse historyByPromptIdResponse = comfyUIQueryByPromptIdService.historyByPromptId(machineAddress, promptId);
                HistoryByPromptIdResponse.StatusDTO status = historyByPromptIdResponse.getStatus();
                if (Objects.equals(status.getStatusStr(), "success") && status.getCompleted()) {
                    HistoryByPromptIdResponse.OutputsDTO outputs = historyByPromptIdResponse.getOutputs();
                    HistoryByPromptIdResponse.OutputsDTO._$9DTO nine = outputs.getNine();
                    List<HistoryByPromptIdResponse.OutputsDTO._$9DTO.ImagesDTO> images = nine.getImages();
                    for (HistoryByPromptIdResponse.OutputsDTO._$9DTO.ImagesDTO image : images) {
                        String filename = image.getFilename();
                        MissionOutput missionOutput = new MissionOutput();
                        missionOutput.setMissionId(mission.getMissionId());
                        missionOutput.setImageData(filename);
                        missionOutput.setStatus(ImageStatusEnum.INIT.getStatus());
                        missionOutputMapper.insert(missionOutput);
                    }
                    mission.setStatus(MissionStatusEnum.SUCCESS.getCode());
                    missionMapper.update(mission);
                    cacheService.zrem(runningMissionKey, promptId);
                } else {
                    mission.setStatus(MissionStatusEnum.FAIL.getCode());
                    missionMapper.update(mission);
                    cacheService.zrem(runningMissionKey, promptId);
                }
            }catch (Exception e){
                log.error("单个任务处理失败");
                mission.setStatus(MissionStatusEnum.FAIL.getCode());
                missionMapper.update(mission);
                cacheService.zrem(runningMissionKey, promptId);
            }
        }
        //覆盖当前机器剩余数量
        JSONObject jsonObject = JSON.parseObject(message);
        if (Objects.equals(jsonObject.getString("type"), "status")) {
            WsMessage wsMessage = JSON.parseObject(message, WsMessage.class);
            Integer queueRemaining = wsMessage.getData().getStatus().getExecInfo().getQueueRemaining();
            cacheService.zadd(CommonConstant.CLOUD_MACHINE, machineAddress, queueRemaining.doubleValue());
        }
    }
}

package org.jeecg.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.dto.HistoryByPromptIdResponse;
import org.jeecg.modules.system.dto.HttpCallContext;
import org.jeecg.modules.system.dto.HttpCallResponse;
import org.jeecg.modules.system.enums.ComfyUIAPIEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:吴金才
 * @Date:2024/1/30 16:21
 */
@Slf4j
@Service
public class ComfyUIQueryByPromptIdServiceImpl extends AbstractComfyUIService<String, String> {
    public HistoryByPromptIdResponse historyByPromptId(String serviceAddress, String promptId) {
        Map<String, Object> queryString = new HashMap<>();
        queryString.put("promptId", promptId);
        String result = super.doInvoke(serviceAddress, promptId, queryString);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject promptDetail = jsonObject.getJSONObject(promptId);
        HistoryByPromptIdResponse historyByPromptIdResponse = JSON.parseObject(JSON.toJSONString(promptDetail), HistoryByPromptIdResponse.class);
        return historyByPromptIdResponse;
    }

    @Override
    protected HttpCallContext<String> transform(String serviceAddress, String promptId, Map<String, Object> queryString) {
        return super.buildHttpCallContext(serviceAddress, ComfyUIAPIEnum.HISTORY, null, queryString);
    }

    @Override
    protected String aggregate(HttpCallResponse httpCallResponse) {
        return super.parseResponse(httpCallResponse);
    }
}

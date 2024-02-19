package org.jeecg.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.dto.HttpCallContext;
import org.jeecg.modules.system.dto.HttpCallResponse;
import org.jeecg.modules.system.dto.QueuePromptResponse;
import org.jeecg.modules.system.enums.ComfyUIAPIEnum;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author:吴金才
 * @Date:2024/1/30 16:21
 */
@Slf4j
@Service
public class ComfyUIPromptServiceImpl extends AbstractComfyUIService<String, QueuePromptResponse> {
    public QueuePromptResponse prompt(String serviceAddress, String prompt) {
        return super.doInvoke(serviceAddress, prompt,null);
    }

    @Override
    public HttpCallContext<String> transform(String serviceAddress, String requestBody, Map<String, Object> queryString) {
        return super.buildHttpCallContext(serviceAddress, ComfyUIAPIEnum.PROMPT, requestBody, queryString);
    }

    @Override
    protected QueuePromptResponse aggregate(HttpCallResponse httpCallResponse) {
        QueuePromptResponse promptResponse = super.parseResponse(httpCallResponse, QueuePromptResponse.class);
        return promptResponse;
    }
}

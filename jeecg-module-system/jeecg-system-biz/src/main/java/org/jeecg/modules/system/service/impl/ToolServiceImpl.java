package org.jeecg.modules.system.service.impl;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.common.PageSearchDTO;
import org.jeecg.modules.system.dto.tool.ToolAddInDTO;
import org.jeecg.modules.system.dto.tool.ToolQueryInDTO;
import org.jeecg.modules.system.dto.tool.ToolQueryOutDTO;
import org.jeecg.modules.system.entity.Tool;
import org.jeecg.modules.system.entity.ToolInfo;
import org.jeecg.modules.system.entity.ToolTemplate;
import org.jeecg.modules.system.mapper.ToolMapper;
import org.jeecg.modules.system.mapper.ToolTemplateMapper;
import org.jeecg.modules.system.service.ToolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/7 13:42
 */
@Service
@Slf4j
public class ToolServiceImpl implements ToolService {
    @Resource
    private ToolMapper toolMapper;
    @Resource
    private ToolTemplateMapper toolTemplateMapper;
    private static final Integer DEFAULT_VERSION = 1;

    @Override
    @Transactional
    public boolean addTool(ToolAddInDTO inDTO) {
        String toolCode = UUID.randomUUID().toString();
        toolCode = toolCode.replace("-", "");
        Tool tool = Tool.builder()
                .version(DEFAULT_VERSION)
                .toolCode(toolCode)
                .toolName(inDTO.getToolName())
                .logo(inDTO.getLogo())
                .isEnable(inDTO.getEnable())
                .description(inDTO.getDescription())
                .build();
        int toolSaveResult = toolMapper.insert(tool);

        ToolTemplate toolTemplate = ToolTemplate.builder()
                .toolCode(toolCode)
                .version(DEFAULT_VERSION)
                .templateContent(inDTO.getTemplateContent())
                .build();
        int toolTmpSaveResult = toolTemplateMapper.insert(toolTemplate);
        return toolSaveResult > 0 && toolTmpSaveResult > 0;
    }

    @Override
    public boolean updateTool(ToolAddInDTO inDTO) {
        String toolCode = inDTO.getToolCode();
        Integer version = inDTO.getVersion();
        Tool tool = toolMapper.findByToolCode(toolCode, version);
        int newVersion = inDTO.getVersion() + 1;
        tool.setVersion(newVersion);
        tool.setToolName(inDTO.getToolName());
        tool.setLogo(inDTO.getLogo());
        tool.setIsEnable(inDTO.getEnable());
        tool.setDescription(inDTO.getDescription());
        int toolUpdateResult = toolMapper.update(tool);

        ToolTemplate toolTemplate = ToolTemplate.builder()
                .toolCode(toolCode)
                .version(newVersion)
                .templateContent(inDTO.getTemplateContent())
                .build();
        int toolTmpSaveResult = toolTemplateMapper.insert(toolTemplate);
        return toolUpdateResult > 0 && toolTmpSaveResult > 0;
    }

    @Override
    public PR findAll(PageSearchDTO inDTO) {
        long total = toolMapper.count();
        List<ToolInfo> result = toolMapper.findAll(inDTO.getOffset(), inDTO.getSize());
        if (CollectionUtils.isEmpty(result)) {
            return PR.ofEmpty(total);
        }
        List<ToolQueryOutDTO> dtOs = convertToolInfo2ToolQueryOutDTOList(result);
        return PR.ok(dtOs, total);
    }

    @Override
    public ToolQueryOutDTO findDetail(ToolQueryInDTO toolQueryInDTO) {
        ToolInfo detail = toolMapper.findDetail(toolQueryInDTO.getToolCode(), toolQueryInDTO.getVersion());
        return convertToolInfo2ToolQueryOutDTO(detail);
    }

    private List<ToolQueryOutDTO> convertToolInfo2ToolQueryOutDTOList(List<ToolInfo> toolInfoList) {
        List<ToolQueryOutDTO> toolQueryOutDTOList = new ArrayList<>();
        for (ToolInfo toolInfo : toolInfoList) {
            ToolQueryOutDTO toolQueryOutDTO = convertToolInfo2ToolQueryOutDTO(toolInfo);
            toolQueryOutDTOList.add(toolQueryOutDTO);
        }
        return toolQueryOutDTOList;
    }

    private ToolQueryOutDTO convertToolInfo2ToolQueryOutDTO(ToolInfo toolInfo) {
        ToolQueryOutDTO toolQueryOutDTO = ToolQueryOutDTO.builder()
                .toolCode(toolInfo.getToolCode())
                .toolName(toolInfo.getToolName())
                .version(toolInfo.getVersion())
                .sort(toolInfo.getSort())
                .enable(toolInfo.getIsEnable())
                .logo(toolInfo.getLogo())
                .description(toolInfo.getDescription())
                .templateContent(toolInfo.getTemplateContent())
                .build();

        return toolQueryOutDTO;
    }

    @Override
    public boolean deleteTool(ToolQueryInDTO inDTO) {
        String toolCode = inDTO.getToolCode();
        Integer version = inDTO.getVersion();
        int delete = toolMapper.delete(toolCode, version);
        return delete > 0;
    }
}

package org.jeecg.modules.system.controller.client;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.dto.tool.ImageQueryInDTO;
import org.jeecg.modules.system.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:吴金才
 * @Date:2024/2/17 20:22
 */
@RestController
@RequestMapping("/client/img")
public class ImageController {
    @Resource
    private ImageService imageService;

    @PostMapping("/queryImg")
    public PR queryAllImg(@RequestBody ImageQueryInDTO inDTO) {
        PR resp = imageService.findAll(inDTO);
        return resp;
    }
}

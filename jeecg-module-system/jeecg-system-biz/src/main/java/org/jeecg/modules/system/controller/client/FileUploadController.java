package org.jeecg.modules.system.controller.client;

import org.jeecg.modules.system.common.R;
import org.jeecg.modules.system.service.impl.UploadImgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author:吴金才
 * @Date:2024/2/6 11:40
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    @Resource
    private UploadImgService uploadImgService;

    @PostMapping("/upload")
    public R handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        uploadImgService.uploadInputImg(file,userId);
        return R.ok();
    }
}

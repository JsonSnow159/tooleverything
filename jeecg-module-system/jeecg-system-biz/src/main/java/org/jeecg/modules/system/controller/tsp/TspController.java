package org.jeecg.modules.system.controller.tsp;

import org.jeecg.modules.system.common.R;
import org.jeecg.modules.system.service.impl.UploadImgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:吴金才
 * @Date:2024/2/1 11:01
 */
@RestController
@RequestMapping("/tsp")
public class TspController {

    @Resource
    private UploadImgService uploadImgService;

    @RequestMapping("/uploadImg")
    //用户填入参数，寻找模版
    public R generatePhoto(){
        uploadImgService.uploadOutputImg();
        return R.ok();
    }
}

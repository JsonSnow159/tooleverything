package org.jeecg.modules.system.controller.admin;

import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.common.PageSearchDTO;
import org.jeecg.modules.system.common.R;
import org.jeecg.modules.system.dto.tool.CloudMachineInDTO;
import org.jeecg.modules.system.dto.tool.CloudMachineOutDTO;
import org.jeecg.modules.system.service.CloudMachineService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:吴金才
 * @Date:2024/2/7 12:17
 */
@RestController
@RequestMapping("/admin/cloudMachine")
public class CloudMachineController {
    @Resource
    private CloudMachineService cloudMachineService;

    @RequestMapping("/add")
    //用户填入参数，寻找模版
    public R addTool(@RequestBody CloudMachineInDTO inDTO) {
        boolean addResult = cloudMachineService.add(inDTO);
        if (addResult) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    @RequestMapping("/update")
    //用户填入参数，寻找模版
    public R updateTool(@RequestBody CloudMachineInDTO inDTO) {
        boolean addResult = cloudMachineService.update(inDTO);
        if (addResult) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    /**
     * 查询全部
     *
     * @return
     */
    @PostMapping
    @RequestMapping("/findAll")
    public PR findAllTool(@RequestBody PageSearchDTO pageSearchDTO) {
        PR resp = cloudMachineService.findAll(pageSearchDTO);
        return resp;
    }

    @RequestMapping("/findDetail")
    //用户填入参数，寻找模版
    public R findDetail(@RequestBody CloudMachineInDTO inDTO) {
        CloudMachineOutDTO detail = cloudMachineService.findDetail(inDTO.getCloudMachineId());
        return R.ok(detail);
    }

    @RequestMapping("/delete")
    //用户填入参数，寻找模版
    public R delete(@RequestBody CloudMachineInDTO inDTO) {
        boolean deleteResult = cloudMachineService.delete(inDTO.getCloudMachineId());
        if (deleteResult) {
            return R.ok();
        } else {
            return R.fail();
        }
    }
}

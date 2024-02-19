package org.jeecg.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.jeecg.modules.system.entity.Image;
import org.jeecg.modules.system.entity.Mission;
import org.jeecg.modules.system.entity.MissionOutput;
import org.jeecg.modules.system.enums.ImageStatusEnum;
import org.jeecg.modules.system.mapper.ImageMapper;
import org.jeecg.modules.system.mapper.MissionMapper;
import org.jeecg.modules.system.mapper.MissionOutputMapper;
import org.jeecg.modules.system.util.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/4 15:50
 */
@Service
@Slf4j
public class UploadImgService {
    @Resource
    private MissionOutputMapper missionOutputMapper;
    @Resource
    private MissionMapper missionMapper;
    @Resource
    private ComfyUIViewServiceImpl comfyUIViewService;
    @Resource
    private MinioUtil minioUtil;
    @Value("${jeecg.minio.publicEndpoint}")
    private String publicEndpoint;
    @Value("${jeecg.minio.bucket}")
    private String bucketName;
    @Resource
    private ImageMapper imageMapper;

    public void uploadOutputImg() {
        List<MissionOutput> missionOutputs = missionOutputMapper.findAll();
        for (MissionOutput missionOutput : missionOutputs) {
            try {
                String missionId = missionOutput.getMissionId();
                Mission mission = missionMapper.findByMissionId(missionId);
                String cloudMachineIp = mission.getCloudMachineIp();
                String cloudMachinePort = mission.getCloudMachinePort();
                String cloudMachineAddress = cloudMachineIp + ":" + cloudMachinePort;
                String fileName = missionOutput.getImageData();
                String urlStr = comfyUIViewService.viewQueue(cloudMachineAddress, fileName, "output");
                URL url = new URL(urlStr);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                minioUtil.uploadFile(fileName, inputStream, ContentType.IMAGE_PNG.getMimeType());
                StringBuilder urlBuilder = new StringBuilder();
                StringBuilder imgUrlSb = urlBuilder.append(publicEndpoint).append("/").append(bucketName).append("/").append(fileName);
                missionOutput.setImageUrl(imgUrlSb.toString());
                missionOutput.setStatus(ImageStatusEnum.SUCCESS.getStatus());
                missionOutputMapper.update(missionOutput);
                //图片存放到图片库
                Image image = new Image();
                image.setUid(mission.getUid());
                image.setImageUrl(imgUrlSb.toString());
                imageMapper.insert(image);
            } catch (Exception e) {
                log.error("图片上传失败");
                missionOutput.setStatus(ImageStatusEnum.FAIL.getStatus());
                missionOutputMapper.update(missionOutput);
            }
        }
    }

    public void uploadInputImg(MultipartFile file, String userId) {
        try {
            minioUtil.uploadFile(file.getOriginalFilename(), file.getInputStream(), file.getContentType());
            Image image = new Image();
            image.setUid(userId);
            StringBuilder urlBuilder = new StringBuilder();
            StringBuilder imgUrlSb = urlBuilder.append(publicEndpoint).append("/").append(bucketName).append("/").append(file.getOriginalFilename());
            image.setImageUrl(imgUrlSb.toString());
            imageMapper.insert(image);
        } catch (Exception e) {
            log.info("图片上传失败");
        }
    }
}

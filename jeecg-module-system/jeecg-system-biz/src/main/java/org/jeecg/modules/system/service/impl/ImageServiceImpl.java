package org.jeecg.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.common.PR;
import org.jeecg.modules.system.dto.tool.ImageQueryInDTO;
import org.jeecg.modules.system.dto.tool.ImageQueryOutDTO;
import org.jeecg.modules.system.entity.Image;
import org.jeecg.modules.system.mapper.ImageMapper;
import org.jeecg.modules.system.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:吴金才
 * @Date:2024/2/17 20:25
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageMapper imageMapper;

    @Override
    public PR findAll(ImageQueryInDTO inDTO) {
        List<Image> imageList = imageMapper.findAll(inDTO.getUid());
//        Page<Tool> page = PageHelper.startPage(inDTO.getPage(), inDTO.getSize())
//                .doSelectPage(() -> toolMapper.findAll1());
//        List<Tool> result = page.getResult();
        long total = imageList.size();
        if (CollectionUtils.isEmpty(imageList)) {
            return PR.ofEmpty(total);
        }
//        return null;
        List<ImageQueryOutDTO> dtOs = convertImageList2ImageQueryOutDTOList(imageList);
        return PR.ok(dtOs, total);
    }

    private List<ImageQueryOutDTO> convertImageList2ImageQueryOutDTOList(List<Image> imageList) {
        List<ImageQueryOutDTO> imageQueryOutDTOList = new ArrayList<>();
        for (Image image : imageList) {
            ImageQueryOutDTO imageQueryOutDTO = convertImage2ImageQueryOutDTO(image);
            imageQueryOutDTOList.add(imageQueryOutDTO);
        }
        return imageQueryOutDTOList;
    }

    private ImageQueryOutDTO convertImage2ImageQueryOutDTO(Image image) {
        ImageQueryOutDTO imageQueryOutDTO = ImageQueryOutDTO.builder()
                .imageId(image.getId())
                .uid(image.getUid())
                .imageUrl(image.getImageUrl())
                .build();
        return imageQueryOutDTO;
    }
}

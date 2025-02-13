package org.jeecg.modules.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author:吴金才
 * @Date:2024/2/1 11:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "工具模版对象", description = "工具模版维护")
public class ToolTemplate extends BaseEntity{
    @ApiModelProperty(value = "工具类型")
    private String toolCode;
    @ApiModelProperty(value = "版本")
    private Integer version;
    @ApiModelProperty(value = "模版内容")
    private String templateContent;
}

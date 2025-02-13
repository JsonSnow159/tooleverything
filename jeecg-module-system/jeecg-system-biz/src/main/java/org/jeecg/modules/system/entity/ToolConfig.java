package org.jeecg.modules.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author:吴金才
 * @Date:2024/2/1 11:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "工具配置对象", description = "工具配置维护")
public class ToolConfig extends BaseEntity{
    @ApiModelProperty(value = "工具类型")
    private String toolCode;
    @ApiModelProperty(value = "版本")
    private Integer version;
    @ApiModelProperty(value = "参数id")
    private String paramId;
    @ApiModelProperty(value = "参数名称")
    private String paramName;
    @ApiModelProperty(value = "参数类型")
    private String paramType;
}

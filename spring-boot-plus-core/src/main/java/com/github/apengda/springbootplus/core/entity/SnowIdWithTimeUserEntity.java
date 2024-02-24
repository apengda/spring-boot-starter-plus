package com.github.apengda.springbootplus.core.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SnowIdWithTimeUserEntity extends SnowIdWithTimeEntity implements WithUser {
    @Schema(description = "创建者id")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String creatorId;

    @Schema(description = "创建者姓名")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String creatorNick;

    @Schema(description = "更新者id")
    private String updatorId;

    @Schema(description = "更新者姓名")
    private String updatorNick;
}

package com.github.apengda.springwebplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.apengda.springwebplus.starter.entity.SnowIdWithTimeUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@TableName("sys_user")
@Schema(description = "系统用户")
public class SysUser extends SnowIdWithTimeUserEntity {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(max = 32, message = "用户名长度超过限制")
    private String username;

    @Schema(description = "昵称")
    @Length(max = 32, message = "密码长度超过限制")
    private String nickname;

    @Schema(description = "密码")
    @Length(max = 64, message = "密码长度超过限制")
    private String password;

    @Schema(description = "盐值")
    @Length(max = 16, message = "长度超过限制")
    private String salt;

    @Schema(description = "手机号码")
    @Length(max = 32, message = "手机号长度超过限制")
    private String phone;

    @Schema(description = "电子邮件")
    @Length(max = 64, message = "邮件长度超过限制")
    private String email;

    @Schema(description = "头像")
    @Length(max = 16, message = "密码长度超过限制")
    private String head;

    @Schema(description = "状态，0：禁用，1：启用")
    private Boolean enabled;

    @Schema(description = "角色id")
    private Long roleId;
}

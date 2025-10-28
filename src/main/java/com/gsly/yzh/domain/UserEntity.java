package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("USERS")
public class UserEntity extends BaseEntity {

    /**
     * 班级id
     */
    @TableField
    private Long classId;

    /**
     * 用户名
     */
    @TableField
    private String username;

    /**
     * 密码
     */
    @TableField
    private String password;

    /**
     * 角色
     */
    @TableField
    private Integer role;

    /**
     * 昵称
     */
    @TableField
    private String nickname;

    /**
     * 电话
     */
    @TableField
    private String phone;

    /**
     * 邮箱
     */
    @TableField
    private String email;

    /**
     * 状态（启用，禁用）
     */
    @TableField
    private Integer status;
}

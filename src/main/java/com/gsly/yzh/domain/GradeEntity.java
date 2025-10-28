package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("GRADE")
public class GradeEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 学院
     */
    @TableField
    private String college;

    /**
     * 年级
     */
    @TableField
    private String gradeName;

    /**
     * 班级
     */
    @TableField
    private String className;
}

package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsly.yzh.domain.UserEntity;
import lombok.Data;

@Data
public class UserReqDTO extends Page<UserEntity> {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 1启用，0禁用
     */
    private Integer status;
}

package com.gsly.yzh.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class JwtUser {
    //是否有效
    private boolean valid;
    //用户id
    private String userId;
    //角色id
    private String roleId;

    public JwtUser() {
        this.valid = false;
    }
}

package com.saveslave.sysmanager.service.model.query;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: Haocj
 * @time: 2025/2/12
 */
@Data
@Accessors(chain = true)
public class LoginBody {

    private String userName;
    private String password;
}

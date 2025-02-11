package com.saveslave.sysmanager.sdk.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pId;

    private String roleCode;

    private String roleName;

    private Integer roleType;

    private Long creatorId;

    private Long updaterId;

    private String remark;

    List<ResourceInfo> resourceInfoList;

}

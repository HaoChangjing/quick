package com.saveslave.sysmanager.sdk.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO
 * @ClassName ResourceInfo
 * @Author admin
 * @Date 2019/7/15
 * @ModDate 2019/7/15
 * @ModUser admin
 * @Version V1.0
 */
@Data
@Accessors(chain = true)
public class ResourceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pId;

    private String resourceCode;

    private String resourceName;

    private String parentCode;

    private Integer resourceLevel;

    private String resourceUrl;

    private String resourceType;

    private String resourceId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    private long creatorId;

    private long updaterId;

    private String clientCode;

    private String remark;

    private String icon;

    private Integer sort;

}

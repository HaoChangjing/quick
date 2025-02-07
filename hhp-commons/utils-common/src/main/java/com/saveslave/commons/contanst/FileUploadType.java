package com.saveslave.commons.contanst;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件上传类型
 *
 */
@Getter
@AllArgsConstructor
public enum FileUploadType {

    /***/
    TENCENT_UPLOAD("TENCENT","腾讯文件上传"),
    FASTDFS_UPLOAD("FASTDFS","fastDfs文件上传"),
    OSS_UPLOAD("OSS","阿里云OSS文件上传"),
    MINIO_UPLOAD("MINIO","MINIO文件上传"),
    OBS_UPLOAD("OBS","华为云OBS文件上传");

    private final String uploadType;
    private final String uploadTypeName;

}

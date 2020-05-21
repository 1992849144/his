package org.java.upload.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类，用于封装上传的结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {

    private Integer code;  //0:上传成功，1：上传失败
    private String msg;//消息内容
    private String data;//图片上传以后的保存路径
}

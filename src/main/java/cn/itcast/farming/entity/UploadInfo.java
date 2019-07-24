package cn.itcast.farming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传请求信息
 */
@Data
@AllArgsConstructor
public class UploadInfo {


    /**
     *  上传目标存储源，如七牛云、阿里云、hdfs本地实现等
     */
    private String origin;


    /**
     * 上传后资源标识
     */
    private String resourceKey;

    /**
     * 正反面
     */
    private String flag;

    /**
     * 上传凭证
     */
    private String uploadToken;

    /**
     * 上传文件内容
     */
    private MultipartFile file;


}

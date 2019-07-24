package cn.itcast.farming.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("fileobject")
public class FileObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件标识
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String fileId;

    /**
     * 原文件名
     */
    @TableField("fileName")
    private String fileName;


    /**
     * 存储源，如七牛云、阿里云、hdfs本地实现等，可扩展
     */
    @TableField("origin")
    private String origin;


    /**
     * 上传资源标识
     */
    @TableField("resourceKey")
    private String resourceKey;

    /**
     * 正反面
     */
    @TableField("flag")
    private String flag; //取值front或back


    /**
     * 下载路径
     */
    @TableField("downloadUrl")
    private String downloadUrl;

    /**
     * 是否受保护的资源，决定客户端直接通过url下载，还是需要在文件服务生成下载url
     */
    @TableField("isprotect")
    private boolean isProtect;

    /**
     * 上传日期
     */
    @TableField("uploadDate")
    private LocalDateTime uploadDate;

}

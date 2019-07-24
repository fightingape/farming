package cn.itcast.farming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 存储空间
 */
@Data
@AllArgsConstructor
public class Bucket {

    /**
     * 存储源，如七牛云、阿里云、hdfs本地实现等，可扩展
     */
    private String origin;

    /**
     * 空间名称
     */
    private String name;

    /**
     * 是否受保护的资源，如果是受保护资源，则必须使用凭证下载
     */
    private boolean protect;


}

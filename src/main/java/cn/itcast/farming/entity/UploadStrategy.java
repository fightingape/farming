package cn.itcast.farming.entity;

import lombok.Data;

/**
 * 上传策略
 * 会被转化为上传凭证
 */
@Data
public class UploadStrategy {

    /**
     * 指定上传的目标资源空间 Bucket 和资源键 Key
     * <bucket>，表示允许用户上传文件到指定的 bucket。key由用户指定，若不指定则随机生成。指定insertOnly为1为新增，若新增时已存在同名资源，上传会失败。insertOnly为0时，没有同名资源则新增，有则覆盖。
     * <bucket>:<key>，表示只允许用户上传指定 key 的文件。用户指定指定的key必须与此key吻合。指定insertOnly为1为新增，若新增时已存在同名资源，上传会失败。insertOnly为0时，没有同名资源则新增，有则覆盖。
     * <bucket>:<keyPrefix>，表示只允许用户上传指定以 keyPrefix 为前缀的文件，当且仅当 isPrefixalScope 字段为 1 时生效，key由用户指定，若不指定则随机生成。在这种格式下指定insertOnly为1为新增，若新增时已存在同名资源，上传会失败。insertOnly为0时，没有同名资源则新增，有则覆盖。
     */
    private String scope;

    /**
     * 限定为新增语意。默认为0，如果设置为非 0 值，则无论 scope 设置为什么形式，仅能以新增模式上传文件。
     */
    private String insertOnly;

    /**
     * 默认为0，若为 1，表示允许用户上传以 scope 的 keyPrefix 为前缀的文件。
     */
    private String isPrefixalScope;

    /**
     * 上传凭证有效截止时间，Unix时间戳，单位为秒，一般建议设置为上传开始时间 + 3600s
     */
    private Long deadline;


}

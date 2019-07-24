package cn.itcast.farming.service;

import cn.itcast.farming.common.domain.RestResponse;
import cn.itcast.farming.entity.FileObject;
import cn.itcast.farming.entity.UploadInfo;
import cn.itcast.farming.entity.UploadStrategy;
import cn.itcast.farming.handler.StorageHandler;
import cn.itcast.farming.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**文件上传
 * 客户端调用后端业务上传
 * 1.客户端调用后端业务进行文件上传
 * 2.1.后端业务调用"文件服务"接口获取上传凭证
 * 2.2.后端业务调用第三方云服务进行文件上传
 * 客户端一次性调用，并且不需要了解第三方云服务差异，但文件流都需要经过业务及文件服务
 *
 * --------------------------------------------------------------------------
 * 客户端调用后端业务获取上传凭证，而后直接调用上传服务进行上传
 * 1.客户端调用上传业务获取上传凭证，上传业务调用"文件服务"接口获取上传凭证(由上传业务控制访问，接口均l)
 * 2.客户端调用"文件服务"接口进行文件上传
 * 客户端不需要了解第三方云服务差异，但文件流都需要经过文件服务
 *
 *
 * 后端通过"文件服务"接口上传至第三方云服务
 * 1.后端生成上传凭证
 * 2.后端上传至第三方云服务
 * 用于生成文件等业务
 */


/**文件下载
 *
 *  客户端直接调用第三方云服务下载
 *  1.客户端调用下载业务获取下载凭证，下载业务调用"文件服务"接口获取下载凭证(由下载业务控制访问，接口为l)
 *  2.客户端直接通过第三方云服务下载文件
 *  客户端需要了解第三方云服务差异
 *
 *  后端通过"文件服务"下载
 * 1.后端生成下载凭证
 * 2.后端从第三方云服务下载文件
 *
 */

@Slf4j
@Service
public class FileService {

    @Autowired
    private StorageHandler storageHandler;

    @Autowired
    private Map<String, StorageHandler> storageHandlerMap;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 生成(上传、下载、管理)凭证
     * @param uploadStrategy 存储源 如七牛云、阿里云、hdfs本地实现等
     * @param params 生成所需的参数，如七牛云需要指定生成的凭证类型(上传、下载、管理)
     * @return
     */
    public String generateToken(String origin, UploadStrategy uploadStrategy, Map<String, Object> params) {
        return storageHandler.generateToken(uploadStrategy,params);
    }

    /**
     * 文件上传
     *
     * @param uploadInfo 上传的文件信息
     * @return 文件的存储信息
     */
    @Transactional
    public FileObject upload(UploadInfo uploadInfo) {
        FileObject fileObject=storageHandler.upload(uploadInfo);
        fileMapper.insert(fileObject);
        return fileObject;
    }


    /**
     * 建议用于后端业务批量生成下载url
     * @param fileKeys 文件标识列表
     * @return Map key为fileKey，value为文件下载url
     */
    public Map<String, String> generateDownloadUrls(String[] fileKeys) throws Exception{
        return storageHandler.generateDownloadUrls(fileKeys);
    }


    //管理接口，建议尽在后端业务使用，目前仅实现删除
    public RestResponse<String> deleteFiles(String[] fileKeys){
        return storageHandler.deleteFiles(fileKeys);
    }

}

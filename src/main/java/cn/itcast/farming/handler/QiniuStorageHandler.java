package cn.itcast.farming.handler;


import cn.itcast.farming.common.domain.RestResponse;
import cn.itcast.farming.entity.FileObject;
import cn.itcast.farming.entity.UploadInfo;
import cn.itcast.farming.entity.UploadStrategy;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 七牛云存储处理器
 */
@Component("qiniu")
@Slf4j
public class QiniuStorageHandler implements StorageHandler {

    @Value("${my.file.qiniu.accessKey}")
    private String accessKey;
    @Value("${my.file.qiniu.secretKey}")
    private String secretKey;
    @Value("${my.file.qiniu.domainOfBucket}")
    private String domainOfBucket;

    @Override
    public String generateToken(UploadStrategy uploadStrategy, Map<String, Object> params) {
        if (params.get("tokenType").equals("1")) { //1表示获得上传凭证
            Auth auth = Auth.create(accessKey, secretKey);
            StringMap putPolicy = new StringMap();
            putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fname\":\"$(fname)\",\"fsize\":$(fsize)}");
            putPolicy.put("returnBodyType", "application/json");
            String upToken = auth.uploadToken(uploadStrategy.getScope(), null, uploadStrategy.getDeadline(), putPolicy, false);
            return upToken;
        } else if (params.get("tokenType").equals("2")) { //2表示获得下载凭证
            //TODO 申请获得七牛云的下载凭证
            return null;

        } else if (params.get("tokenType").equals("3")) { //3表示获得管理凭证
            //TODO 申请获得七牛云的管理凭证
            return null;
        }
        return null;
    }

    @Override
    public FileObject upload(UploadInfo uploadInfo) {
        MultipartFile file = uploadInfo.getFile();
        String origin = uploadInfo.getOrigin();
        String uploadToken = uploadInfo.getUploadToken();
        String flag=uploadInfo.getFlag();
        //String resourceKey=uploadInfo.getResourceKey();

            try {
                //构造一个带指定Zone对象的配置类
                Configuration cfg = new Configuration(Zone.zone1());
                //其他参数参考类注释
                UploadManager uploadManager = new UploadManager(cfg);

                Response file_response = uploadManager.put(file.getInputStream(), null, uploadToken, null, null);
                //解析上传成功的结果
                DefaultPutRet file_putRet = new Gson().fromJson(file_response.bodyString(), DefaultPutRet.class);
                if (file_response.statusCode != 200) {
                    return null;
                }
                FileObject fileObject = new FileObject();
                fileObject.setFileName(file.getOriginalFilename());
                fileObject.setOrigin(origin);
                fileObject.setResourceKey(file_putRet.key);
                fileObject.setFlag(flag);
                fileObject.setProtect(true);
                fileObject.setUploadDate(LocalDateTime.now());
                //TODO fileObject.setDownloadUrl("");

                return fileObject;
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                return null;
            }

    }

    @Override
    public Map<String, String> generateDownloadUrls(String[] fileKeys) throws  Exception{
        HashMap<String,String> urlMap=new HashMap<>();
        for(String fileKey:fileKeys){
            String fileName = fileKey;
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            Auth auth = Auth.create(accessKey, secretKey);
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            urlMap.put(fileKey,finalUrl);
        }
        return urlMap;
    }

    @Override
    public RestResponse<String> deleteFiles(String[] fileKeys) {
        /*Configuration cfg = new Configuration(Zone.zone1());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, fileKeys);
            Response response = bucketManager.batch(batchOperations);
            return RestResponse.success("删除成功");
        } catch (QiniuException ex) {
            return RestResponse.success("删除失败："+ex.getMessage());
        }*/
        return null;
    }


}

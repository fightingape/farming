package cn.itcast.farming.controller;

import cn.itcast.farming.common.domain.RestResponse;
import cn.itcast.farming.entity.FileObject;
import cn.itcast.farming.entity.UploadInfo;
import cn.itcast.farming.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "文件上传接口，暴露给后端业务与客户端")
@RestController
public class UploadController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value="上传文件", notes="上传文件")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "origin", value = "存储源，如七牛云、阿里云、hdfs本地实现等，可扩展", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "resourceKey", value = "资源标识", required = false, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "flag", value = "正反面", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "uploadToken", value = "上传凭证", required = true, dataType = "String", paramType="query")
    })
    @CrossOrigin
    public RestResponse<FileObject> uploadFile(@RequestParam("origin") String origin,
                                               @RequestParam("resourceKey") String resourceKey,
                                               @RequestParam("uploadToken") String uploadToken,
                                               @RequestParam("flag") String flag,
                                               @RequestParam("file") MultipartFile file){

        FileObject fileObject=fileService.upload(new UploadInfo(origin,resourceKey,flag,uploadToken,file));
        RestResponse<FileObject> restResponse= RestResponse.success(fileObject);
        restResponse.setMsg("上传成功！");
        return restResponse;
    }


}

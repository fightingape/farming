package cn.itcast.farming.controller;

import cn.itcast.farming.common.domain.RestResponse;
import cn.itcast.farming.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "管理接口接口，仅暴露给后端业务")
@RestController
public class ManagementController {


    @Autowired
    private FileService fileService;


    @ApiOperation(value="后端业务批量生成下载url", notes="后端业务批量生成下载url")
    @ApiImplicitParam(name = "fileKeys", value = "文件资源key，逗号分隔", required = true, dataType = "String", paramType="query")
    @PostMapping(value = "/generatedownloadurls")
    public RestResponse<Map<String, String>> downloadUrl(@RequestParam("fileKeys") String[] fileKeys) throws Exception{
        Map<String, String> urlMap = fileService.generateDownloadUrls(fileKeys);
        return RestResponse.success(urlMap);
    }


    @ApiOperation(value="后端业务批量删除文件", notes="后端业务批量删除文件")
    @ApiImplicitParam(name = "fileKeys", value = "文件key列表，逗号分隔", required = true, dataType = "String", paramType="query")
    @PostMapping(value = "/deleteFiles")
    public RestResponse<String> deleteFiles(@RequestParam("fileKeys") String[] fileKeys){
        return fileService.deleteFiles(fileKeys);
    }

}

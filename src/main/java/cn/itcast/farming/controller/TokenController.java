package cn.itcast.farming.controller;


import cn.itcast.farming.common.domain.RestResponse;
import cn.itcast.farming.entity.UploadStrategy;
import cn.itcast.farming.service.FileService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "获取令牌接口，仅暴露给后端业务")
@RestController
public class TokenController {


    @Autowired
    private FileService fileService;


    @ApiOperation(value="申请获得凭证", notes="申请获得凭证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "origin", value = "存储源，如七牛云、阿里云、hdfs本地实现等，可扩展", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "params", value = "生成所需的参数，json格式，包括UploadStrategy，同时包含各存储源的特殊化参数，如七牛云需要指定生成的凭证类型(上传、下载、管理)", required = true, paramType="body")
    })
    @PostMapping(value = "/l/generatetoken")
    public RestResponse<String> generateToken(@RequestParam("origin")String origin,
                                              @RequestBody Map<String,Object> params){
        UploadStrategy uploadStrategy = convertParams2UploadStrategy(params);
        String token = fileService.generateToken(origin,uploadStrategy, params);
        return RestResponse.success(token);
    }

    private UploadStrategy convertParams2UploadStrategy(Map<String, Object> params) {
        //TODO: 参数校验，更详细的写法
        String json = JSON.toJSONString(params);
        UploadStrategy uploadStrategy = JSONObject.parseObject(json,UploadStrategy.class);
        return uploadStrategy;
    }


}

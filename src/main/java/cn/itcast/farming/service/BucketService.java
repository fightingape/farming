package cn.itcast.farming.service;

import cn.itcast.farming.entity.Bucket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储空间管理
 * origin为存储源，如七牛云、阿里云、hdfs本地实现等，可扩展
 */
@Service
public class BucketService {

    /**
     * 一级key为origin，二级key为存储空间名称
     */
    public static Map<String, Map<String, Bucket>> bucketMap = new HashMap<>();

    public static List<Bucket> bucketList = new ArrayList<>();

    /**
     * 存储源
     */
    public static final String ORIGIN_TYPE = "qiniu";

    public BucketService() {

        bucketList.add(new Bucket(ORIGIN_TYPE,"bucket1",true));
        bucketList.add(new Bucket(ORIGIN_TYPE,"bucket2",false));
        //TODO: 可考虑从数据库或者配置文件初始化,或直接调用各存储源的API获取

        for(Bucket bucket : bucketList){
            if(!bucketMap.containsKey(bucket.getOrigin())){
                bucketMap.put(bucket.getOrigin(), new HashMap<>());
            }
            bucketMap.get(bucket.getOrigin()).put(bucket.getName(),bucket);
        }

    }


    /**
     * 根据存储来源与存储空间名称查找存储空间对象
     * @param origin
     * @param name
     * @return
     */
    public Bucket getBucket(String origin, String name){
        if(!bucketMap.containsKey(origin)){
            return null;
        }
        return bucketMap.get(origin).get(name);
    }


}

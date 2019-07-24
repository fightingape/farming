package cn.itcast.farming.mapper;

import cn.itcast.farming.entity.FileObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author zhangdongxu
 * @since 2019-06-27
 */
@Repository
public interface FileMapper extends BaseMapper<FileObject> {


}

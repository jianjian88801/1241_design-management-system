package com.xunmaw.design.dao;

import com.xunmaw.design.domain.Topic;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author chenchengjian
 * @date 2022/2/25 16:45
 * Description:
 */
@Repository
public interface TopicMapper extends Mapper<Topic> {
}

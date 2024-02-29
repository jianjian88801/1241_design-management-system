package com.xunmaw.design.dao;

import com.xunmaw.design.domain.Student;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * @date 2022/1/21 20:58
 * Description:
 */
@Repository
public interface StudentMapper extends Mapper<Student> {
}

package com.xunmaw.design.service;

import com.github.pagehelper.Page;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/3/19 16:40
 * Description: 系部管理员Service
 */
public interface DeptManagerService {
    List<Student> findAllStudents(Administrator user);
    Page<Student> searchAllStudent(Map searchMap, Administrator user);

    List<Major> getMajorsByDep(Administrator user);

    List<Class> getAClassByMajorId(Administrator user, String id, Integer session);

    Boolean delStudent(List<Integer> ids);

    Boolean updateStuStatus(Integer status, Integer id);

    Boolean ResetStuPassword(Integer id);


    Page<Topic> searchAllTopic(Map searchMap, Administrator user);

    Page<Teacher> searchAllTeacher(Map searchMap, Administrator user);

    boolean addTeacher(Teacher teacher, Administrator user);

    Page<Major> searchAllMajors(Map searchMap,Administrator user);

    Page<Administrator> searchAllMajorAdministrator(Map searchMap, Administrator user);

    boolean addStudent(Student student, Administrator user);

    Page<Topic> searchAllOKTopic(Map searchMap, Administrator user);

    Page<Topic> searchStudentsFile(Map searchMap, Administrator user);
}

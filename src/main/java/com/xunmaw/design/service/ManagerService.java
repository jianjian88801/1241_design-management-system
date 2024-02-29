package com.xunmaw.design.service;

import com.github.pagehelper.Page;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/1/21 20:45
 * Description:
 */
public interface ManagerService {

    public List<Student> findAllStudents();

    public List<Teacher> findAllTeachers();

    List<Topic> findAllTopics();

    public Page<Student> searchAllStudent(Map searchMap);

    public Page<Teacher> searchAllTeacher(Map searchMap);

    Page<Depart> searchAllDept(Map searchMap);


    Student findStuById(Integer id);

    List<Depart> searchAllDeptToSelect();

    List<Major> getMajorsByDepId(Integer departId);

    List<Class> getAClassByMajorId(String id,Integer session);

    boolean addStudent(Student student);

    Boolean updateStudent(Student student);

    Boolean delStudent(List<Integer> ids);

    Boolean updateStuStatus(Integer status, Integer id);

    Boolean ResetStuPassword(Integer id);

    Page<Topic> searchAllTopic(Map searchMap);

    Teacher findTeaById(Integer id);

    Boolean delTeacher(List<Integer> ids);


    Boolean ResetTeaPassword(Integer id);

    Boolean updateTeaStatus(Integer status, Integer id);

    Topic findTopicById(Integer id);

    boolean UpdateTopic(Topic topic);

    boolean addTeacher(Teacher teacher);

    List<Major> findGuideMajorsByTeaId(Integer id);

    Boolean updateTeacher(Teacher teacher);

    Page<Administrator> searchAllDeptAdministrator(Map searchMap);

    Page<Administrator> searchAllMajorAdministrator(Map searchMap);

    Boolean ResetDeptManagerPassword(Integer id);

    Page<Major> searchAllMajors(Map searchMap);


    void importStu(MultipartFile studentExcel) throws IOException, InvalidFormatException;

    void importTea(MultipartFile teacherExcel) throws IOException, InvalidFormatException;

    Boolean ManagerResetPassword(Map searchMap, Administrator user);

    List<Student> findAllComStudents(HttpServletRequest request);

    List<Teacher> findComTeachers(HttpServletRequest request);

    List<Choose> findTopicChooseById(Integer id);

    Page<Topic> searchStudentsFile(Map searchMap, Administrator user);

    List<Major> getAllMajor();

    Page<Topic> searchAllOKTopic(Map searchMap);

    void setSystemConfig(String grade,String note, Date startTime,Date EndTime);

    List<Time_Config> getAllConfig(String grade);
}

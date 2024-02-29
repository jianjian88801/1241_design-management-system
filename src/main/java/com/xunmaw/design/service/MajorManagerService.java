package com.xunmaw.design.service;

import com.github.pagehelper.Page;
import com.xunmaw.design.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/4/8 17:33
 * Description:
 */
public interface MajorManagerService {
    Page<Topic_Major> searchNotReviewedTopic(Map searchMap, Administrator user);

    Topic_Major findTopicMajorById(Integer id, Administrator user);

    Boolean UpdateTopicMajorInfo(Topic_Major topic_major, Administrator user);

    Page<Student> searchMajorUnacceptedStudents(Map searchMap, Administrator user);

    Page<Topic> searchAllOKTopic(Map searchMap, Administrator user);

    Page<Topic> searchAllTopic(Map searchMap, Administrator user);

    List<Teacher> getTeacherInMajor(Administrator user);

    List<Topic> getTopicByTeacher(Integer id, Administrator user);

    Boolean ManuallyAssignTopic(String stuId, String teaId, String topicId,Administrator user);

    List<Choose> findStuWishTopicById(Integer id);

    Boolean CancelStudentAdmission(Integer topic_id, Administrator user);

    Boolean EmailRemindStudent(Administrator user);

    List<Choose> GetRandomlyAssigned(Administrator user);

    Boolean SubmitRandomAssignment(List<Choose> choosese,Administrator user);

    Page<Teacher> searchAllTeacherTarget(Map searchMap, Administrator user);

    TeaTarget findTeaTargetById(Map tea_target, Administrator user);

    Boolean UpdateTeaTargetById(Map tea_target, Administrator user);

    Page<Student> searchAllStudent(Map searchMap,Administrator user);

    List<Teacher> searchAllTeacherNum( Administrator user);

    List<OpenAnswer> GetOpenAnswerAllocationResults(Administrator user, Map openParamMap);

    Boolean SubmitOpenAnswerResult(List<OpenAnswer> openAnswerList, Administrator user);

    Page<OpenAnswer> searchMajorAllOpenAnswer(Map searchMap, Administrator user);

    List<Teacher> findOpenAnswerTeaList(Administrator user, Integer openAnswerId);

    List<Student> findOpenAnswerStuList(Administrator user, Integer id);

    OpenAnswer findOpenAnswerById(Administrator user, Integer id);

    Boolean delOpenAnswerTeaById(Integer id);

    List<Teacher> getUndistributedTea(Administrator user);

    Boolean addTeaToOpenAnswerGroup(Integer id, Integer open_answer_id,Administrator user);

    Boolean delOpenAnswerStuById(Integer id);

    List<Student> getUndistributedStu(Administrator user);

    Boolean addStuToOpenAnswerGroup(Integer id, Integer open_answer_id, Administrator user);

    Boolean SaveOpenAnswer(Integer id, OpenAnswer openAnswer, Administrator user);

    Boolean dismissOpenAnswerGroupById(Integer id, Administrator user);

    Page<Guidance> searchMajorTeaGuidanceRecord(Map searchMap, Administrator user);

    List<GuidanceRecord> searchGuidanceDetails(Integer id, Administrator user);
}

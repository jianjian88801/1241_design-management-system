package com.xunmaw.design.service;

import com.github.pagehelper.Page;
import com.xunmaw.design.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/4/3 14:33
 * Description:
 */
public interface TeacherService {
    List<Major> getTeacherMajors(Teacher user);

    List<Major> getMajorsByDep(Teacher user);

    Page<Topic> searchAllTopic(Map searchMap, Teacher user);

    boolean CreateTopic(Topic topic, Teacher user, List<Integer> majors);

    Page<Topic> searchMyTopic(Map searchMap, Teacher user);

    Teacher findMyInfo(Teacher user);

    Boolean TeacherResetPassword(Map searchMap, Teacher user);

    List<Topic_Major> getTopicMajorById(Integer id);

    Boolean deleteTopicMajorById(Integer id);

    Boolean SelectStudentById(Integer id, Teacher user);

    Boolean CancelStudentAdmission(Integer id, Teacher user);

    Boolean deleteTopicById(Integer id);

    Page<Topic> searchMyStudents(Map searchMap,Teacher user);

    Page<Topic> searchStudentsFile(Map searchMap, Teacher user);

    boolean findStudentChooseStatus(Integer id);

    Boolean RemindMyStuById(Integer stuId, Teacher user);

    List<LogNote> searchMyLogNotes(Teacher user);

    Page<Guidance> searchMyGuidanceRecord(Map searchMap, Teacher user);

    List<GuidanceRecord> searchGuidanceDetails(Integer id, Teacher user);

    Boolean SaveGuidanceLog(Map guidanceMap, Teacher user);

    TeaTarget searchMyTargetData(Teacher user);

    Assignment_book findStuAssignmentBookById(Integer id);

    Boolean UpdateStuAssBook(Assignment_book assignment_book);

    Opening_report findOpeningReportById(Integer id);

    Boolean UpdateStuOpenReport(Opening_report opening_report);

    MidTermExam findMidTermExamById(Integer id);

    Boolean UpdateMidTermExam(MidTermExam midTermExam);

    OpenAnswerTea getMyOpenAnswerInfo(Teacher user);


    boolean SubmitOpenAnswerAddress(String address, Teacher user);

    boolean SubmitOpenAnswerTime(String time, Teacher user);

    List<Student>findOpenAnswerStuList(Map searchMap, Teacher user);

    OpenAnswerStu GetStuOpenAnswerById(Integer id);

    List<OpenAnswerQuestion> GetStuOpenAnswerQuestionListById(Integer id);

    OpenAnswerRecord GetStuOpenAnswerRecordById(Integer id);

    Boolean SubmitStuOpenAnswerRecord(Map map, Teacher user);
}

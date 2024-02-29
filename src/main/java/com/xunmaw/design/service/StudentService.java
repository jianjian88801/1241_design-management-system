package com.xunmaw.design.service;

import com.github.pagehelper.Page;
import com.xunmaw.design.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/3/28 21:17
 * Description:
 */
public interface StudentService {
    Page<Topic> searchAllTopic(Map searchMap, Student user);

    Map<String, Object> uploadImgMap(MultipartFile multipartFile, String dir);

    List<Choose> findMyWishTopic(Student user);

    Boolean ChooseTopic(List<Integer> ids, Student user);

    Boolean CancelChooseTopic(Integer id, Student user);

    Boolean StudentResetPassword(Map searchMap, Student user);

    Assignment_book findMyAssignmentBook(Integer id, Student user);

    Boolean SaveAssignmentBook(Map searchMap, Student user);

    Opening_report findOpeningReportById(Integer id, Student user);

    Boolean SaveOpeningReport(Map searchMap, Student user);

    Page<GuidanceRecord> searchAllGuidance(Map searchMap, Student user);

    MidTermExam findMidTermExamById(Integer id, Student user);

    Boolean SaveMidTermExam(Map searchMap, Student user);

    OpenAnswerStu GetStuOpenAnswer(Student user);

    Boolean SubmitOpenAnswerQuestion(Map searchMap, Student user);

    List<OpenAnswerQuestion> GetStuOpenAnswerQuestionList(Student user);

    Boolean delOpenAnswerQuestionById(Integer id);

    OpenAnswerRecord GetStuOpenAnswerRecord(Student user);
}

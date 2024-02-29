package com.xunmaw.design.controller;

import com.github.pagehelper.Page;
import com.xunmaw.design.Util.word2vec.Segment;
import com.xunmaw.design.Util.word2vec.Word2Vec;
import com.xunmaw.design.common.MessageConstant;
import com.xunmaw.design.common.PageResult;
import com.xunmaw.design.common.Result;
import com.xunmaw.design.common.StatusCode;
import com.xunmaw.design.dao.TopicMapper;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.MajorManagerService;
import com.xunmaw.design.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/4/8 17:32
 * Description:专业管理员控制层:只负责接收前端浏览器发送的请求和请求参数，调用service层获取业务逻辑加工处理后的数据
 */

@Controller
@RestController
@RequestMapping("/MajorManager")
public class MajorManagerController {
    @Autowired
    private MajorManagerService majorManagerService;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ManagerService managerService;

    //查找未审核的选题
    @RequestMapping("/searchNotReviewedTopic")
    public PageResult searchNotReviewedTopic(@RequestBody Map searchMap, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic_Major> topic_majors = majorManagerService.searchNotReviewedTopic(searchMap,user);
        for (Topic_Major topic :
                topic_majors) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topic_majors.getResult(), topic_majors.getTotal());
    }

    //根据id查找专业选题信息
    @RequestMapping("/findTopicMajorById")
    public Result findTopicMajorById(Integer id,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("需要查找的选题Id："+id);
        Topic_Major topic_major = majorManagerService.findTopicMajorById(id,user);
        System.out.println("根据id找到的专业选题信息为😔：\n"+topic_major);
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,topic_major);
    }
    @RequestMapping("/CheckTopic")
    public String checkTopic(Integer id, HttpServletRequest request){
        Word2Vec vec = new Word2Vec();
        try {
            vec.loadGoogleModel("E:/ATools/Google_word2vec_zhwiki210720_300d.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Topic topic = (Topic) request.getSession().getAttribute("user");
        List<Topic> topics = topicMapper.selectAll();
        for (int i = 0; i < topics.size(); i++) {
            String title = topics.get(i).getTitle();
            for (int j = 1; j < topics.size(); j++) {
                String title1 = topics.get(j).getTitle();
                List<String> wordList1 = Segment.getWords(title);
                List<String> wordList2 = Segment.getWords(title1);
                System.out.println("-----句子相似度-----");
                System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2));
                float result = vec.sentenceSimilarity(wordList1, wordList2);
                if (result > 0.7) {
                    System.out.println("重复题目如下：");
                    System.out.println(title + "------" + title1);
                    return "重复题目如下：" + title + "------" + title1 + "请重新提交！";
                }
                return "查重成功！";
            }
        }
        return "";

    }
    //更新选题信息——审核选题
    @RequestMapping("/UpdateTopicMajorInfo")
    public Result UpdateTopicMajorInfo(@RequestBody Map TopicMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("所需更新的信息："+TopicMap);

        Topic_Major topic_major = new Topic_Major();
        topic_major.setId((Integer) TopicMap.get("id"));
        topic_major.setTopic_id((Integer) TopicMap.get("topic_id"));
        topic_major.setMajor_id((Integer) TopicMap.get("major_id"));
        topic_major.setStatus((Integer) TopicMap.get("status"));
        if(TopicMap.get("pbz")!=null){
            topic_major.setPbz(TopicMap.get("pbz").toString());
        }

        Boolean flag = majorManagerService.UpdateTopicMajorInfo(topic_major,user);
        return new Result(true,StatusCode.OK,"审核完成！");
    }

    //查询专业中未录取的学生
    @RequestMapping("/searchMajorUnacceptedStudents")
    public PageResult searchMajorUnacceptedStudents(@RequestBody Map searchMap, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Student> students = majorManagerService.searchMajorUnacceptedStudents(searchMap,user);
        System.out.println("该专业查询到的未被选中的学生-----------------");
        for (Student one:
             students) {
            System.out.println("----------");
            System.out.println(one);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,students.getResult(), students.getTotal());
    }

    //查找所有的录取得到选题信息
    @RequestMapping("/searchAllOKTopic")
    public PageResult searchAllOKTopic(@RequestBody Map searchMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic> topics = majorManagerService.searchAllOKTopic(searchMap,user);
        System.out.println("----------所有的被录取的选题--------");
        for (Topic topic :
                topics) {
            System.out.println(topic);
            System.out.println("--------------");
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    @RequestMapping("/searchAllTopic")
    public PageResult searchAllTopic(@RequestBody Map searchMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic> topics = majorManagerService.searchAllTopic(searchMap,user);
        for (Topic topic :
                topics) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //查找所有该专业的教师指标
    @RequestMapping("/searchAllTeacherTarget")
    public PageResult searchAllTeacherTarget(@RequestBody Map searchMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Page<Teacher> teachers = majorManagerService.searchAllTeacherTarget(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,teachers.getResult(), teachers.getTotal());
    }

    @RequestMapping("/findStuById")
    public Result findStuById(Integer id){
        Student student = managerService.findStuById(id);
        System.out.println("findStuById:\n"+student);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,student);
    }

    @RequestMapping("/getTeacherInMajor")
    public Result getTeacherInMajor(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Teacher> teachers = majorManagerService.getTeacherInMajor(user);
        System.out.println("majorManager——"+user.getName()+"——下的教师为：-----");
        for (Teacher one :
                teachers) {
            System.out.println(one);
            System.out.println("-----------------");
        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,teachers);
    }


    @RequestMapping("/getTopicByTeacher")
    public Result getTopicByTeacher(Integer id,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Topic> topics = majorManagerService.getTopicByTeacher(id,user);
        System.out.println("majorManager——"+"id为"+id+"的教师 能选择的选题为：-----");
        for (Topic one :
                topics) {
            System.out.println(one);
            System.out.println("-----------------");
        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,topics);
    }

    //给学生手动分配选题
    @RequestMapping("/ManuallyAssignTopic")
    public Result ManuallyAssignTopic(@RequestBody Map searchMap, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);

        String stuId = searchMap.get("id").toString();
        String teaId = searchMap.get("teaId").toString();
        String topicId = searchMap.get("topicId").toString();

        Boolean flag = majorManagerService.ManuallyAssignTopic(stuId,teaId,topicId,user);

        if (flag){
            return new Result(true,StatusCode.OK,"手动分配成功！");
        }else {
            return new Result(false,StatusCode.OK,"手动分配失败！");
        }
    }

    //查找单个id的学生的选题情况
    @RequestMapping("/findStuWishTopicById")
    public Result findStuWishTopicById(HttpServletRequest request,Integer id){
        System.out.println("需要查找的学生id:"+id);
        List<Choose> chooseList = majorManagerService.findStuWishTopicById(id);
        System.out.println("的选题信息为：\n");
        for (Choose choose: chooseList
        ) {
            System.out.println(choose);
        }
        System.out.println("-------------");
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,chooseList);
    }

    //专业管理员取消录取该学生
    @RequestMapping("/CancelStudentAdmission")
    public Result CancelStudentAdmission(Integer topic_id,HttpServletRequest request){
        System.out.println("选择的topic_id:"+topic_id);
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Boolean flag = majorManagerService.CancelStudentAdmission(topic_id,user);
        if(flag == true){
            return new Result(true,StatusCode.OK,"取消录取学生成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"取消录取学生失败！");
        }
    }

    //专业管理员 给未选选题的学生 发送邮件
    @RequestMapping("/EmailRemindStudent")
    public Result EmailRemindStudent(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Boolean flag = majorManagerService.EmailRemindStudent(user);
        if(flag == true){
            return new Result(true,StatusCode.OK,"发送成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"发送失败！");
        }
    }

    //获取随机分配结果
    @RequestMapping("/GetRandomlyAssigned")
    public Result GetRandomlyAssigned(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Choose> chooseList = majorManagerService.GetRandomlyAssigned(user);
        for (Choose choose :
                chooseList) {
            System.out.println("----------------");
            System.out.println(choose);
        }
        if (chooseList.isEmpty()){
            return new Result(false,StatusCode.OK,"选题不够分配 未能完全分配！");
        }
        return new Result(true,StatusCode.OK,"随机分配结果",chooseList);
    }

    //获取 开题答辩——分配结果
    @RequestMapping("/GetOpenAnswerAllocationResults")
    public Result GetOpenAnswerAllocationResults(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Map openParamMap = (Map) request.getSession().getAttribute("open_paramMap");
        System.out.println("session获取的open_paramMap:"+openParamMap.toString());
        List<OpenAnswer> openAnswerList = majorManagerService.GetOpenAnswerAllocationResults(user,openParamMap);
        if (openAnswerList.isEmpty()){
            return new Result(false,StatusCode.OK,"选题不够分配 未能完全分配！");
        }
        return new Result(true,StatusCode.OK,"随机分配结果",openAnswerList);
    }

    @RequestMapping("/SubmitRandomAssignment")
    public Result SubmitRandomAssignment(@RequestBody List<Choose> Choosese,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Choosese为:"+Choosese);
        Boolean flag = majorManagerService.SubmitRandomAssignment(Choosese,user);
        if (flag){
            return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS);
        }else {
            return new Result(false, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS);
        }
    }

    @RequestMapping("/SubmitOpenAnswerResult")
    public Result SubmitOpenAnswerResult(@RequestBody List<OpenAnswer> openAnswerList,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
//        System.out.println("openAnswerList为:\n"+openAnswerList);
        Boolean flag = majorManagerService.SubmitOpenAnswerResult(openAnswerList,user);
        if (flag){
            return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS);
        }else {
            return new Result(false, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS);
        }
    }

    @RequestMapping("/findTeaTargetById")
    public Result findTeaTargetById(@RequestBody Map Tea_Target, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
//        System.out.println("Tea_Target为:"+Tea_Target);

        TeaTarget teaTarget = majorManagerService.findTeaTargetById(Tea_Target,user);
        return new Result(true,StatusCode.OK,"查询成功！",teaTarget);
    }

    @RequestMapping("/UpdateTeaTargetById")
    public Result UpdateTeaTargetById(@RequestBody Map Tea_Target, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Tea_Target为:"+Tea_Target);

        Boolean flag = majorManagerService.UpdateTeaTargetById(Tea_Target,user);
        if (flag){
            return new Result(flag,StatusCode.OK,"设置成功！");
        }else {
            return new Result(flag,StatusCode.ERROR,"设置失败！");
        }

    }

    @RequestMapping("/searchAllStudent")
    public PageResult searchAllStudent(@RequestBody Map searchMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("searchAllStudent——searchMap为:"+searchMap);
        Page<Student> page = majorManagerService.searchAllStudent(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,page.getResult(), page.getTotal());
    }

    @RequestMapping("/searchAllTeacherNum")
    public Result searchAllTeacherNum(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Teacher    > teacherList = majorManagerService.searchAllTeacherNum(user);
        return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,teacherList.size());
    }

    //系统预分配——开题答辩
    @RequestMapping("/SystemAllocation")
    public Result SystemAllocation(@RequestBody Map paramMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        request.getSession().setAttribute("open_paramMap",paramMap);
        return new Result(true,StatusCode.OK,"预分配完成！",null);
    }

    // 查询该专业下所有的开题答辩分配情况
    @RequestMapping("/searchMajorAllOpenAnswer")
    public PageResult searchMajorAllOpenAnswer(@RequestBody Map searchMap,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<OpenAnswer> openAnswers = majorManagerService.searchMajorAllOpenAnswer(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,openAnswers.getResult(), openAnswers.getTotal());
    }

    @RequestMapping("/findOpenAnswerTeaList")
    public Result findOpenAnswerTeaList(Integer id,HttpServletRequest request){
        System.out.println("open_answer_id:"+id);
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Teacher> teacherList = majorManagerService.findOpenAnswerTeaList(user,id);
        return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,teacherList);
    }

    @RequestMapping("/findOpenAnswerStuList")
    public Result findOpenAnswerStuList(Integer id,HttpServletRequest request){
        System.out.println("open_answer_id:"+id);
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Student> students = majorManagerService.findOpenAnswerStuList(user,id);
        return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,students);
    }

    @RequestMapping("/findOpenAnswerById")
    public Result findOpenAnswerById(Integer id,HttpServletRequest request){
        System.out.println("open_answer_id:"+id);
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        OpenAnswer openAnswer = majorManagerService.findOpenAnswerById(user,id);
        return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,openAnswer);
    }

    // 根据教师id删除开题答辩组中的教师
    @RequestMapping("/delOpenAnswerTeaById")
    public Result delOpenAnswerTeaById(Integer id){
        System.out.println("要删除的教师Id="+id);
        Boolean flag = majorManagerService.delOpenAnswerTeaById(id);
        return new Result(flag,StatusCode.OK,MessageConstant.TEACHER_DELETE_SUCCESS);
    }

    // 根据教师id删除开题答辩组中的学生
    @RequestMapping("/delOpenAnswerStuById")
    public Result delOpenAnswerStuById(Integer id){
        System.out.println("要删除的学生Id="+id);
        Boolean flag = majorManagerService.delOpenAnswerStuById(id);
        return new Result(flag,StatusCode.OK,MessageConstant.STUDENT_DELETE_SUCCESS);
    }

    //获取该专业下 所有的未被分配开题答辩组的教师列表
    @RequestMapping("/getUndistributedTea")
    public Result getUndistributedTea(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Teacher> teacherList = majorManagerService.getUndistributedTea(user);
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_SEARCH_SUCCESS,teacherList);
    }

    //获取该专业下 所有的未被分配开题答辩组的学生列表
    @RequestMapping("/getUndistributedStu")
    public Result getUndistributedStu(HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<Student> studentList = majorManagerService.getUndistributedStu(user);
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_SEARCH_SUCCESS,studentList);
    }

    // 根据教师id删除开题答辩组中的教师
    @RequestMapping("/addTeaToOpenAnswerGroup")
    public Result addTeaToOpenAnswerGroup(Integer id,Integer open_answer_id,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("要添加的教师Id="+id);
        System.out.println("open_answer_id="+open_answer_id);
        Boolean flag = majorManagerService.addTeaToOpenAnswerGroup(id,open_answer_id,user);
        return new Result(flag,StatusCode.OK,MessageConstant.TEACHER_DELETE_SUCCESS);
    }

    // 根据教师id删除开题答辩组中的教师
    @RequestMapping("/addStuToOpenAnswerGroup")
    public Result addStuToOpenAnswerGroup(Integer id,Integer open_answer_id,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("要添加的学生Id="+id);
        System.out.println("open_answer_id="+open_answer_id);
        Boolean flag = majorManagerService.addStuToOpenAnswerGroup(id,open_answer_id,user);
        return new Result(flag,StatusCode.OK,MessageConstant.TEACHER_DELETE_SUCCESS);
    }

    @RequestMapping("/SaveOpenAnswer")
    public Result SaveOpenAnswer(Integer id, @RequestBody OpenAnswer openAnswer, HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        System.out.println("openanswerId="+id);
        System.out.println("OpenAnswer:"+openAnswer);

        Boolean flag = majorManagerService.SaveOpenAnswer(id,openAnswer,user);
        if (flag){
            return new Result(flag,StatusCode.OK,"设置成功！");
        }else {
            return new Result(flag,StatusCode.ERROR,"设置失败！");
        }
    }

    //根据Id解散答辩分组
    @RequestMapping("/dismissOpenAnswerGroupById")
    public Result dismissOpenAnswerGroupById(Integer id,HttpServletRequest request){
        System.out.println("要解散的答辩组Id="+id);
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Boolean flag = majorManagerService.dismissOpenAnswerGroupById(id,user);
        if(flag == true){
            return new Result(true,StatusCode.OK,"解散成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"解散失败！");
        }
    }

    //查询教师指导记录
    @RequestMapping("/searchMajorTeaGuidanceRecord")
    public PageResult searchMajorTeaGuidanceRecord(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        Page<Guidance> guidances = majorManagerService.searchMajorTeaGuidanceRecord(searchMap,user);
//        System.out.println(guidances);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,guidances.getResult(), guidances.getTotal());
    }

    //根据ID获取知道记录详情
    @RequestMapping(value = "/searchGuidanceDetails")
    public Result searchGuidanceDetails(Integer id,HttpServletRequest request){
        Administrator user = (Administrator) request.getSession().getAttribute("user");
        List<GuidanceRecord> guidanceRecordList = majorManagerService.searchGuidanceDetails(id,user);
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,guidanceRecordList);
    }

}

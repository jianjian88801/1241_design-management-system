package com.xunmaw.design.controller;

import com.github.pagehelper.Page;
import com.xunmaw.design.Util.word2vec.Segment;
import com.xunmaw.design.Util.word2vec.Word2Vec;
import com.xunmaw.design.common.MessageConstant;
import com.xunmaw.design.common.PageResult;
import com.xunmaw.design.common.Result;
import com.xunmaw.design.common.StatusCode;
import com.xunmaw.design.dao.*;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.ManagerService;
import com.xunmaw.design.service.StudentService;
import com.xunmaw.design.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/4/3 14:31
 * Description:
 */

@Controller
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.loadGoogleMode}")
    private String loadGoogleMode;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private FileDataMapper fileDataMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DepartMapper departMapper;
    @Autowired
    private MajorMapper majorMapper;

    @PostMapping("/upload/image")
    @ResponseBody
    public void uploadMap(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String dir = "image/"+user.getTeaName() +"/"+year;
        Map<String, Object> map = studentService.uploadImgMap(multipartFile, dir);
        System.out.println(map);
    }

    //获取教师的指导专业
    @RequestMapping(value = "/getTeacherMajors")
    public Result getMajorsByDepId(Integer id , HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        List<Major> majors = teacherService.getTeacherMajors(user);
        for (Major major : majors) {
            System.out.println(major);
        }
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,majors);
    }

    //根据教师获取该教师系部所有专业
    @RequestMapping(value = "/getMajorsByDep")
    public Result getMajorsByDep( HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        List<Major> majors = teacherService.getMajorsByDep(user);
        for (Major major : majors) {
            System.out.println(major);
        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,majors);
    }

    @RequestMapping("/searchAllTopic")
    public PageResult searchAllTopic(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic> topics = teacherService.searchAllTopic(searchMap,user);
        for (Topic topic :
                topics) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //教师选题查重
    @RequestMapping("/CheckTopicDuplicates")
    public Result CheckTopicDuplicates(String topicStr)  {
        System.out.println("topicStr:"+topicStr);

        Word2Vec vec = new Word2Vec();
        try {
            vec.loadGoogleModel(loadGoogleMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Topic> topics = topicMapper.selectAll();
        String title = topicStr;
        for (int i = 0; i < topics.size(); i++) {
            String title1 = topics.get(i).getTitle();
            List<String> wordList1 = Segment.getWords(title);
            List<String> wordList2 = Segment.getWords(title1);
            System.out.println("-----句子相似度-----");
            System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2));
            float result = vec.sentenceSimilarity(wordList1, wordList2);
            if (result > 0.7) {
                System.out.println("重复题目如下：");
                System.out.println(title + "------" + title1);
                return new Result(false,StatusCode.ERROR,"新建选题相似度过高！相似选题：\n" + title1);
            }
        }
        return new Result(true,StatusCode.OK,"选题为未重复，可以创建！");
    }

    //创建选题
    @RequestMapping("/CreateTopic")
    public Result CreateTopic(@RequestBody Map topicMap,HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");

        Topic topic = new Topic();
        topic.setTitle(topicMap.get("title").toString());
        topic.setCate(topicMap.get("cate").toString());
        topic.setSource(topicMap.get("source").toString());
        topic.setType(topicMap.get("type").toString());
        topic.setContent(topicMap.get("content").toString());

        //限选专业
        List<Integer> majors = (List<Integer>) topicMap.get("majors");
        System.out.println("majors："+majors);

//        System.out.println("新建的topic为:"+topic);
//
//        Word2Vec vec = new Word2Vec();
//        try {
//            vec.loadGoogleModel(loadGoogleMode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<Topic> topics = topicMapper.selectAll();
//        String title = topic.getTitle();
//        for (int i = 0; i < topics.size(); i++) {
//            String title1 = topics.get(i).getTitle();
//            List<String> wordList1 = Segment.getWords(title);
//            List<String> wordList2 = Segment.getWords(title1);
//            System.out.println("-----句子相似度-----");
//            System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2));
//            float result = vec.sentenceSimilarity(wordList1, wordList2);
//            if (result > 0.7) {
//                System.out.println("重复题目如下：");
//                System.out.println(title + "------" + title1);
//                return new Result(false,StatusCode.ERROR,"新建选题相似度过高！相似选题：\n" + title1);
//            }
//        }
        boolean flag = teacherService.CreateTopic(topic,user,majors);
        if (flag){
            return new Result(true,StatusCode.OK,"新建选题信息成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"新建选题信息失败！");
        }
    }

    //查询该老师自己发布的选题
    @RequestMapping("/searchMyTopic")
    public PageResult searchMyTopic(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
//        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic> topics = teacherService.searchMyTopic(searchMap,user);
//        for (Topic topic :
//                topics) {
//            System.out.println(topic);
//        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //查询该教师的信息
    @RequestMapping("/findMyInfo")
    public Result findMyInfo(HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findMyInfo(user);
//        System.out.println(teacher);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,teacher);
    }

    //更新教师信息
    @RequestMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Map teacherMap){
        System.out.println("所需更新的教师："+teacherMap);

        Teacher teacher = new Teacher();
        teacher.setTeaId((Integer) teacherMap.get("teaId"));
        teacher.setTeaNo(teacherMap.get("teaNo").toString());
        teacher.setTeaName(teacherMap.get("teaName").toString());
        teacher.setDepartId((Integer) teacherMap.get("departId"));
        teacher.setPhoneNumber(teacherMap.get("phoneNumber").toString());
        teacher.setGender((Integer) teacherMap.get("gender"));
        teacher.setXueWei(teacherMap.get("xueWei").toString());
        teacher.setJobTitle(teacherMap.get("jobTitle").toString());
        teacher.setMajors((ArrayList<Integer>) teacherMap.get("majors"));

        Boolean flag = managerService.updateTeacher(teacher);
//        System.out.println("更新成功："+flag);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_UPDATE_SUCCESS);
    }

    //保存教师指导记录
    @RequestMapping("/SaveGuidanceLog")
    public Result SaveGuidanceLog(@RequestBody Map GuidanceMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        System.out.println("GuidanceMap:"+GuidanceMap);

        Boolean flag = teacherService.SaveGuidanceLog(GuidanceMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"指导记录保存失败！");
        }else {
            return new Result(true,StatusCode.OK,"指导记录保存成功！");
        }
    }

    //教师修改密码
    @RequestMapping("/TeacherResetPassword")
    public Result TeacherResetPassword(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        Boolean flag = teacherService.TeacherResetPassword(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"原始密码错误！请重新输入！");
        }else {
            return new Result(true,StatusCode.OK,"教师密码修改成功！");
        }
    }

    //获取选题的 限选专业
    @RequestMapping(value = "/getTopicMajorById")
    public Result getTopicMajorById(Integer id){
        List<Topic_Major> topic_majors = teacherService.getTopicMajorById(id);
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,topic_majors);
    }

    //根据ID获取知道记录详情
    @RequestMapping(value = "/searchGuidanceDetails")
    public Result searchGuidanceDetails(Integer id,HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        List<GuidanceRecord> guidanceRecordList = teacherService.searchGuidanceDetails(id,user);
        return new Result(true, StatusCode.OK, MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,guidanceRecordList);
    }

    //删除选题的限选专业
    @RequestMapping("/deleteTopicMajorById")
    public Result deleteTopicMajorById(Integer id){
        Boolean flag = teacherService.deleteTopicMajorById(id);
        if (flag){
            return new Result(true,StatusCode.OK,"限选专业删除成功");
        }else {
            return new Result(false,StatusCode.OK,"限选专业删除失败");
        }
    }

    //教师选择学生
    @RequestMapping("/SelectStudentById")
    public Result SelectStudentById(Integer id,HttpServletRequest request){
        //首先需要判断 该学生是否已经被其他的老师选中
        boolean i = teacherService.findStudentChooseStatus(id);
        if (i==false){
            return new Result(false,StatusCode.ERROR,"该学生已经被其他老师录取！！");
        }

        System.out.println("选择的chooseid:"+id);
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        Boolean flag = teacherService.SelectStudentById(id,user);
        if(flag == true){
            return new Result(true,StatusCode.OK,"选择学生成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"选择学生失败！");
        }
    }

    //教师取消录取的学生
    @RequestMapping("/CancelStudentAdmission")
    public Result CancelStudentAdmission(Integer id,HttpServletRequest request){
        System.out.println("选择的chooseId:"+id);
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        Boolean flag = teacherService.CancelStudentAdmission(id,user);
        if(flag == true){
            return new Result(true,StatusCode.OK,"取消录取学生成功！");
        }else {
            return new Result(false,StatusCode.ERROR,"取消录取学生失败！");
        }
    }

    //查找该教师所对应的学生文件日志信息
    @RequestMapping("/searchMyLogNotes")
    public Result searchMyLogNotes(HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        List<LogNote> logNoteList = teacherService.searchMyLogNotes(user);
        return new Result(true, StatusCode.OK, "学生日志信息查找成功！",logNoteList);
    }

    //查找该教师所对应指标数据
    @RequestMapping("/searchMyTargetData")
    public Result searchMyTargetData(HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        TeaTarget teaTarget = teacherService.searchMyTargetData(user);
        return new Result(true, StatusCode.OK, "教师指标数据查找成功！",teaTarget);
    }

    //更新任务书信息——审核选题
    @RequestMapping("/UpdateStuAssBook")
    public Result UpdateStuAssBook(@RequestBody Assignment_book assignment_book, HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        System.out.println("所需更新的信息："+assignment_book);
        Boolean flag = teacherService.UpdateStuAssBook(assignment_book);
        return new Result(flag,StatusCode.OK,"审核完成！");
    }

    //更新开题报告信息——审核选题
    @RequestMapping("/UpdateStuOpenReport")
    public Result UpdateStuOpenReport(@RequestBody Opening_report opening_report , HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        System.out.println("所需更新开题报告的信息："+opening_report);
        Boolean flag = teacherService.UpdateStuOpenReport(opening_report);
        return new Result(flag,StatusCode.OK,"审核完成！");
    }

    //更新中期检查信息——审核选题
    @RequestMapping("/UpdateMidTermExam")
    public Result UpdateMidTermExam(@RequestBody MidTermExam midTermExam ,HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        System.out.println("所需更新中期检查的信息："+midTermExam);
        Boolean flag = teacherService.UpdateMidTermExam(midTermExam);
        return new Result(flag,StatusCode.OK,"审核完成！");
    }

    //修改选题信息
    @RequestMapping("/UpdateTopic")
    public Result UpdateTopic(@RequestBody Topic topic){
        System.out.println(topic);
        boolean flag = managerService.UpdateTopic(topic);
//        System.out.println("添加成功："+flag);
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_UPDATE_SUCCESS);
    }

    //开题答辩组组长教师，修改开题答辩地点
    @RequestMapping("/SubmitOpenAnswerAddress")
    public Result SubmitOpenAnswerAddress( String address,HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        System.out.println("修改地点："+address);
        boolean flag = teacherService.SubmitOpenAnswerAddress(address,user);
        return new Result(flag,StatusCode.OK,MessageConstant.TOPIC_UPDATE_SUCCESS);
    }

    //开题答辩组组长教师，修改开题答辩地点
    @RequestMapping("/SubmitOpenAnswerTime")
    public Result SubmitOpenAnswerTime(@RequestBody OpenAnswerTea openAnswerTea, HttpServletRequest request){
        String time = openAnswerTea.getOpenAnswer().getTime();
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        System.out.println("修改时间："+time);
        boolean flag = teacherService.SubmitOpenAnswerTime(time,user);
        return new Result(flag,StatusCode.OK,MessageConstant.TOPIC_UPDATE_SUCCESS);
    }

    //删除选题信息
    @RequestMapping("/deleteTopicById")
    public Result deleteTopicById(Integer id){
        Boolean flag = teacherService.deleteTopicById(id);
        if (flag){
            return new Result(true,StatusCode.OK,"选题删除成功");
        }else {
            return new Result(false,StatusCode.OK,"选题删除失败");
        }
    }

    //查询教师录取的学生
    @RequestMapping("/searchMyStudents")
    public PageResult searchMyStudents(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        Page<Topic> topics = teacherService.searchMyStudents(searchMap,user);
        System.out.println(user.getTeaName()+"录取的学生为：---------");
        for (Topic topic :
                topics) {
            System.out.println("------------");
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //查询教师指导记录
    @RequestMapping("/searchMyGuidanceRecord")
    public PageResult searchMyGuidanceRecord(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        Page<Guidance> guidances = teacherService.searchMyGuidanceRecord(searchMap,user);
        System.out.println(guidances);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,guidances.getResult(), guidances.getTotal());
    }

    @RequestMapping("/findStuById")
    public Result findStuById(Integer id){
        Student student = managerService.findStuById(id);
        System.out.println("findStuById:\n"+student);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,student);
    }


    //查询教师录取的学生——文件
    @RequestMapping("/searchStudentsFile")
    public PageResult searchStudentsFile(@RequestBody Map searchMap,HttpServletRequest request){
        System.out.println("searchMap:"+searchMap);

        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        Page<Topic> topics = teacherService.searchStudentsFile(searchMap,user);
//        System.out.println(user.getTeaName()+"录取的学生及文件为：---------");
//        for (Topic topic :
//                topics) {
//            System.out.println("------------");
//            System.out.println(topic);
//        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //查询该学生的任务书
    @RequestMapping("/findStuAssignmentBookById")
    public Result findStuAssignmentBookById(Integer id){
        Assignment_book assignment_book = teacherService.findStuAssignmentBookById(id);
        if (assignment_book!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,assignment_book);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    //查询该学生的开题报告
    @RequestMapping("/findOpeningReportById")
    public Result findOpeningReportById(Integer id){
        Opening_report opening_report = teacherService.findOpeningReportById(id);
        if (opening_report!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,opening_report);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    //查询该学生的中期检查
    @RequestMapping("/findMidTermExamById")
    public Result findMidTermExamById(Integer id){
        MidTermExam midTermExam = teacherService.findMidTermExamById(id);
        if (midTermExam!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,midTermExam);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, HttpServletRequest request, int fileType,String stuNumber) throws UnsupportedEncodingException {

        System.out.println("文件类型:"+fileType);
        System.out.println("学生stuNumber:"+stuNumber);

        final Student student = studentMapper.selectByPrimaryKey(stuNumber);

        FileData example = new FileData();
        example.setStudentid(student.getStuNumber());
        FileData fileData = fileDataMapper.selectOne(example);

        String type = null;
        if (fileType == 1) {
            type = fileData.getJobfile();
        }else if (fileType == 2){
            type = fileData.getKtfile();
        }else if (fileType == 3){
            type = fileData.getShufile();
        }else if (fileType == 4){
            type = fileData.getYifile();
        }else if (fileType == 5){
            type = fileData.getXgfile();
        }else if (fileType == 6){
            type = fileData.getChafile();
        }else if (fileType == 7){
            type = fileData.getBsfile();
        }else if (fileType == 8){
            type = fileData.getZqfile();
        }
        String fileName = type.substring(type.lastIndexOf("/") + 1);
        String dir = uploadFolder+type;

        System.out.println("文件路径为："+dir);
        File file = new File(dir);

        if(!file.exists()){
            return "下载文件不存在";
        }

        response.reset();
        response.setContentType("application/octet-stream");

        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }


    //邮件提醒学生提交文件
    @RequestMapping("/RemindMyStuById")
    public Result RemindMyStuById(Integer stuId,HttpServletRequest request){
        Teacher user = (Teacher) request.getSession().getAttribute("user");
        Boolean flag = teacherService.RemindMyStuById(stuId,user);
        if (flag){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS);
        }else {
            return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS);
        }
    }

    //根据教师获取该教师的开题答辩信息
    @RequestMapping(value = "/getMyOpenAnswerInfo")
    public Result getMyOpenAnswerInfo( HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        OpenAnswerTea openAnswer = teacherService.getMyOpenAnswerInfo(user);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswer);
    }

    //查询该教师对应的答辩组的学生
    @RequestMapping("/findOpenAnswerStuList")
    public Result findOpenAnswerStuList(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        List<Student> students = teacherService.findOpenAnswerStuList(searchMap,user);
        return new Result(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,students);
    }

    //查询该学生的中期检查
    @RequestMapping("/GetStuOpenAnswerById")
    public Result GetStuOpenAnswerById(Integer id){
        System.out.println("要查的学生id="+id);
        OpenAnswerStu openAnswerStu = teacherService.GetStuOpenAnswerById(id);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerStu);
    }

    //查询该学生的开题答辩问题列表
    @RequestMapping("/GetStuOpenAnswerQuestionListById")
    public Result GetStuOpenAnswerQuestionListById(Integer id,HttpServletRequest request){
        List<OpenAnswerQuestion> openAnswerQuestionList = teacherService.GetStuOpenAnswerQuestionListById(id);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerQuestionList);
    }

    //查询该学生的开题答辩记录信息
    @RequestMapping("/GetStuOpenAnswerRecordById")
    public Result GetStuOpenAnswerRecordById(Integer id,HttpServletRequest request){
        OpenAnswerRecord openAnswerRecord = teacherService.GetStuOpenAnswerRecordById(id);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerRecord);
    }

    //保存教师指导记录
    @RequestMapping("/SubmitStuOpenAnswerRecord")
    public Result SubmitStuOpenAnswerRecord(@RequestBody Map map,HttpServletRequest request){
        HttpSession session = request.getSession();
        Teacher user = (Teacher) session.getAttribute("user");
        System.out.println("Map:"+map);

        Boolean flag = teacherService.SubmitStuOpenAnswerRecord(map,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"保存失败！");
        }else {
            return new Result(true,StatusCode.OK,"保存成功！");
        }
    }
}

package com.xunmaw.design.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunmaw.design.dao.*;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenchengjian
 * @date 2022/3/28 21:17
 * Description:
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.staticPath}")
    private String staticPath;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DepartMapper departMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TeaGuideMajorMapper teaGuideMajorMapper;
    @Autowired
    private AdministratorMapper administratorMapper;
    @Autowired
    private TopicMajorMapper topicMajorMapper;
    @Autowired
    private ChooseMapper chooseMapper;
    @Autowired
    private AssignmentBookMapper assignmentBookMapper;
    @Autowired
    private FileDataMapper fileDataMapper;
    @Autowired
    private OpeningReportMapper openingReportMapper;
    @Autowired
    private GuidanceRecordMapper guidanceRecordMapper;
    @Autowired
    private MidTermExamMapper midTermExamMapper;
    @Autowired
    private OpenAnswerMapper openAnswerMapper;
    @Autowired
    private OpenAnswerTeaMapper openAnswerTeaMapper;
    @Autowired
    private OpenAnswerStuMapper openAnswerStuMapper;
    @Autowired
    private OpenAnswerRecordMapper openAnswerRecordMapper;
    @Autowired
    private OpenAnswerQuestionMapper openAnswerQuestionMapper;

    @Override
    public Page<Topic> searchAllTopic(Map searchMap, Student user) {
        Major major = new Major();
        major.setProNumber(user.getProNumber());
        user.setMajor(majorMapper.selectOne(major));

        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //筛选该系部下从属的选题
            criteria.andLike("depart_id",user.getStuDepartId().toString());

            //指导教师搜索(精确查找）
            if (StringUtil.isNotEmpty((String) searchMap.get("teaName"))){
                Teacher teacher = new Teacher();
                teacher.setTeaName(searchMap.get("teaName").toString());
                Teacher find_teacher = teacherMapper.selectOne(teacher);
                criteria.andLike("teacher_id", find_teacher.getTeaId().toString());
            }

            //标题名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("topicTitle"))){
                criteria.andLike("title","%"+(String) searchMap.get("topicTitle")+"%");
            }

            //状态搜索
            //if (StringUtil.isNotEmpty(searchMap.get("status").toString())){
            //    criteria.andLike("state", searchMap.get("status").toString());
            //}

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Topic> topics = (Page<Topic>) topicMapper.selectByExample(example);
        for (Topic topic :
                topics) {
            topic.setTeacher(teacherMapper.selectByPrimaryKey(topic.getTeacher_id()));
            if (topic.getStudent_id() != null){
                //表示该选题已被学生选中
                topic.setStudent(studentMapper.selectByPrimaryKey(topic.getStudent_id()));
                topic.getStudent().setaClass(classMapper.selectOne(new Class(topic.getStudent().getStuClass())));
            }else {
                topic.setStudent(new Student());
                topic.getStudent().setaClass(new Class());
            }
            Choose one = new Choose();
            one.setTopic_id(topic.getTopic_id());
            topic.setChooses(chooseMapper.select(one));

            Topic_Major topic_major = new Topic_Major();
            topic_major.setTopic_id(topic.getTopic_id());
            topic_major.setMajor_id(user.getMajor().getMajorId());
            topic.setTopic_major(topicMajorMapper.selectOne(topic_major));
        }

        topics.removeIf(e->e.getTopic_major()==null);
        topics.removeIf(e->e.getTopic_major().getStatus()<2);

        return topics;
    }

    @Override
    public Map<String,Object> uploadImgMap(MultipartFile multipartFile, String dir){

        //// 1: 指定文件上传的目录
        //File dirFile = new File("E://ATools/" + dir);
        try {
            String realFilename = multipartFile.getOriginalFilename();  // 上传的文件：aaa.jpg
            // 2：截取文件名的后缀
            String imgSuffix = realFilename.substring(realFilename.lastIndexOf(".")); // 拿到.jpg
            // 3：生成的唯一文件名: 最好不用中文名
            String newFileName = UUID.randomUUID().toString() + imgSuffix; // 将aaa.jpg 改写成：SD23423432-234234ms.jpg
            // 4：日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());    // 日期目录：2022/3/26

            //// 以前在Javaweb做文件上传
            //HttpServletRequest request = null;
            //String serverpath = request.getServletContext().getContextPath(); // 当前tomcat目录下 webapps/demo/upload/bbs/2022/3/26/SD23423432-234234ms.jpg
            //// http:localhost:8777/demo/upload/bbs/2022/3/26/SD23423432-234234ms.jpg


            // 5: 指定文件上传以后的目录
            String serverpath = uploadFolder;
            File targetPath = new File(serverpath + dir); // 生成一个最终目录：E://ATools/avater/2022/3/26
            if (!targetPath.exists()) targetPath.mkdirs();   // 如果目录不存在：E://ATools/bbs/2022/3/26 递归创建
            // 6：指定文件上传以后的服务器的完整的文件名
            File targetFileName = new File(targetPath, realFilename); // 文件上传以后再服务器上最终文件名和目录是：E://ATools/bbs/2022/3/26/SD23423432-234234ms.jpg
            // 7：文件上传到指定的目录
            multipartFile.transferTo(targetFileName); // 将用户选择的aaa.jpg上传到 E://ATools/bbs/2022/3/26/SD23423432-234234ms.jpg

            // 可访问的路径要返回页面
            // http://localhost:8777/bbs/2022/3/26/SD23423432-234234ms.jpg
            String filename = dir +  "/" + realFilename;

            Map<String, Object> map = new HashMap<>();
            map.put("url", staticPath + "/upimages/" + filename);
            map.put("size", multipartFile.getSize());
            map.put("ext", imgSuffix);
            map.put("filename", realFilename);
            map.put("rpath", dir +  "/" + realFilename);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Choose> findMyWishTopic(Student user) {
        Choose example = new Choose();
        example.setStudent_id(user.getId());
        List<Choose> list = chooseMapper.select(example);

        for (Choose choose: list
             ) {
            Topic topicExample = new Topic();
            topicExample.setTopic_id(choose.getTopic_id());
            choose.setTopic(topicMapper.selectOne(topicExample));

            choose.getTopic().setTeacher(teacherMapper.selectByPrimaryKey(choose.getTopic().getTeacher_id()));
            Choose one = new Choose();
            one.setTopic_id(choose.getTopic().getTopic_id());
            choose.getTopic().setChooses(chooseMapper.select(one));
        }
        return list;
    }

    @Override
    public Boolean ChooseTopic(List<Integer> ids, Student user) {
        for (Integer id :
                ids) {
            Choose choose = new Choose();
            choose.setTopic_id(id);
            choose.setStudent_id(user.getId());
            choose.setStatus(0);    //  初始status状态值为0 表示为被老师录取
            choose.setWish(1);      //  每个学生只能选一个志愿 所以都是默认第一志愿
            choose.setSel_time(new Date());
            int row = chooseMapper.insert(choose);
        }
        return true;
    }


    //学生取消选题
    @Override
    public Boolean CancelChooseTopic(Integer id, Student user) {
        Choose choose = new Choose();
        choose.setChoose_id(id);
        choose.setStudent_id(user.getId());
        int row = chooseMapper.delete(choose);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean StudentResetPassword(Map searchMap, Student user) {
        //1. 先验证输入的原始密码是否正确
        String old_password = searchMap.get("old_password").toString();
        Student example = new Student();
        example.setId(user.getId());
        Student student = studentMapper.selectOne(example);

        if (!student.getPassword().equals(old_password)){
            return false;
        }else {
            student.setPassword(searchMap.get("new_password").toString());
            studentMapper.updateByPrimaryKeySelective(student);
            return true;
        }
    }

    @Override
    public Assignment_book findMyAssignmentBook(Integer id, Student user) {
        Assignment_book ass_book_example = new Assignment_book();
        ass_book_example.setStu_id(id);
        Assignment_book assignment_book = assignmentBookMapper.selectOne(ass_book_example);

        //放入文件位置
        String stuNumber = studentMapper.selectByPrimaryKey(assignment_book.getStu_id()).getStuNumber();
        final FileData fileDataExample = new FileData();
        fileDataExample.setStudentid(stuNumber);
        final String jobfile = fileDataMapper.selectOne(fileDataExample).getJobfile();
        assignment_book.setFile_location(jobfile);
        
        //放入教师姓名
        assignment_book.setTea_name(teacherMapper.selectByPrimaryKey(assignment_book.getTea_id()).getTeaName());

        //放入学生姓名
        assignment_book.setStu_name(studentMapper.selectByPrimaryKey(assignment_book.getStu_id()).getStuName() +"+"
                +stuNumber);
        //放入学生系部-专业-班级
        final String depName = departMapper.selectByPrimaryKey(user.getStuDepartId()).getDepName();

        Major major_temp = new Major();
        major_temp.setProNumber(user.getProNumber());
        final String majorName = majorMapper.selectOne(major_temp).getMajorName();

        Class aClass_temp = new Class();
        aClass_temp.setClassChar(user.getStuClass());
        final String className = classMapper.selectOne(aClass_temp).getClassName();

        String department = depName+"+"+majorName+"+"+className;
//        System.out.println("整合后的部门："+department);

        assignment_book.setDepartment(department);

        return assignment_book;
    }

    @Override
    public Boolean SaveAssignmentBook(Map searchMap, Student user) {
        int flag=0;
        Assignment_book assignment_book_example = new Assignment_book();
        assignment_book_example.setStu_id(user.getId());
        Assignment_book assignment_book = assignmentBookMapper.selectOne(assignment_book_example);
        if (assignment_book!=null){
            //学生创建过 任务书记录
            assignment_book.setResearch_contents(searchMap.get("contents").toString());
            assignment_book.setPurpose(searchMap.get("purpose").toString());
            assignment_book.setReference(searchMap.get("reference").toString());
            flag = assignmentBookMapper.updateByPrimaryKey(assignment_book);
        }
        if (flag==1){
            return true;
        }
        return false;
    }

    @Override
    public Opening_report findOpeningReportById(Integer id, Student user) {
        Opening_report opening_report_example = new Opening_report();
        opening_report_example.setStu_id(id);
        Opening_report opening_report = openingReportMapper.selectOne(opening_report_example);

        //放入文件位置
        String stuNumber = studentMapper.selectByPrimaryKey(opening_report.getStu_id()).getStuNumber();
        final FileData fileDataExample = new FileData();
        fileDataExample.setStudentid(stuNumber);
        final String ktfile = fileDataMapper.selectOne(fileDataExample).getKtfile();
        opening_report.setFile_location(ktfile);

        //放入教师姓名
        opening_report.setTea_name(teacherMapper.selectByPrimaryKey(opening_report.getTea_id()).getTeaName());

        //放入学生姓名
        opening_report.setStu_name(studentMapper.selectByPrimaryKey(opening_report.getStu_id()).getStuName() +"+"
                +stuNumber);

        //放入对应的任务书
        opening_report.setAssignment_book(findMyAssignmentBook(user.getId(), user));

//        System.out.println("开题报告:\n"+opening_report);
        return opening_report;
    }

    @Override
    public Boolean SaveOpeningReport(Map searchMap, Student user) {
        int flag=0;
        Opening_report opening_report_example = new Opening_report();
        opening_report_example.setStu_id(user.getId());
        Opening_report opening_report = openingReportMapper.selectOne(opening_report_example);
        if (opening_report!=null){
            //学生创建过 任务书记录
            opening_report.setBackground_and_significance(searchMap.get("background_and_significance").toString());
            opening_report.setContent(searchMap.get("content").toString());
            opening_report.setReference(searchMap.get("reference").toString());
            opening_report.setDevelopment_trend(searchMap.get("development_trend").toString());
            opening_report.setExpected(searchMap.get("expected").toString());
            opening_report.setDesign(searchMap.get("design").toString());
            flag = openingReportMapper.updateByPrimaryKey(opening_report);
        }
        if (flag==1){
            return true;
        }
        return false;
    }

    @Override
    public Page<GuidanceRecord> searchAllGuidance(Map searchMap, Student user) {

        //通用Mapper多条件搜索，标准写法
        Example example = new Example(GuidanceRecord.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //筛选该系部下从属的选题
            criteria.andLike("stu_id",user.getId().toString());

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<GuidanceRecord> guidanceRecords = (Page<GuidanceRecord>) guidanceRecordMapper.selectByExample(example);
        for (GuidanceRecord one : guidanceRecords) {
            one.setTeacher(teacherMapper.selectByPrimaryKey(one.getTea_id()));
            one.setStudent(studentMapper.selectByPrimaryKey(one.getStu_id()));
        }
        return guidanceRecords;
    }

    @Override
    public MidTermExam findMidTermExamById(Integer id, Student user) {
        MidTermExam midTermExam_example = new MidTermExam();
        midTermExam_example.setStu_id(id);
        MidTermExam midTermExam = midTermExamMapper.selectOne(midTermExam_example);

        //放入文件位置
        String stuNumber = studentMapper.selectByPrimaryKey(midTermExam.getStu_id()).getStuNumber();
        final FileData fileDataExample = new FileData();
        fileDataExample.setStudentid(stuNumber);
        final String zqfile = fileDataMapper.selectOne(fileDataExample).getZqfile();
        midTermExam.setFile_location(zqfile);

        //放入教师姓名
        midTermExam.setTeacher(teacherMapper.selectByPrimaryKey(midTermExam.getTea_id()));

        //放入学生姓名
        midTermExam.setStudent(studentMapper.selectByPrimaryKey(midTermExam.getStu_id()));
        midTermExam.getStudent().setStuName(midTermExam.getStudent().getStuName()+"+"+midTermExam.getStudent().getStuNumber());

        //放入对应的任务书
        midTermExam.setAssignment_book(findMyAssignmentBook(user.getId(), user));

        return midTermExam;
    }

    @Override
    public Boolean SaveMidTermExam(Map searchMap, Student user) {
        int flag=0;
        MidTermExam midTermExam_example = new MidTermExam();
        midTermExam_example.setStu_id(user.getId());
        MidTermExam midTermExam = midTermExamMapper.selectOne(midTermExam_example);
        if (midTermExam!=null){
            //学生创建过 任务书记录
            midTermExam.setTime_schedule(searchMap.get("time_schedule").toString());
            midTermExam.setCompletion(searchMap.get("completion").toString());
            midTermExam.setInitial_achieve(searchMap.get("initial_achieve").toString());
            midTermExam.setMain_issues(searchMap.get("main_issues").toString());
            flag = midTermExamMapper.updateByPrimaryKey(midTermExam);
        }
        if (flag==1){
            return true;
        }
        return false;
    }

    @Override
    public OpenAnswerStu GetStuOpenAnswer(Student user) {
        Major major_example = new Major();
        major_example.setProNumber(user.getProNumber());
        user.setMajor(majorMapper.selectOne(major_example));

        Topic topic_example = new Topic();
        topic_example.setStudent_id(user.getId());
        Integer guidance_tea_id = null;
        if (topicMapper.selectOne(topic_example)!=null){
            guidance_tea_id = topicMapper.selectOne(topic_example).getTeacher_id();
            user.setGuid_tea(teacherMapper.selectByPrimaryKey(guidance_tea_id));
            user.setTopic(topicMapper.selectOne(topic_example));
        }else {
            user.setGuid_tea(new Teacher());
        }

        Class aClass = new Class();
        aClass.setClassChar(user.getStuClass());
        user.setaClass(classMapper.selectOne(aClass));

        OpenAnswerStu oas_example = new OpenAnswerStu();
        oas_example.setStu_id(user.getId());
        OpenAnswerStu openAnswerStudent = openAnswerStuMapper.selectOne(oas_example);
        OpenAnswer openAnswer = openAnswerMapper.selectByPrimaryKey(openAnswerStudent.getOpen_answer_id());
        List<Teacher> teachers = new ArrayList<>();
        OpenAnswerTea oat_example = new OpenAnswerTea();
        oat_example.setOpen_answer_id(openAnswerStudent.getOpen_answer_id());
        List<OpenAnswerTea> tea_select = openAnswerTeaMapper.select(oat_example);
        for (OpenAnswerTea one :
                tea_select) {
            teachers.add(teacherMapper.selectByPrimaryKey(one.getTea_id()));
        }
        openAnswer.setTeacherList(teachers);
        openAnswerStudent.setOpenAnswer(openAnswer);

        openAnswerStudent.setStudent(user);



        return openAnswerStudent;
    }

    @Override
    public Boolean SubmitOpenAnswerQuestion(Map searchMap, Student user) {
        OpenAnswerRecord oar_example = new OpenAnswerRecord();
        oar_example.setStu_id(user.getId());
        OpenAnswerRecord openAnswerRecord = openAnswerRecordMapper.selectOne(oar_example);

        OpenAnswerQuestion openAnswerQuestion = new OpenAnswerQuestion();
        openAnswerQuestion.setOpen_answer_id(openAnswerRecord.getOpen_answer_id());
        openAnswerQuestion.setRecord_id(openAnswerRecord.getId());
        openAnswerQuestion.setStu_id(user.getId());
        openAnswerQuestion.setTea_id(Integer.valueOf(searchMap.get("teaId").toString()));
        openAnswerQuestion.setQuestion(searchMap.get("question").toString());
        openAnswerQuestion.setAnswer(searchMap.get("answer").toString());
        openAnswerQuestion.setRemark(searchMap.get("remark").toString());

        int i = openAnswerQuestionMapper.insert(openAnswerQuestion);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<OpenAnswerQuestion> GetStuOpenAnswerQuestionList(Student user) {
        OpenAnswerQuestion oaq_example = new OpenAnswerQuestion();
        oaq_example.setStu_id(user.getId());
        List<OpenAnswerQuestion> questions = openAnswerQuestionMapper.select(oaq_example);
        for (OpenAnswerQuestion one : questions) {
            one.setTeacher(teacherMapper.selectByPrimaryKey(one.getTea_id()));
        }
        return questions;
    }

    @Override
    public Boolean delOpenAnswerQuestionById(Integer id) {
        int i = openAnswerQuestionMapper.deleteByPrimaryKey(id);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public OpenAnswerRecord GetStuOpenAnswerRecord(Student user) {
        OpenAnswerRecord oar_example = new OpenAnswerRecord();
        oar_example.setStu_id(user.getId());
        OpenAnswerRecord openAnswerRecord = openAnswerRecordMapper.selectOne(oar_example);
        return openAnswerRecord;
    }

}

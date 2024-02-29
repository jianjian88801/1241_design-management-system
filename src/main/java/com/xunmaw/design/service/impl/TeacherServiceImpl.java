package com.xunmaw.design.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunmaw.design.dao.*;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenchengjian
 * @date 2022/4/3 14:34
 * Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Value("${spring.mail.username}")
    private String sendEmailUsername;

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
    private FileDataMapper fileDataMapper;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private AssignmentBookMapper assignmentBookMapper;
    @Autowired
    private ProgressMapper progressMapper;
    @Autowired
    private LogNoteMapper logNoteMapper;
    @Autowired
    private GuidanceRecordMapper guidanceRecordMapper;
    @Autowired
    private GuidanceMapper guidanceMapper;
    @Autowired
    private TeaTargetMapper teaTargetMapper;
    @Autowired
    private OpeningReportMapper openingReportMapper;
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

    //获取教师的指导专业
    @Override
    public List<Major> getTeacherMajors(Teacher user) {
        Major major = new Major();
        major.setMajorDepartId(user.getDepartId());
        List<Major> majors = majorMapper.select(major);

        for (Major major1:
                majors) {
            Depart depart = new Depart();
            depart.setDepId(major1.getMajorDepartId());
            Depart depart1 = departMapper.selectByPrimaryKey(depart);
            major1.setDepart(depart1);
        }

        return majors;
    }

    //根据教师获取该教师系部所有专业
    @Override
    public List<Major> getMajorsByDep(Teacher user) {
        Major major = new Major();
        major.setMajorDepartId(user.getDepartId());
        List<Major> majors = majorMapper.select(major);

        for (Major major1:
                majors) {
            Depart depart = new Depart();
            depart.setDepId(major1.getMajorDepartId());
            Depart depart1 = departMapper.selectByPrimaryKey(depart);
            major1.setDepart(depart1);
        }

        return majors;
    }

    @Override
    public Page<Topic> searchAllTopic(Map searchMap, Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //专业搜索
            if (StringUtil.isNotEmpty(searchMap.get("major").toString())){
                Major major = new Major();
                major.setProNumber(searchMap.get("major").toString());
                Integer major_id = majorMapper.selectOne(major).getMajorId();
                criteria.andLike("student_major_id", major_id.toString());
            }

            //届数搜索
            if (StringUtil.isNotEmpty(searchMap.get("session").toString())){
                criteria.andLike("grade", searchMap.get("session").toString());
            }

            //指导教师搜索(精确查找）
            if (StringUtil.isNotEmpty((String) searchMap.get("teaName"))){
                Teacher teacher = new Teacher();
                teacher.setTeaName(searchMap.get("teaName").toString());
                Teacher find_teacher = teacherMapper.selectOne(teacher);
                criteria.andLike("teacher_id", find_teacher.getTeaId().toString());
            }

            //学生名搜索(精确查找）
            if (StringUtil.isNotEmpty((String) searchMap.get("StuName"))){
                Student student = new Student();
                student.setStuName(searchMap.get("StuName").toString());
                Student find_student = studentMapper.selectOne(student);
                criteria.andLike("student_id", find_student.getId().toString());
            }

            //标题名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("topicTitle"))){
                criteria.andLike("title","%"+(String) searchMap.get("topicTitle")+"%");
            }

            criteria.andLike("depart_id",user.getDepartId().toString());

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

        }
        return topics;
    }

    @Override
    public boolean CreateTopic(Topic topic, Teacher user, List<Integer> majors) {
        topic.setTeacher_id(user.getTeaId());
        topic.setDepart_id(user.getDepartId());
        topic.setStatus(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        topic.setAddtime(new Date());
        //暂时写死TODO
        topic.setGrade(2019);
        int row = topicMapper.insert(topic);

        //重新获取刚刚插入的记录 为了获取该记录的id值
        topic.setAddtime(null);//防止时间不匹配错误 重新查询时不带它
        Topic one = topicMapper.selectOne(topic);
        System.out.println("重新获取的topic:"+one);
        for (Integer majorId:
                majors) {
            Topic_Major topic_major = new Topic_Major();
            topic_major.setTopic_id(one.getTopic_id());
            topic_major.setMajor_id(majorId);
            topic_major.setStatus(0);                   //0：初始 未被专业管理员审核通过状态
            topicMajorMapper.insert(topic_major);
        }

        if(row>0){
            return true;
        }else{
            return false;
        }
//        return false;
    }

    @Override
    public Page<Topic> searchMyTopic(Map searchMap, Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //专业搜索
            if (StringUtil.isNotEmpty(searchMap.get("major").toString())){
                Major major = new Major();
                major.setProNumber(searchMap.get("major").toString());
                Integer major_id = majorMapper.selectOne(major).getMajorId();
                criteria.andLike("student_major_id", major_id.toString());
            }

            //届数搜索
            if (StringUtil.isNotEmpty(searchMap.get("session").toString())){
                criteria.andLike("grade", searchMap.get("session").toString());
            }

            criteria.andLike("teacher_id", user.getTeaId().toString());

            //学生名搜索(精确查找）
            if (StringUtil.isNotEmpty((String) searchMap.get("StuName"))){
                Student student = new Student();
                student.setStuName(searchMap.get("StuName").toString());
                Student find_student = studentMapper.selectOne(student);
                criteria.andLike("student_id", find_student.getId().toString());
            }

            //标题名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("topicTitle"))){
                criteria.andLike("title","%"+(String) searchMap.get("topicTitle")+"%");
            }

            criteria.andLike("depart_id",user.getDepartId().toString());

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
            topic.setTopic_majors(topicMajorMapper.select(topic_major));

            Integer temp = 0;
            Integer tool =0;
            for (Topic_Major item:
                    topic.getTopic_majors()) {
                tool = tool+2;
                temp = temp + item.getStatus();
            }
//            System.out.println("-------------");
//            System.out.println("tool:"+tool);
//            System.out.println("temp:"+temp);
            if (temp == tool && temp>0){
                topic.setGeneral_status(2);
            }else if (temp < tool && temp != 0){
                topic.setGeneral_status(1);
            }else {
                topic.setGeneral_status(0);
            }

        }
        return topics;
    }

    //查询该教师的信息
    @Override
    public Teacher findMyInfo(Teacher user) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(user.getTeaId());
        teacher.setDepart(departMapper.select(new Depart(teacher.getDepartId())).get(0));

        List<Major> majorList = findGuideMajorsByTeaId(user.getTeaId());
        teacher.setMajorList(majorList);

        List<Integer> majors = new ArrayList<>();
        for (int i = 0; i < majorList.size(); i++) {
            majors.add(majorList.get(i).getMajorId());
        }
        teacher.setMajors((ArrayList<Integer>) majors);

        return teacher;
    }


    //教师修改密码
    @Override
    public Boolean TeacherResetPassword(Map searchMap, Teacher user) {
        //1. 先验证输入的原始密码是否正确
        String old_password = searchMap.get("old_password").toString();
        Teacher example = new Teacher();
        example.setTeaId(user.getTeaId());
        Teacher teacher = teacherMapper.selectOne(example);

        if (!teacher.getPassword().equals(old_password)){
            return false;
        }else {
            teacher.setPassword(searchMap.get("new_password").toString());
            teacherMapper.updateByPrimaryKeySelective(teacher);
            return true;
        }
    }

    @Override
    public List<Topic_Major> getTopicMajorById(Integer id) {
        Topic_Major example = new Topic_Major();
        example.setTopic_id(id);
        List<Topic_Major> topic_majors = topicMajorMapper.select(example);
        for (Topic_Major one :
                topic_majors) {
            Major major = new Major();
            major.setMajorId(one.getMajor_id());
            one.setMajor(majorMapper.selectOne(major));
            System.out.println(one);
        }
        return topic_majors;
    }


    //教师删除选题的限选专业
    @Override
    public Boolean deleteTopicMajorById(Integer id) {
        int row = topicMajorMapper.deleteByPrimaryKey(id);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean SelectStudentById(Integer id, Teacher user) {

        Choose choose = chooseMapper.selectByPrimaryKey(id);
        Topic one = topicMapper.selectByPrimaryKey(choose.getTopic_id());
        System.out.println("看看选题是不是已经有人了："+one);

        int i=0,j=0,k=0,l;
        Student stu = new Student();

        if (one.getStudent_id()==null){
            choose.setStatus(1);
            i = chooseMapper.updateByPrimaryKey(choose);

            Student student = studentMapper.selectByPrimaryKey(choose.getStudent_id());
            stu = student;
            Major major = new Major();
            major.setProNumber(student.getProNumber());
            student.setMajor(majorMapper.selectOne(major));
            student.setChoose_status(1);
            j = studentMapper.updateByPrimaryKey(student);

            Topic topic = topicMapper.selectByPrimaryKey(choose.getTopic_id());
            topic.setStudent_id(student.getId());
            topic.setStudent_major_id(student.getMajor().getMajorId());
            k = topicMapper.updateByPrimaryKey(topic);

            Assignment_book assignment_book = new Assignment_book();
            assignment_book.setTea_id(user.getTeaId());
            assignment_book.setStu_id(choose.getStudent_id());
            assignment_book.setTitle(topic.getTitle());
            l = assignmentBookMapper.insert(assignment_book);



        }else {
            return false;
        }

        Integer temp = i+j+k+l;

        if (temp<4){
            return false;
        }else {
            /*
               给学生发送被录取的邮件信息
             */
            if (stu.getEmail()!=null){
                MimeMessage mailMessage=javaMailSender.createMimeMessage();
                //需要借助Helper类
                MimeMessageHelper helper=new MimeMessageHelper(mailMessage);
                String context="<b>"+stu.getStuName()+"同学你好：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，教师"+user.getTeaName()+"已为录取了你的毕业设计志愿， 请您尽快通过登录系统查看。"
                        +"<br><br><b>毕业设计管理系统<br>"+user.getTeaName()+"</b>";
                try {
                    helper.setFrom(sendEmailUsername);
                    helper.setTo(stu.getEmail());
//            helper.setBcc("密送人");
//            helper.setSubject("主题");
                    helper.setSentDate(new Date());//发送时间
                    helper.setText(context,true);
                    //第一个参数要发送的内容，第二个参数是不是Html格式。
                    javaMailSender.send(mailMessage);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    @Override
    public Boolean CancelStudentAdmission(Integer id, Teacher user) {
        Choose temp = new Choose();
        temp.setChoose_id(id);
        Choose choose = chooseMapper.selectByPrimaryKey(temp);
        choose.setStatus(0);
        int i = chooseMapper.updateByPrimaryKey(choose);

        Student student = studentMapper.selectByPrimaryKey(choose.getStudent_id());
        student.setChoose_status(0);
        int j = studentMapper.updateByPrimaryKey(student);

        Topic topic = topicMapper.selectByPrimaryKey(choose.getTopic_id());
        topic.setStudent_id(null);
        topic.setStudent_major_id(null);
        int k = topicMapper.updateByPrimaryKey(topic);

        Integer test = i+j+k;

        if (test<3){
            return false;
        }else {
            return true;
        }
    }

    public List<Major> findGuideMajorsByTeaId(Integer id) {

        List<Major> majors = new ArrayList<>();

        TeaGuideMajor teaGuideMajor = new TeaGuideMajor();
        teaGuideMajor.setTeacher_id(id);
        List<TeaGuideMajor> teaGuideMajorList = teaGuideMajorMapper.select(teaGuideMajor);

        for (TeaGuideMajor teaMajor :
                teaGuideMajorList) {
            Integer major_id = teaMajor.getMajor_id();
            Major example = new Major();
            example.setMajorId(major_id);
            Major major = majorMapper.selectOne(example);
            majors.add(major);
        }

        return majors;
    }

    //教师删除选题信息
    @Override
    public Boolean deleteTopicById(Integer id) {
        int i = topicMapper.deleteByPrimaryKey(id);
        Topic_Major topic_major = new Topic_Major();
        topic_major.setTopic_id(id);
        int j = topicMajorMapper.delete(topic_major);
        if(i+j==2){
            return true;
        }else{
            return false;
        }
    }


    //查询老师录取的学生
    //用于页面的方法
    @Override
    public Page<Topic> searchMyStudents(Map searchMap,Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            criteria.andLike("teacher_id",user.getTeaId().toString());
            criteria.andIsNotNull("student_id");

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
        

        for (Topic topic : topics) {
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
        }
        return topics;
    }

    @Override
    public Page<Topic> searchStudentsFile(Map searchMap, Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //专业搜索
            if (StringUtil.isNotEmpty(searchMap.get("major").toString())){
                criteria.andLike("student_major_id", searchMap.get("major").toString());
            }

            criteria.andLike("teacher_id",user.getTeaId().toString());
            criteria.andIsNotNull("student_id");

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
                FileData fileData = new FileData();
                fileData.setStudentid(topic.getStudent().getStuNumber());
                topic.getStudent().setFileData(fileDataMapper.selectOne(fileData));
                if (topic.getStudent().getFileData()==null){
                    topic.getStudent().setFileData(new FileData());
                }
                topic.setProgress(progressMapper.selectOne(new Progress(topic.getStudent_id())));
                if (topic.getProgress()==null){
                    Progress progress = new Progress(topic.getStudent_id());
                    progress.setProgress(0);
                    topic.setProgress(progress);
                }
            }else {
                topic.setStudent(new Student());
                topic.getStudent().setaClass(new Class());
            }
            Choose one = new Choose();
            one.setTopic_id(topic.getTopic_id());
            topic.setChooses(chooseMapper.select(one));
        }
        return topics;
    }

    //查看你学生的选题状态
    @Override
    public boolean findStudentChooseStatus(Integer chooseId) {
        Choose choose = chooseMapper.selectByPrimaryKey(chooseId);
        Student student = studentMapper.selectByPrimaryKey(choose.getStudent_id());
        if (student.getChoose_status()==0){
            // 表示该学生未被选中过了，能在被其他老师选中
            return true;
        }else {
            return false;
        }
    }

    //邮件提醒学生提交文件
    @Override
    public Boolean RemindMyStuById(Integer stuId, Teacher user) {
        MimeMessage mailMessage=javaMailSender.createMimeMessage();
        Student student = studentMapper.selectByPrimaryKey(stuId);
        if (!student.getEmail().isEmpty()){
            //需要借助Helper类
            MimeMessageHelper helper=new MimeMessageHelper(mailMessage);
            String context="<b>"+student.getStuName()+"同学你好：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，注意文件提交截至时间，以免误期，请您尽快通过登录系统查看。"
                    +"<br><br><b>毕业设计管理系统<br>"+user.getTeaName()+"</b>";
            try {
                helper.setFrom(sendEmailUsername);
                helper.setTo(student.getEmail());
                helper.setSentDate(new Date());//发送时间
                helper.setText(context,true);
                //第一个参数要发送的内容，第二个参数是不是Html格式。
                javaMailSender.send(mailMessage);

                return true;

            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public List<LogNote> searchMyLogNotes(Teacher user) {
        LogNote logNote_example = new LogNote();
        logNote_example.setTea_id(user.getTeaId());
        List<LogNote> logNoteList = logNoteMapper.select(logNote_example);
        Collections.reverse(logNoteList);

        System.out.println("该教师的学生日志");
        for (LogNote log_one:logNoteList) {
            log_one.setStudent(studentMapper.selectByPrimaryKey(log_one.getStu_id()));
            System.out.println(log_one);
        }

        return logNoteList;
    }

    @Override
    public Page<Guidance> searchMyGuidanceRecord(Map searchMap, Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Guidance.class);//指定查询的表

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            criteria.andLike("teacher_id",user.getTeaId().toString());

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Guidance> guidances = (Page<Guidance>) guidanceMapper.selectByExample(example);

        for (Guidance one : guidances) {
            one.setTeacher(teacherMapper.selectByPrimaryKey(one.getTeacher_id()));
            one.setMajor(majorMapper.selectByPrimaryKey(one.getMajor_id()));
        }

        return guidances;
    }

    @Override
    public List<GuidanceRecord> searchGuidanceDetails(Integer id, Teacher user) {
        GuidanceRecord guidanceRecord_example = new GuidanceRecord();
        guidanceRecord_example.setGuidance_id(id);
        List<GuidanceRecord> guidanceRecordList = guidanceRecordMapper.select(guidanceRecord_example);
        for (GuidanceRecord one :
                guidanceRecordList) {
            one.setTeacher(teacherMapper.selectByPrimaryKey(one.getTea_id()));
            one.setStudent(studentMapper.selectByPrimaryKey(one.getStu_id()));
            one.setMajor(majorMapper.selectByPrimaryKey(one.getMajorId()));
        }
        return guidanceRecordList;
    }

    @Override
    public Boolean SaveGuidanceLog(Map guidanceMap, Teacher user) {
        List<Integer> stu_id = (List<Integer>) guidanceMap.get("students");
        Calendar calendar = Calendar.getInstance();
        Guidance guidance = new Guidance();
        guidance.setSession(calendar.get(Calendar.YEAR));
        guidance.setTeacher_id(user.getTeaId());
        guidance.setGuidance_count(stu_id.size());
        Major major_example = new Major();
        major_example.setProNumber(studentMapper.selectByPrimaryKey(stu_id.get(0)).getProNumber());
        Major major = majorMapper.selectOne(major_example);
        guidance.setMajor_id(major.getMajorId());
        final int i = guidanceMapper.insertSelective(guidance);
        System.out.println(guidance.getId());

        GuidanceRecord guidanceRecord = new GuidanceRecord();
        guidanceRecord.setSession(guidance.getSession());
        guidanceRecord.setGuidance_id(guidance.getId());
        guidanceRecord.setMajorId(guidance.getMajor_id());
        guidanceRecord.setTea_id(guidance.getTeacher_id());
        guidanceRecord.setAddress(guidanceMap.get("address").toString());

        try {
            System.out.println(guidanceMap.get("time").toString());
            Date insert_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(guidanceMap.get("time").toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式
            //format():将给定的 Date 格式化为日期/时间字符串。即：date--->String
            String dateString = formatter.format(insert_time);
            System.out.println("转换为String格式：" + dateString);
            guidanceRecord.setTime(dateString);
            try {
                Date date = formatter.parse(dateString);//parse():String--->date
                System.out.println("转化为date格式：" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        guidanceRecord.setDetail(guidanceMap.get("detail").toString());
        for (Integer one_id :
                stu_id) {
            guidanceRecord.setStu_id(one_id);
            guidanceRecordMapper.insert(guidanceRecord);
        }
        return true;
    }

    @Override
    public TeaTarget searchMyTargetData(Teacher user) {
        TeaTarget teaTarget = new TeaTarget();
        teaTarget.setTea_id(user.getTeaId());
        TeaTarget target = teaTargetMapper.selectOne(teaTarget);
        if (target!=null) {
            Example example = new Example(Topic.class);//指定查询的表tb_topic
            Example.Criteria criteria = example.createCriteria();//创建查询条件
            criteria.andLike("teacher_id", user.getTeaId().toString());
            criteria.andIsNotNull("student_id");
            int one_tea_stu_num = topicMapper.selectByExample(example).size();  //逐个教师的学生人数
            System.out.println(user.getTeaName() + "的录取人数：" + one_tea_stu_num);
            target.setStu_num_per(one_tea_stu_num * 100 / target.getStu_num());

            //选题数量部分
            Example example1 = new Example(Topic.class);//指定查询的表tb_topic
            Example.Criteria criteria1 = example1.createCriteria();//创建查询条件
            criteria1.andLike("teacher_id", user.getTeaId().toString());
            int one_tea_topic_num = topicMapper.selectByExample(example1).size();   //逐个教师的选题人数
            System.out.println(user.getTeaName() + "的选题数：" + one_tea_topic_num);
            target.setTopic_num_per(one_tea_topic_num * 100 / target.getTopic_num());

            //指导记录部分
            Example example2 = new Example(Guidance.class);//指定查询的表tb_topic
            Example.Criteria criteria2 = example2.createCriteria();//创建查询条件
            criteria2.andLike("teacher_id", user.getTeaId().toString());
            int one_tea_guidance_per = guidanceMapper.selectByExample(example2).size();
            System.out.println(user.getTeaName() + "的指导记录数量：" + one_tea_guidance_per);
            target.setGuidance_num_per(one_tea_guidance_per * 100 / target.getGuidance_num());
            //推优名额部分 TODO

            return target;
        }else{
            return new TeaTarget();
        }
    }

    @Override
    public Assignment_book findStuAssignmentBookById(Integer id) {
        Assignment_book ass_book_example = new Assignment_book();
        ass_book_example.setStu_id(id);
        Assignment_book assignment_book = assignmentBookMapper.selectOne(ass_book_example);

        //放入文件位置
        Student student = studentMapper.selectByPrimaryKey(assignment_book.getStu_id());
        String stuNumber = student.getStuNumber();
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
        final String depName = departMapper.selectByPrimaryKey(student.getStuDepartId()).getDepName();

        Major major_temp = new Major();
        major_temp.setProNumber(student.getProNumber());
        final String majorName = majorMapper.selectOne(major_temp).getMajorName();

        Class aClass_temp = new Class();
        aClass_temp.setClassChar(student.getStuClass());
        final String className = classMapper.selectOne(aClass_temp).getClassName();

        String department = depName+"+"+majorName+"+"+className;
//        System.out.println("整合后的部门："+department);

        assignment_book.setDepartment(department);

        return assignment_book;
    }

    @Override
    public Opening_report findOpeningReportById(Integer id) {
        Opening_report opening_report_example = new Opening_report();
        opening_report_example.setStu_id(id);
        Opening_report opening_report  = openingReportMapper.selectOne(opening_report_example);

        //放入文件位置
        Student student = studentMapper.selectByPrimaryKey(opening_report.getStu_id());
        String stuNumber = student.getStuNumber();
        final FileData fileDataExample = new FileData();
        fileDataExample.setStudentid(stuNumber);
        final String ktfile = fileDataMapper.selectOne(fileDataExample).getKtfile();
        opening_report.setFile_location(ktfile);

        //放入教师姓名
        opening_report.setTea_name(teacherMapper.selectByPrimaryKey(opening_report.getTea_id()).getTeaName());

        //放入学生姓名
        opening_report.setStu_name(studentMapper.selectByPrimaryKey(opening_report.getStu_id()).getStuName() +"+"
                +stuNumber);

        opening_report.setAssignment_book(findStuAssignmentBookById(id));

        return opening_report;
    }

    @Override
    public Boolean UpdateStuOpenReport(Opening_report opening_report) {
        final int i = openingReportMapper.updateByPrimaryKey(opening_report);
        if (i!=1){
            return false;
        }
        return true;
    }

    @Override
    public MidTermExam findMidTermExamById(Integer id) {
        MidTermExam midTermExam_example = new MidTermExam();
        midTermExam_example.setStu_id(id);
        MidTermExam midTermExam = midTermExamMapper.selectOne(midTermExam_example);

        //放入文件位置
        Student student = studentMapper.selectByPrimaryKey(midTermExam.getStu_id());
        String stuNumber = student.getStuNumber();
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
        midTermExam.setAssignment_book(findStuAssignmentBookById(id));

        return midTermExam;
    }

    @Override
    public Boolean UpdateMidTermExam(MidTermExam midTermExam) {
        final int i = midTermExamMapper.updateByPrimaryKey(midTermExam);
        if (i!=1){
            return false;
        }
        return true;
    }

    @Override
    public OpenAnswerTea getMyOpenAnswerInfo(Teacher user) {
        OpenAnswerTea oat_example = new OpenAnswerTea();
        oat_example.setTea_id(user.getTeaId());
        OpenAnswerTea openAnswerTea = openAnswerTeaMapper.selectOne(oat_example);
        openAnswerTea.setTeacher(user);
        openAnswerTea.setOpenAnswer(openAnswerMapper.selectByPrimaryKey(openAnswerTea.getOpen_answer_id()));
        openAnswerTea.setMajor(majorMapper.selectByPrimaryKey(openAnswerTea.getMajor_id()));
        OpenAnswerTea answerTea = new OpenAnswerTea();
        answerTea.setOpen_answer_id(openAnswerTea.getOpen_answer_id());
        List<OpenAnswerTea> teas = openAnswerTeaMapper.select(answerTea);
        List<Teacher> teachers = new ArrayList<>();
        for (OpenAnswerTea one :teas) {
            teachers.add(teacherMapper.selectByPrimaryKey(one.getTea_id()));
        }
        openAnswerTea.getOpenAnswer().setTeacherList(teachers);

        return openAnswerTea;
    }

    @Override
    public boolean SubmitOpenAnswerAddress(String address, Teacher user) {
        OpenAnswer openAnswer_example  = new OpenAnswer();
        openAnswer_example.setHeadman_id(user.getTeaId());
        OpenAnswer openAnswer = openAnswerMapper.selectOne(openAnswer_example);
        openAnswer.setAddress(address);
        int i = openAnswerMapper.updateByPrimaryKey(openAnswer);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean SubmitOpenAnswerTime(String time, Teacher user) {
        OpenAnswer openAnswer_example  = new OpenAnswer();
        openAnswer_example.setHeadman_id(user.getTeaId());
        OpenAnswer openAnswer = openAnswerMapper.selectOne(openAnswer_example);
        try {
            Date insert_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式
            //format():将给定的 Date 格式化为日期/时间字符串。即：date--->String
            String dateString = formatter.format(insert_time);
            System.out.println("转换为String格式：" + dateString);
            openAnswer.setTime(dateString);
            try {
                Date date = formatter.parse(dateString);//parse():String--->date
                System.out.println("转化为date格式：" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = openAnswerMapper.updateByPrimaryKey(openAnswer);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Student> findOpenAnswerStuList(Map searchMap, Teacher user) {

        OpenAnswerTea oat_example = new OpenAnswerTea();
        oat_example.setTea_id(user.getTeaId());
        OpenAnswerTea openAnswerTea = openAnswerTeaMapper.selectOne(oat_example);

        OpenAnswerStu oas_example = new OpenAnswerStu();
        oas_example.setOpen_answer_id(openAnswerTea.getOpen_answer_id());
        List<OpenAnswerStu> openAnswerStus = openAnswerStuMapper.select(oas_example);
        List<Student> students = new ArrayList<>();
        for (OpenAnswerStu one : openAnswerStus) {
            Student student = studentMapper.selectByPrimaryKey(one.getStu_id());
            Major major_example = new Major();
            major_example.setProNumber(student.getProNumber());
            student.setMajor(majorMapper.selectOne(major_example));

            Topic topic_example = new Topic();
            topic_example.setStudent_id(student.getId());
            Integer guidance_tea_id = null;
            if (topicMapper.selectOne(topic_example)!=null){
                guidance_tea_id = topicMapper.selectOne(topic_example).getTeacher_id();
                student.setGuid_tea(teacherMapper.selectByPrimaryKey(guidance_tea_id));
            }else {
                student.setGuid_tea(new Teacher());
            }

            Class aClass = new Class();
            aClass.setClassChar(student.getStuClass());
            student.setaClass(classMapper.selectOne(aClass));
            OpenAnswerRecord openAnswerRecord = new OpenAnswerRecord();
            openAnswerRecord.setStu_id(student.getId());
            student.setOpenAnswerRecord(openAnswerRecordMapper.selectOne(openAnswerRecord));
            students.add(student);
        }
        return students;
    }

    @Override
    public OpenAnswerStu GetStuOpenAnswerById(Integer id) {
        Student user = studentMapper.selectByPrimaryKey(id);

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
    public List<OpenAnswerQuestion> GetStuOpenAnswerQuestionListById(Integer id) {
        Student user = studentMapper.selectByPrimaryKey(id);
        OpenAnswerQuestion oaq_example = new OpenAnswerQuestion();
        oaq_example.setStu_id(user.getId());
        List<OpenAnswerQuestion> questions = openAnswerQuestionMapper.select(oaq_example);
        for (OpenAnswerQuestion one : questions) {
            one.setTeacher(teacherMapper.selectByPrimaryKey(one.getTea_id()));
        }
        return questions;
    }

    @Override
    public OpenAnswerRecord GetStuOpenAnswerRecordById(Integer id) {
        OpenAnswerRecord oar_example = new OpenAnswerRecord();
        oar_example.setStu_id(id);
        OpenAnswerRecord openAnswerRecord = openAnswerRecordMapper.selectOne(oar_example);
        return openAnswerRecord;
    }

    @Override
    public Boolean SubmitStuOpenAnswerRecord(Map map, Teacher user) {
        OpenAnswerRecord example = new OpenAnswerRecord();
        example.setStu_id(Integer.valueOf(map.get("stu_id").toString()));
        OpenAnswerRecord openAnswerRecord = openAnswerRecordMapper.selectOne(example);
        openAnswerRecord.setResult(map.get("result").toString());
        openAnswerRecord.setAttitude(map.get("attitude").toString());
        openAnswerRecord.setCollecting_status(map.get("collecting_status").toString());
        openAnswerRecord.setUnderstanding_status(map.get("understanding_status").toString());
        openAnswerRecord.setOpen_report_status(map.get("open_report_status").toString());
        openAnswerRecord.setRemark(map.get("remark").toString());
        SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateF = dsf.format(new Date());
        System.out.println(dateF);
        openAnswerRecord.setExam_time(dateF);
        openAnswerRecord.setProgress(2);
        int i = openAnswerRecordMapper.updateByPrimaryKey(openAnswerRecord);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean UpdateStuAssBook(Assignment_book assignmentBook) {
        final int i = assignmentBookMapper.updateByPrimaryKey(assignmentBook);
        if (i!=1){
            return false;
        }
        return true;
    }

    //查找教师录取的学生
    //用于后端处理数据的方法
    public List<Student> searchMyChooseStudents(Teacher user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic
        Example.Criteria criteria = example.createCriteria();//创建查询条件
        criteria.andLike("teacher_id",user.getTeaId().toString());
        criteria.andIsNotNull("student_id");
        List<Topic> topicList = topicMapper.selectByExample(example);

        List<Student> studentList = new ArrayList<>();
        for (Topic one : topicList
                ) {
            Student student = studentMapper.selectByPrimaryKey(one.getStudent_id());
            studentList.add(student);
        }

        return studentList;
    }

}

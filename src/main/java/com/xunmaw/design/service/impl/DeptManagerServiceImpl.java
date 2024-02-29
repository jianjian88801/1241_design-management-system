package com.xunmaw.design.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunmaw.design.dao.*;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.DeptManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/3/19 16:40
 * Description:系部管理员Service实现类
 */

@Service
public class DeptManagerServiceImpl implements DeptManagerService {
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
    private ChooseMapper chooseMapper;
    @Autowired
    private FileDataMapper fileDataMapper;


    @Override
    public List<Student> findAllStudents(Administrator user) {
        Student example = new Student();
        example.setStuDepartId(user.getDepart_id());
        List<Student> students = studentMapper.select(example);
//        List<Student> Students = studentMapper.selectAll();
        for (Student student :
                students) {
            student.setDepart(departMapper.select(new Depart(student.getStuDepartId())).get(0));
            student.setMajor(majorMapper.selectOne(new Major(student.getProNumber())));
            student.setaClass(classMapper.selectOne(new Class(student.getStuClass())));
        }
        return students;
    }

    @Override
    public Page<Student> searchAllStudent(Map searchMap, Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Student.class);//指定查询的表tb_student

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //系部搜索
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("StuDepartId", user.getDepart_id().toString());
            }

            //专业搜索
            if (StringUtil.isNotEmpty(searchMap.get("major").toString())){
                criteria.andLike("ProNumber", searchMap.get("major").toString());
            }

            //届数搜索
            if (StringUtil.isNotEmpty(searchMap.get("session").toString())){
                criteria.andLike("session", searchMap.get("session").toString());
            }

            //状态搜索
            if (StringUtil.isNotEmpty(searchMap.get("status").toString())){
                criteria.andLike("state", searchMap.get("status").toString());
            }

            //学号搜索
            if (StringUtil.isNotEmpty(searchMap.get("stuNumber").toString())){
                criteria.andLike("StuNumber", searchMap.get("stuNumber").toString());
            }

            //姓名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))){
                criteria.andLike("StuName","%"+(String) searchMap.get("name")+"%");
            }

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Student> students = (Page<Student>) studentMapper.selectByExample(example);
        for (Student student :
                students) {
            student.setDepart(departMapper.select(new Depart(student.getStuDepartId())).get(0));
            student.setMajor(majorMapper.selectOne(new Major(student.getProNumber())));
            student.setaClass(classMapper.selectOne(new Class(student.getStuClass())));
        }
        return students;
    }

    @Override
    public List<Major> getMajorsByDep(Administrator user) {
        Major major = new Major();
        major.setMajorDepartId(user.getDepart_id());
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
    public List<Class> getAClassByMajorId(Administrator user, String id, Integer session) {
        Major major = new Major();

        if (id!=null){
            major.setProNumber(id);
        }
        Major major1 = majorMapper.selectOne(major);

        Class class1 = new Class();
        class1.setMajorId(major1.getMajorId());
        class1.setSession(session);

        List<Class> classList = classMapper.select(class1);
        return classList;
    }

    @Override
    public Boolean delStudent(List<Integer> ids) {
        for (Integer id:ids) {
            studentMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public Boolean updateStuStatus(Integer status, Integer id) {
        Student student = new Student();
        student.setId(id);
        student.setState(status);
        int row = studentMapper.updateByPrimaryKeySelective(student);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean ResetStuPassword(Integer id) {
        Student student = new Student();
        student.setId(id);
        student.setPassword("123456");
        int row = studentMapper.updateByPrimaryKeySelective(student);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    // 查询选题
    @Override
    public Page<Topic> searchAllTopic(Map searchMap, Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //系部管理员特有
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("depart_id", user.getDepart_id().toString());
            }

//            criteria.andIsNotNull("student_id");

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

    // 查询选题
    @Override
    public Page<Topic> searchAllOKTopic(Map searchMap,Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Topic.class);//指定查询的表tb_topic

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //系部管理员特有
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("depart_id", user.getDepart_id().toString());
            }

            criteria.andIsNotNull("student_id");

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
    public Page<Topic> searchStudentsFile(Map searchMap, Administrator user) {
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

            criteria.andLike("depart_id",user.getDepart_id().toString());

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
    public Page<Teacher> searchAllTeacher(Map searchMap,Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Teacher.class);//指定查询的表tb_teacher

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件


            //系部搜索
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("DepartId", user.getDepart_id().toString());
            }

            //姓名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("teaName"))){
                criteria.andLike("TeaName","%"+(String) searchMap.get("teaName")+"%");
            }

            //工号搜索
            if (StringUtil.isNotEmpty(searchMap.get("teaNo").toString())){
                criteria.andLike("TeaNo", searchMap.get("teaNo").toString());
            }

            //状态搜索
            if (StringUtil.isNotEmpty(searchMap.get("status").toString())){
                criteria.andLike("status", searchMap.get("status").toString());
            }

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Teacher> teachers = (Page<Teacher>) teacherMapper.selectByExample(example);
        for (Teacher teacher :
                teachers) {
            teacher.setDepart((Depart) departMapper.select(new Depart(teacher.getDepartId())).get(0));
        }
        return teachers;
    }

    @Override
    public boolean addTeacher(Teacher teacher, Administrator user) {

        if (teacher.getDepartId()!=user.getDepart_id()){
            return false;
        }

        teacher.setStatus(1);//初始设置为正常状态
        teacher.setPassword("123456");//设置初始密码
        teacher.setDepartName(departMapper.selectOne(new Depart(teacher.getDepartId())).getDepName());

        System.out.println("最终进数据库的teacher："+teacher);
        int row = teacherMapper.insert(teacher);

        Teacher teacher1 = new Teacher();//检索模板
        teacher1.setTeaNo(teacher.getTeaNo());

        Teacher teacher2 = teacherMapper.selectOne(teacher1);//刚刚插入的教师记录

        for (int i = 0; i < teacher.getMajors().size(); i++) {
            TeaGuideMajor teaGuideMajor = new TeaGuideMajor();
            teaGuideMajor.setMajor_id(teacher.getMajors().get(i));
            teaGuideMajor.setTeacher_id(teacher2.getTeaId());
            teaGuideMajorMapper.insert(teaGuideMajor);
        }

        if(row>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Page<Major> searchAllMajors(Map searchMap,Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Major.class);//指定查询的表tb_major

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //学号搜索
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("majorDepartId", user.getDepart_id().toString());
            }

            //姓名模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("majorName"))){
                criteria.andLike("majorName","%"+(String) searchMap.get("majorName")+"%");
            }

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Major> majors = (Page<Major>) majorMapper.selectByExample(example);

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
    public Page<Administrator> searchAllMajorAdministrator(Map searchMap, Administrator user) {
        //通用Mapper多条件搜索，标准写法
        Example example = new Example(Administrator.class);//指定查询的表tb_administrator

        // 初始化分页条件
        int pageNum = 1;
        int pageSize = 10;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();//创建查询条件

            //系部（学院）管理员身份
            if (StringUtil.isNotEmpty(user.getDepart_id().toString())){
                criteria.andLike("depart_id", user.getDepart_id().toString());
            }

            //专业名称模糊搜索
            if (StringUtil.isNotEmpty((String) searchMap.get("majorName"))){
                criteria.andLike("name","%"+(String) searchMap.get("majorName")+"%");
            }

            //系部（学院）管理员身份
            if (StringUtil.isNotEmpty(searchMap.get("type").toString())){
                criteria.andLike("type", searchMap.get("type").toString());
            }

            //分页
            if((Integer) searchMap.get("pageNum")!=null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if ((Integer) searchMap.get("pageSize") !=null) {
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);//使用PageHelper插件完成分页
        Page<Administrator> administrators = (Page<Administrator>) administratorMapper.selectByExample(example);

        for (Administrator manager :
                administrators) {
            Depart depart = new Depart();
            depart.setDepId(manager.getDepart_id());
            manager.setDepart(departMapper.selectByPrimaryKey(depart));
            System.out.println(manager);
        }

        return administrators;
    }

    @Override
    public boolean addStudent(Student student, Administrator user) {

        if (student.getStuDepartId() != user.getDepart_id()){
            return false;
        }

        student.setState(1);//初始设置为正常状态
        student.setPassword("123456");//设置初始密码

        int row = studentMapper.insert(student);
        if(row>0){
            return true;
        }else{
            return false;
        }
    }


}

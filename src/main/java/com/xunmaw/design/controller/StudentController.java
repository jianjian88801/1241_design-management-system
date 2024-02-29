package com.xunmaw.design.controller;

import com.github.pagehelper.Page;
import com.xunmaw.design.Util.word2vec.Segment;
import com.xunmaw.design.Util.word2vec.Word2Vec;
import com.xunmaw.design.common.MessageConstant;
import com.xunmaw.design.common.PageResult;
import com.xunmaw.design.common.Result;
import com.xunmaw.design.common.StatusCode;
import com.xunmaw.design.dao.*;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.FileDataService;
import com.xunmaw.design.service.ManagerService;
import com.xunmaw.design.service.StudentService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/3/28 21:15
 * Description:
 */

@Controller
@RestController
@RequestMapping("/Student")
public class StudentController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private StudentService studentService;
    @Autowired
    private ManagerService managerService;

    @Autowired
    private DepartMapper departMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private FileDataMapper fileDataMapper;
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    private LogNoteMapper logNoteMapper;


    @PostMapping("/upload/file2")
    @ResponseBody
    public void uploadMap(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, int fileType) {

        System.out.println("文件类型："+fileType);

        if (multipartFile.isEmpty()) {

        }
        String realfile = null;
        if (fileType == 1) {
            realfile = "任务书";
        }else if (fileType == 2){
            realfile = "开题报告";
        }else if (fileType == 3){
            realfile = "文献综述";
        }else if (fileType == 4){
            realfile = "外文翻译";
        }else if (fileType == 5){
            realfile = "相关文件";
        }else if (fileType == 6){
            realfile = "查重报告";
        }else if (fileType == 7){
            realfile = "毕业论文";
        }else if (fileType == 8){
            realfile = "中期检查";
        }

        long size = multipartFile.getSize();
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType(); // image/png;image/jpg;image/gif
        //if (!contentType.equals("png|jpg|gif")) {
        //    return "文件类型不正确"; // 伪代码，不正确的代码
        //}
        // 1：获取用户指定的文件夹。问这个文件夹为什么要从页面上传递过来？
        // 原因是：做隔离，不同业务，不同文件放在不同的目录中
        Student user = (Student) request.getSession().getAttribute("user");
        Depart depart = departMapper.selectByPrimaryKey(user.getStuDepartId());
        String depName = depart.getDepName();
        Major major = majorMapper.selectOne(new Major(user.getProNumber()));
        String majorName = major.getMajorName();
        String session = user.getSession() + "届";
        String dir = session + "/" + depName + "/" + majorName +"/" + realfile ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datePath = dateFormat.format(new Date());
        Map<String, Object> map = studentService.uploadImgMap(multipartFile, dir);


        FileData example = new FileData();
        example.setStudentid(user.getStuNumber());
        FileData data = fileDataMapper.selectOne(example);
        System.out.println(user.getStuNumber()+"学号的记录："+data);

        if(data == null){
            //data 值为空 表示filedata 表中没有存在该学生学号的记录 是第一次上传文件
            //需要进行insert 创建一条新的记录
            FileData fileData = new FileData();
            fileData.setStudentid(user.getStuNumber());
            if (fileType == 1) {
                fileData.setJobfile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setJobtime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,1);
            }else if (fileType == 2){
                fileData.setKtfile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setKttime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,2);
            }else if (fileType == 3){
                fileData.setShufile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setShutime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,3);
            }else if (fileType == 4){
                fileData.setYifile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setYitime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,4);
            }else if (fileType == 5){
                fileData.setXgfile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setXgtime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,5);
            }else if (fileType == 6){
                fileData.setChafile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setChatime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,6);
            }else if (fileType == 7){
                fileData.setBsfile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setBstime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,7);
            }else if (fileType == 8){
                fileData.setZqfile(map.get("rpath").toString());
                System.out.println(map.get("rpath").toString());
                fileData.setZqtime(datePath);
                fileDataMapper.insert(fileData);
                insertLogNote(user, datePath,8);
            }
        }else {
            //data值不为空 表示表中存在记录
            //学生之前已经上传过文件 需要对记录进行更新操作。
            if (fileType == 1) {
                data.setJobfile(map.get("rpath").toString());
                data.setJobtime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,1);
            }else if (fileType == 2){
                data.setKtfile(map.get("rpath").toString());
                data.setKttime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,2);
            }else if (fileType == 3){
                data.setShufile(map.get("rpath").toString());
                data.setShutime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,3);
            }else if (fileType == 4){
                data.setYifile(map.get("rpath").toString());
                data.setYitime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,4);
            }else if (fileType == 5){
                data.setXgfile(map.get("rpath").toString());
                data.setXgtime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,5);
            }else if (fileType == 6){
                data.setChafile(map.get("rpath").toString());
                data.setChatime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,6);
            }else if (fileType == 7){
                data.setBsfile(map.get("rpath").toString());
                data.setBstime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,7);
            }else if (fileType == 8){
                data.setZqfile(map.get("rpath").toString());
                data.setZqtime(datePath);
                fileDataMapper.updateByPrimaryKey(data);
                insertLogNote(user, datePath,8);
            }

        }



        //return studentService.uploadImgMap(multipartFile, dir);
    }

    private void insertLogNote(Student user, String datePath, int filetype) {
        LogNote logNote = new LogNote();
        logNote.setStu_id(user.getId());
        Topic topic_example = new Topic();
        topic_example.setStudent_id(user.getId());
        logNote.setTea_id(topicMapper.selectOne(topic_example).getTeacher_id());

        if (filetype==1){
            logNote.setTypename("选题");
            logNote.setDetails("上传选题文件");
        }else if (filetype==2){
            logNote.setTypename("任务书");
            logNote.setDetails("上传任务书");
        }else if (filetype==3){
            logNote.setTypename("开题报告");
            logNote.setDetails("上传开题报告");
        }else if (filetype==4){
            logNote.setTypename("中期检查");
            logNote.setDetails("上传中期检查文件");
        }else if (filetype==5){
            logNote.setTypename("论文评阅");
            logNote.setDetails("上传论文评阅");
        }else if (filetype==6){
            logNote.setTypename("论文答辩");
            logNote.setDetails("上传论文答辩文件");
        }else if (filetype==8){
            logNote.setTypename("中期检查");
            logNote.setDetails("上传中期检查文件");
        }else{
            logNote.setTypename("答辩终稿");
            logNote.setDetails("上传答辩终稿");
        }

        logNote.setDate(datePath);
        logNoteMapper.insert(logNote);
    }

    @RequestMapping("/searchAllFileData")
    public Result searchAllFileData(HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        FileData fileData = fileDataService.searchAllFileData(user);
        System.out.println("学生的文件:"+fileData);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,fileData);
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, HttpServletRequest request,int fileType) throws UnsupportedEncodingException {

        System.out.println("文件类型:"+fileType);

        Student user = (Student) request.getSession().getAttribute("user");
        FileData example = new FileData();
        example.setStudentid(user.getStuNumber());
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

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
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





    @RequestMapping("/searchAllTopic")
    public PageResult searchAllTopic(@RequestBody Map searchMap, HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        System.out.println("Topic-searchMap为:"+searchMap);
        Page<Topic> topics = studentService.searchAllTopic(searchMap,user);
        for (Topic topic :
                topics) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    @RequestMapping("/searchAllGuidance")
    public PageResult searchAllGuidance(@RequestBody Map searchMap, HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        Page<GuidanceRecord> guidanceRecords = studentService.searchAllGuidance(searchMap,user);
        for (GuidanceRecord one :
                guidanceRecords) {
            System.out.println(one);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,guidanceRecords.getResult(), guidanceRecords.getTotal());
    }

    //根据id查找选题信息
    @RequestMapping("/findTopicById")
    public Result findTopicById(Integer id){
        System.out.println("需要查找的选题Id："+id);
        Topic topic = managerService.findTopicById(id);
        System.out.println("根据id找到的选题信息为："+topic);
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,topic);
    }

    //查询该学生的任务书
    @RequestMapping("/findMyAssignmentBook")
    public Result findMyAssignmentBook(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        Assignment_book assignment_book = studentService.findMyAssignmentBook(user.getId(),user);
        if (assignment_book!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,assignment_book);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    //查询该学生的开题报告
    @RequestMapping("/findOpeningReportById")
    public Result findOpeningReportById(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        Opening_report opening_report = studentService.findOpeningReportById(user.getId(),user);
        if (opening_report!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,opening_report);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    //查询该学生的中期检查
    @RequestMapping("/findMidTermExamById")
    public Result findMidTermExamById(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        MidTermExam midTermExam = studentService.findMidTermExamById(user.getId(),user);
        if (midTermExam!=null){
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,midTermExam);
        }
        return new Result(false,StatusCode.ERROR,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS, null);
    }

    //查询该学生的信息
    @RequestMapping("/findMyInfo")
    public Result findMyInfo(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        Student student = managerService.findStuById(user.getId());
        System.out.println("findStuById:\n"+student);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,student);
    }

    //select器中填充系别数据
    @RequestMapping("/getAllDeptToSelect")
    public Result searchAllDeptToSelect(){
        List<Depart> departs = managerService.searchAllDeptToSelect();
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,departs);
    }

    //select器中根据系别id填充专业数据
    @RequestMapping(value = "/getMajorsByDepId")
    public Result getMajorsByDepId(Integer id){
        List<Major> majors = managerService.getMajorsByDepId(id);
        for (Major major : majors) {
            System.out.println(major);
        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,majors);
    }

    //select器中根据专业id填充班级数据
    @RequestMapping(value = "/getAClassByMajorId")
    public Result getAClassByMajorId(String id,Integer session){
//        System.out.println("获取的session："+session);
//        System.out.println("获取的专业id："+id);
        List<Class> classes = managerService.getAClassByMajorId(id,session);
//        for (Class aclass :
//                classes) {
//            System.out.println(aclass);
//        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,classes);
    }


    @RequestMapping("/updateStudent")
    public Result update(@RequestBody Student student){
//        System.out.println("所需更新的："+student);
        Boolean flag = managerService.updateStudent(student);
//        System.out.println("更新成功："+flag);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_UPDATE_SUCCESS);
    }

    //查找学生自己的选题情况
    @RequestMapping("/findMyWishTopic")
    public Result findMyWishTopic(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        List<Choose> MyTopicList = studentService.findMyWishTopic(user);
        System.out.println(user.getStuName()+"的选题信息为：\n");
        System.out.println("-------------");
        for (Choose choose: MyTopicList
        ) {
            System.out.println(choose);
        }
        System.out.println("-------------");
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,MyTopicList);
    }

    @RequestMapping("/findTeaById")
    public Result findTeaById(Integer id){
//        System.out.println("需要查找的教师Id："+id);
        Teacher teacher = managerService.findTeaById(id);
        System.out.println(teacher);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,teacher);
    }

    //根据id查找选题情况
    @RequestMapping("/findTopicChooseById")
    public Result findTopicChooseById(Integer id){
        System.out.println("需要查找的选题Id："+id);
        List<Choose> chooseList = managerService.findTopicChooseById(id);
        System.out.println("根据id找到的选题信息为：\n");
        for (Choose choose: chooseList
        ) {
            System.out.println(choose);
        }
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,chooseList);
    }



    @RequestMapping("/ChooseTopic")
    public Result ChooseTopic(@RequestBody List<Integer> ids,HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        System.out.println("选了个啥："+studentService.findMyWishTopic(user));
        if(studentService.findMyWishTopic(user).isEmpty()){
            System.out.println(ids);
            Boolean flag = studentService.ChooseTopic(ids,user);
            if (flag==true){
                return new Result(true,StatusCode.OK,MessageConstant.STUDENT_DELETE_SUCCESS);
            }else {
                return new Result(false,StatusCode.ERROR,"选题失败！");
            }
        }else {
            return new Result(false,StatusCode.ERROR,"你已选过选题！请勿再选！");
        }
    }

    //根据chooseId取消学生选题记录
    @RequestMapping("/CancelChooseTopic")
    public Result CancelChooseTopic(Integer id,HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        System.out.println("需要取消的ChooseId："+id);

        Boolean flag = studentService.CancelChooseTopic(id,user);

        if(flag == true){
            return new Result(true,StatusCode.OK,"撤销选题成功 ！");
        }else{
            return new Result(false,StatusCode.ERROR,"撤销选题失败！");
        }


    }


    //保存任务书信息
    @RequestMapping("/SaveAssignmentBook")
    public Result SaveAssignmentBook(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        Boolean flag = studentService.SaveAssignmentBook(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"任务书信息修改失败！");
        }else {
            return new Result(true,StatusCode.ERROR,"任务书信息修改成功！");
        }
    }

    //保存开题报告信息
    @RequestMapping("/SaveOpeningReport")
    public Result SaveOpeningReport(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        Boolean flag = studentService.SaveOpeningReport(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"任务书信息修改失败！");
        }else {
            return new Result(true,StatusCode.ERROR,"任务书信息修改成功！");
        }
    }

    //保存中期检查信息
    @RequestMapping("/SaveMidTermExam")
    public Result SaveMidTermExam(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        Boolean flag = studentService.SaveMidTermExam(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"中期检查修改失败！");
        }else {
            return new Result(true,StatusCode.ERROR,"中期检查信息修改成功！");
        }
    }


    //Student修改密码
    @RequestMapping("/StudentResetPassword")
    public Result StudentResetPassword(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        Boolean flag = studentService.StudentResetPassword(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"原始密码错误！请重新输入！");
        }else {
            return new Result(true,StatusCode.OK,"学生密码修改成功！");
        }
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

        //String[] topicName = new String[10];
        //topicName[0] = "基于Web的医药集中采购平台的设计与实现—卫厅局端";
        //topicName[1] = "基于JavaEE孕婴用品网上商店的设计与实现——后台管理";

        //计算句子相似度
        //System.out.println("-----句子相似度-----");
        //快速句子相似度
        //System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2));
        //float result = vec.sentenceSimilarity(wordList1, wordList2);
        //if (result > 0.7) {
        //    return "题目重复，请重新提交";
        //}
        //return "查重成功！";
    }

    //查询该学生的开题答辩信息
    @RequestMapping("/GetStuOpenAnswer")
    public Result GetStuOpenAnswer(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        OpenAnswerStu openAnswerStu = studentService.GetStuOpenAnswer(user);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerStu);
    }

    //查询该学生的开题答辩记录信息
    @RequestMapping("/GetStuOpenAnswerRecord")
    public Result GetStuOpenAnswerRecord(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        OpenAnswerRecord openAnswerRecord = studentService.GetStuOpenAnswerRecord(user);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerRecord);
    }

    //查询该学生的开题答辩问题列表
    @RequestMapping("/GetStuOpenAnswerQuestionList")
    public Result GetStuOpenAnswerQuestionList(HttpServletRequest request){
        Student user = (Student) request.getSession().getAttribute("user");
        List<OpenAnswerQuestion> openAnswerQuestionList = studentService.GetStuOpenAnswerQuestionList(user);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,openAnswerQuestionList);
    }

    //学生保存开题答辩问题记录
    @RequestMapping("/SubmitOpenAnswerQuestion")
    public Result SubmitOpenAnswerQuestion(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");
        System.out.println("提交答辩问题:\n"+searchMap);
        Boolean flag = studentService.SubmitOpenAnswerQuestion(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.ERROR,"保存失败！");
        }else {
            return new Result(true,StatusCode.OK,"保存成功！");
        }
    }

    //根据答辩问答id删除答辩问答信息
    @RequestMapping("/delOpenAnswerQuestionById")
    public Result delOpenAnswerQuestionById(Integer id){
        System.out.println("需要删除的问答Id："+id);
        Boolean flag = studentService.delOpenAnswerQuestionById(id);
        return new Result(flag,StatusCode.OK,"删除成功！");
    }
}

package com.xunmaw.design.controller;

import com.github.pagehelper.Page;
import com.xunmaw.design.common.MessageConstant;
import com.xunmaw.design.common.PageResult;
import com.xunmaw.design.common.Result;
import com.xunmaw.design.common.StatusCode;
import com.xunmaw.design.domain.Class;
import com.xunmaw.design.domain.*;
import com.xunmaw.design.service.DeptManagerService;
import com.xunmaw.design.service.ManagerService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenchengjian
 * @date 2022/3/17 11:22
 * Description: 系部管理员控制层:只负责接收前端浏览器发送的请求和请求参数，调用service层获取业务逻辑加工处理后的数据
 */

@RestController
@RequestMapping("DeptManager")
public class DeptManagerController {

    @Autowired
    private DeptManagerService deptManagerService;
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/find")
    public Result find(HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        List<Student> all = deptManagerService.findAllStudents(user);
        return new Result(false,200,"请求成功！！！",all);
    }

    @RequestMapping("/searchAllStudent")
    public PageResult searchAllStudent(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        System.out.println("searchMap为:"+searchMap);
        Page<Student> page = deptManagerService.searchAllStudent(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,page.getResult(), page.getTotal());
    }

    @RequestMapping("/searchAllTeacher")
    public PageResult searchAllTeacher(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Page<Teacher> page = deptManagerService.searchAllTeacher(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.TEACHER_SEARCH_SUCCESS,page.getResult(), page.getTotal());
    }

    //select器中根据系别id填充专业数据
    @RequestMapping(value = "/getMajorsByDep")
    public Result getMajorsByDep(HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        List<Major> majors = deptManagerService.getMajorsByDep(user);
        for (Major major : majors) {
            System.out.println(major);
        }
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,majors);
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
    public Result getAClassByMajorId(String id,Integer session,HttpServletRequest request){
        HttpSession session1 = request.getSession();
        Administrator user = (Administrator) session1.getAttribute("user");
        List<Class> classes = deptManagerService.getAClassByMajorId(user,id,session);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,classes);
    }

    @RequestMapping("/delStudent")
    public Result delStudent(@RequestBody List<Integer> ids){
        Boolean flag = deptManagerService.delStudent(ids);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_DELETE_SUCCESS);
    }

    // /community/updateStatus/0/1
    @RequestMapping("/updateStuStatus/{status}/{id}")
    public Result updateStuStatus(@PathVariable("status") Integer status,@PathVariable("id") Integer id){
        System.out.println("status:"+status+":id:"+id);
        Boolean flag = deptManagerService.updateStuStatus(status,id);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_UPDATE_STATUS_SUCCESS);
    }

    //重置学生密码
    @RequestMapping("/ResetStuPassword/{id}")
    public Result ResetStuPassword(@PathVariable("id") Integer id){
        Boolean flag = deptManagerService.ResetStuPassword(id);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_UPDATE_STATUS_SUCCESS);
    }

    @RequestMapping("/findStuById")
    public Result findStuById(Integer id){
        Student student = managerService.findStuById(id);
        System.out.println("findStuById:\n"+student);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,student);
    }

    @RequestMapping("/findTeaById")
    public Result findTeaById(Integer id){
//        System.out.println("需要查找的教师Id："+id);
        Teacher teacher = managerService.findTeaById(id);
        System.out.println(teacher);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,teacher);
    }

    //select器中填充系别数据
    @RequestMapping("/getAllDeptToSelect")
    public Result searchAllDeptToSelect(){
        List<Depart> departs = managerService.searchAllDeptToSelect();
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_FIND_BY_ID_SUCCESS,departs);
    }

    @RequestMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student){
        Boolean flag = managerService.updateStudent(student);
        return new Result(true,StatusCode.OK,MessageConstant.STUDENT_UPDATE_SUCCESS);
    }

    //创建学生
    @RequestMapping("/addStudent")
    public Result add(@RequestBody Student student,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        System.out.println(student.toString());
        boolean flag = deptManagerService.addStudent(student,user);
        if (flag == true) {
            return new Result(true,StatusCode.OK,MessageConstant.STUDENT_ADD_SUCCESS);
        }else {
            return new Result(false,StatusCode.OK,"你无权添加其他系部的学生！");
        }
    }

    @RequestMapping("/searchAllTopic")
    public PageResult searchAllTopic(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Page<Topic> topics = deptManagerService.searchAllTopic(searchMap,user);
        for (Topic topic :
                topics) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    @RequestMapping("/searchAllOKTopic")
    public PageResult searchAllOKTopic(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Page<Topic> topics = deptManagerService.searchAllOKTopic(searchMap,user);
        for (Topic topic :
                topics) {
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

    //根据id查找选题信息
    @RequestMapping("/findTopicById")
    public Result findTopicById(Integer id){
        System.out.println("需要查找的选题Id："+id);
        Topic topic = managerService.findTopicById(id);
        System.out.println("根据id找到的选题信息为："+topic);
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_FIND_BY_ID_SUCCESS,topic);
    }

    //修改选题信息
    @RequestMapping("/UpdateTopic")
    public Result UpdateTopic(@RequestBody Topic topic){
        System.out.println(topic);
        boolean flag = managerService.UpdateTopic(topic);
//        System.out.println("添加成功："+flag);
        return new Result(true,StatusCode.OK,MessageConstant.TOPIC_UPDATE_SUCCESS);
    }
    //根据教师id查询教师的指导专业
    @RequestMapping("/findGuideMajorsByTeaId")
    public Result findGuideMajorsByTeaId(Integer id){
        System.out.println("需要查找Majors的教师Id："+id);
        List<Major> majors = managerService.findGuideMajorsByTeaId(id);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_FIND_BY_ID_SUCCESS,majors);
    }

    // /community/updateStatus/0/1
    @RequestMapping("/updateTeaStatus/{status}/{teaId}")
    public Result updateTeaStatus(@PathVariable("status") Integer status,@PathVariable("teaId") Integer id){
        System.out.println("status:"+status+":id:"+id);
        Boolean flag = managerService.updateTeaStatus(status,id);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_UPDATE_STATUS_SUCCESS);
    }

    //重置教师密码
    @RequestMapping("/ResetTeaPassword/{id}")
    public Result ResetTeaPassword(@PathVariable("id") Integer id){
        Boolean flag = managerService.ResetTeaPassword(id);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_UPDATE_STATUS_SUCCESS);
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
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_UPDATE_SUCCESS);
    }

    //创建学生
    @RequestMapping("/addTeacher")
    public Result addTeacher(@RequestBody Map teacherMap,HttpServletRequest request){

        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");

        System.out.println(teacherMap.toString());

        Teacher teacher = new Teacher();
        teacher.setTeaNo(teacherMap.get("teaNo").toString());
        teacher.setTeaName(teacherMap.get("teaName").toString());
        teacher.setDepartId((Integer) teacherMap.get("departId"));
        teacher.setPhoneNumber(teacherMap.get("phoneNumber").toString());
        teacher.setGender((Integer) teacherMap.get("gender"));
        teacher.setXueWei(teacherMap.get("xueWei").toString());
        teacher.setJobTitle(teacherMap.get("jobTitle").toString());
        teacher.setMajors((ArrayList<Integer>) teacherMap.get("majors"));

        boolean flag = deptManagerService.addTeacher(teacher,user);

        if (flag == true) {
            return new Result(true,StatusCode.OK,MessageConstant.TEACHER_ADD_SUCCESS);
        }else {
            return new Result(false,StatusCode.OK,"你无权添加其他系部的教师！");
        }
    }

    // 删除教师
    @RequestMapping("/delTeacher")
    public Result delTeacher(@RequestBody List<Integer> ids){
        Boolean flag = managerService.delTeacher(ids);
        return new Result(true,StatusCode.OK,MessageConstant.TEACHER_DELETE_SUCCESS);
    }

    @RequestMapping("/searchAllMajors")
    public PageResult searchAllMajors(@RequestBody Map searchMap,HttpServletRequest request){

        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");

        Page<Major> page = deptManagerService.searchAllMajors(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.DEPART_SEARCH_SUCCESS,page.getResult(), page.getTotal());
    }

    //搜索所有专业管理员
    @RequestMapping("/searchAllMajorAdministrator")
    public PageResult searchAllMajorAdministrator(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Page<Administrator> page = deptManagerService.searchAllMajorAdministrator(searchMap,user);
        return new PageResult(true, StatusCode.OK, MessageConstant.DEPART_MANAGER_SEARCH_SUCCESS,page.getResult(), page.getTotal());
    }

    //管理员修改密码
    @RequestMapping("/DeptManagerResetPassword")
    public Result ManagerResetPassword(@RequestBody Map searchMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Boolean flag = managerService.ManagerResetPassword(searchMap,user);

        if (flag==false){
            return new Result(false,StatusCode.OK,"原始密码错误！请重新输入！");
        }else {
            return new Result(true,StatusCode.OK,"教务管理员密码修改成功！");
        }
    }


    @RequestMapping("/excel_stu")
    public void exportStu(HttpServletResponse response,HttpServletRequest request) throws IOException{
        response.setCharacterEncoding("UTF-8");
        List<Student> list = managerService.findAllComStudents(request);
        //System.out.println(list);
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet页
        HSSFSheet sheet = wb.createSheet("学生信息表");

        //创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("id");
        titleRow.createCell(1).setCellValue("学生学号");
        titleRow.createCell(2).setCellValue("学生姓名");
        titleRow.createCell(3).setCellValue("专业名称");
        titleRow.createCell(4).setCellValue("所属班级");
        titleRow.createCell(5).setCellValue("电话号码");
        titleRow.createCell(6).setCellValue("学生届数");
        titleRow.createCell(7).setCellValue("所在校区");
        //遍历将数据放到excel列中
        for (Student student : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(student.getId());
            dataRow.createCell(1).setCellValue(student.getStuNumber());
            dataRow.createCell(2).setCellValue(student.getStuName());
            dataRow.createCell(3).setCellValue(student.getDepart().getDepName());
            dataRow.createCell(4).setCellValue(student.getaClass().getClassName());
            dataRow.createCell(5).setCellValue(student.getPhoneNumber());
            dataRow.createCell(6).setCellValue(student.getSession());
            dataRow.createCell(7).setCellValue(student.getCampus());
        }
                  /*   // 设置下载时客户端Excel的名称
             String filename =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);  */

        // 设置下载时客户端Excel的名称   （上面注释的改进版本，上面的中文不支持）
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String("student".getBytes(),"iso-8859-1") + ".xls");


        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
    @RequestMapping("/excel_tea")
    public void exportTea(HttpServletResponse response, HttpServletRequest request ) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Teacher> list = managerService.findComTeachers(request);
        System.out.println(list);
        //System.out.println(list);
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet页
        HSSFSheet sheet = wb.createSheet("教师信息表");

        //创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("id");
        titleRow.createCell(1).setCellValue("教职工号");
        titleRow.createCell(2).setCellValue("教师姓名");
        titleRow.createCell(3).setCellValue("所属院系");
        titleRow.createCell(4).setCellValue("电话号码");
        titleRow.createCell(5).setCellValue("教师职称");
        titleRow.createCell(6).setCellValue("职工学位");

        //遍历将数据放到excel列中
        for (Teacher teacher : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(teacher.getTeaId());
            dataRow.createCell(1).setCellValue(teacher.getTeaNo());
            dataRow.createCell(2).setCellValue(teacher.getTeaName());
            dataRow.createCell(3).setCellValue(teacher.getDepart().getDepName());
            dataRow.createCell(4).setCellValue(teacher.getPhoneNumber());
            dataRow.createCell(5).setCellValue(teacher.getJobTitle());
            dataRow.createCell(6).setCellValue(teacher.getXueWei());

        }
                  /*   // 设置下载时客户端Excel的名称
             String filename =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);  */

        // 设置下载时客户端Excel的名称   （上面注释的改进版本，上面的中文不支持）
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String("teacher".getBytes(), "iso-8859-1") + ".xls");


        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
    //导入
    @RequestMapping("/importStu")
    public Result importStu(MultipartFile studentExcel, HttpServletRequest request, HttpSession session){
        if(studentExcel == null){
            session.setAttribute("excelName", "未上传文件，上传失败！");
            return new Result(false,StatusCode.ERROR,MessageConstant.FILE_TYPE_ERROR);
        }
        String studentExcelFileName = studentExcel.getOriginalFilename();
        if(!studentExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            session.setAttribute("excelName", "文件格式不正确！请使用.xls或.xlsx后缀的文档，导入失败！");
            return new Result(false,StatusCode.ERROR,MessageConstant.FILE_TYPE_ERROR);
        }
        //导入
        try {
            managerService.importStu(studentExcel);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        session.setAttribute("excelName", "导入成功！");
        //return "redirect: /Manager/StudentsList.html";
        return new Result(true,StatusCode.OK,MessageConstant.NO_FILE_SELECTED);
    }
    //导入
    @RequestMapping("/importTea")
    public Result importTea(MultipartFile teacherExcel, HttpServletRequest request, HttpSession session){
        if(teacherExcel == null){
            session.setAttribute("excelName", "未上传文件，上传失败！");
            return new Result(false,StatusCode.ERROR,MessageConstant.FILE_TYPE_ERROR);
        }
        String studentExcelFileName = teacherExcel.getOriginalFilename();
        if(!studentExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            session.setAttribute("excelName", "文件格式不正确！请使用.xls或.xlsx后缀的文档，导入失败！");
            return new Result(false,StatusCode.ERROR,MessageConstant.FILE_TYPE_ERROR);
        }
        //导入
        try {
            managerService.importTea(teacherExcel);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        session.setAttribute("excelName", "导入成功！");
        //return "redirect: /Manager/StudentsList.html";
        return new Result(true,StatusCode.OK,MessageConstant.NO_FILE_SELECTED);
    }
    @RequestMapping("/excel_topic")
    public void exportTopic(HttpServletResponse response ) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Topic> list = managerService.findAllTopics();
        System.out.println(list);
        //System.out.println(list);
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet页
        HSSFSheet sheet = wb.createSheet("选题信息表");

        //创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("序号");
        titleRow.createCell(1).setCellValue("题目名称");
        titleRow.createCell(2).setCellValue("学号");
        titleRow.createCell(3).setCellValue("学生姓名");
        titleRow.createCell(4).setCellValue("专业班级");
        titleRow.createCell(5).setCellValue("教师姓名");
        titleRow.createCell(6).setCellValue("教师职称");
        titleRow.createCell(7).setCellValue("选题类别");
        titleRow.createCell(8).setCellValue("成果类型");
        titleRow.createCell(9).setCellValue("选题来源");

        //遍历将数据放到excel列中
        for (Topic topic : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(topic.getTopic_id());
            dataRow.createCell(1).setCellValue(topic.getTitle());
            dataRow.createCell(2).setCellValue(topic.getStudent().getStuNumber());
            dataRow.createCell(3).setCellValue(topic.getStudent().getStuName());
            dataRow.createCell(4).setCellValue(topic.getStudent().getaClass().getClassName());
            dataRow.createCell(5).setCellValue(topic.getTeacher().getTeaName());
            dataRow.createCell(6).setCellValue(topic.getTeacher().getJobTitle());
            dataRow.createCell(7).setCellValue(topic.getCate());
            dataRow.createCell(8).setCellValue(topic.getType());
            dataRow.createCell(9).setCellValue(topic.getSource());
        }
                  /*   // 设置下载时客户端Excel的名称
             String filename =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);  */

        // 设置下载时客户端Excel的名称   （上面注释的改进版本，上面的中文不支持）
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String("topic".getBytes(), "iso-8859-1") + ".xls");


        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
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

    //系部管理员查询录取的学生——文件
    @RequestMapping("/searchStudentsFile")
    public PageResult searchStudentsFile(@RequestBody Map searchMap,HttpServletRequest request){
        System.out.println("searchMap:"+searchMap);

        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        Page<Topic> topics = deptManagerService.searchStudentsFile(searchMap,user);
        System.out.println("系部id为："+user.getDepart_id()+"录取的学生及文件为：---------");
        for (Topic topic :
                topics) {
            System.out.println("------------");
            System.out.println(topic);
        }
        return new PageResult(true, StatusCode.OK, MessageConstant.STUDENT_SEARCH_SUCCESS,topics.getResult(), topics.getTotal());
    }

}

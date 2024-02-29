package com.xunmaw.design.common;

/**
 * @date 2022/1/22 15:07
 * Description: 返回结果消息提示常量类
 */
public class MessageConstant {
    //---------------------------学生（student）操作消息提示信息---------------------------------------
    public static final String STUDENT_SEARCH_SUCCESS="查询学生列表信息成功！";
    public static final String STUDENT_ADD_SUCCESS="新增学生信息成功！";
    public static final String STUDENT_UPDATE_SUCCESS="修改学生信息成功！";
    public static final String STUDENT_DELETE_SUCCESS="删除学生信息成功！";
    public static final String STUDENT_PIC_UPLOAD_SUCCESS="学生缩略图上传成功！";
    public static final String STUDENT_PIC_DEL_SUCCESS = "学生缩略图删除成功！";
    public static final String STUDENT_FIND_BY_ID_SUCCESS = "根据主键获取学生对象成功！";
    public static final String STUDENT_UPDATE_STATUS_SUCCESS = "学生状态信息更新成功！";
    //---------------------------教师（teacher）操作消息提示信息---------------------------------------
    public static final String TEACHER_SEARCH_SUCCESS="查询教师列表信息成功！";
    public static final String TEACHER_UPDATE_SUCCESS="修改教师信息成功！";
    public static final String TEACHER_ADD_SUCCESS="新增教师信息成功！";
    public static final String TEACHER_FIND_BY_ID_SUCCESS = "根据主键获取教师对象成功！";
    public static final String TEACHER_DELETE_SUCCESS="删除教师信息成功！";
    public static final String TEACHER_UPDATE_STATUS_SUCCESS = "教师状态信息更新成功！";
    //---------------------------学院/系别（depart）操作消息提示信息---------------------------------------
    public static final String DEPART_SEARCH_SUCCESS="查询系部列表信息成功！";
    public static final String DEPART_MANAGER_SEARCH_SUCCESS="查询系部管理员列表信息成功！";
    //---------------------------系统提示信息----------------------------------------------------------
    public static final String SYSTEM_BUSY = "系统繁忙，请求稍后重试！";
    //---------------------------文件上传提示信息-------------------------------------------------------
    public static final String NO_FILE_SELECTED = "未选择上传的文件,请求选择后上传!";
    public static final String NO_WRITE_PERMISSION = "上传目录没有写权限!";
    public static final String INCORRECT_DIRECTORY_NAME = "目录名不正确!";
    public static final String SIZE_EXCEEDS__LIMIT = "上传文件大小超过限制!";
    public static final String FILE_TYPE_ERROR = "文件类型错误，只允许上传JPG/PNG/JPEG/GIF等图片类型的文件!";

    //---------------------------选题（topic）操作消息提示信息---------------------------------------
    public static final String TOPIC_FIND_BY_ID_SUCCESS="根据主键获取选题对象成功！";
    public static final String TOPIC_UPDATE_SUCCESS="修改选题信息成功！";

}

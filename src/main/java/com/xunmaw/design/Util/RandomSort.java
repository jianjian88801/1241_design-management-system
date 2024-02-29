package com.xunmaw.design.Util;

import com.xunmaw.design.domain.Student;
import com.xunmaw.design.domain.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
乱序算法
 */
public class RandomSort {

//    static class Student{
//        String name;
//        Integer age;
//
//        public Student(String name, Integer age) {
//            this.name = name;
//            this.age = age;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public Integer getAge() {
//            return age;
//        }
//
//        public void setAge(Integer age) {
//            this.age = age;
//        }
//    }

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        Student[] arr = students.toArray(new Student[students.size()]);
//        Student[] arr = {student1,student2,student3};
        randomSort(arr);
        for(int i = 0; i < arr.length; i ++){
            System.out.print(arr[i].getStuName() + " ");
        }
        System.out.println(" ");
    }
    //随机乱序
    public static void randomSort(Student[] arr){
        Student[] temp = arr.clone();
        int size = arr.length;
        for(int i = 0; i < size; i ++){
            if(arr[i] == temp[i]){//与原数组元素相等，说明未改变位置
                change(temp, arr, i);
            }
        }
    }

    public static void randomSort(Teacher[] arr){
        Teacher[] temp = arr.clone();
        int size = arr.length;
        for(int i = 0; i < size; i ++){
            if(arr[i] == temp[i]){//与原数组元素相等，说明未改变位置
                change(temp, arr, i);
            }
        }
    }

    private static void change(Student[] original, Student[] arr, int target){
        int size = arr.length;
        int[] indexes = getIndexesWithout( size, target);
        Student middle = arr[target];
        Random random = new Random();
        //尝试交换的元素下标
        int index = indexes[random.nextInt(size -1)];
        // System.out.println("index----------> " + index);
        if(arr[index] == original[index]){//与原数组元素相等，未变位置，可以交换
            arr[target] = arr[index];
            arr[index] = middle;
        }else {//若选中的元素是已经更换位置的，则继续尝试与数组中的其它元素交换位置
            for(int i = 0; i < size && i != index && i != target; i ++){
                if(arr[i] == original[i]){
                    arr[target] = arr[i];
                    arr[i] = middle;
                }
            }
        }
    }

    private static void change(Teacher[] original, Teacher[] arr, int target){
        int size = arr.length;
        int[] indexes = getIndexesWithout( size, target);
        Teacher middle = arr[target];
        Random random = new Random();
        //尝试交换的元素下标
        int index = indexes[random.nextInt(size -1)];
        // System.out.println("index----------> " + index);
        if(arr[index] == original[index]){//与原数组元素相等，未变位置，可以交换
            arr[target] = arr[index];
            arr[index] = middle;
        }else {//若选中的元素是已经更换位置的，则继续尝试与数组中的其它元素交换位置
            for(int i = 0; i < size && i != index && i != target; i ++){
                if(arr[i] == original[i]){
                    arr[target] = arr[i];
                    arr[i] = middle;
                }
            }
        }
    }

    //获取目标下标之外的下标
    private static int[] getIndexesWithout(int size, int targetIndex){
        int[] ta = new int[size - 1];
        for(int i = 0; i < size; i ++){
            if(i != targetIndex){
                if(i < targetIndex){
                    ta[i] = i;
                }else{
                    ta[i-1] = i;
                }
            }
        }
        return ta;
    }
}

package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface EmployeeDao {

    /**
     * getTeacherNameById
     * @param id 导师id
     * @return 导师姓名
     */
    String getTeacherNameById(@Param("id") Integer id);

    int updateTrainingPeriod(@Param("id")int id, @Param("beginDate") Date beginDate, @Param("endDate")Date endDate);

    List<Employee> searchEmployeesByTeacherId(@Param("id") Integer id);

    Employee searchEmployeeById(@Param("id") Integer id);

    List<Employee> searchAllEmployees();

    void deleteEmployeeById(@Param("id") int id);

    void updateEmployeeType(@Param("employee") RespEmployee respEmployee);

    void updateEmployeeName(@Param("name") String s, @Param("id") Integer id);

    Employee searchEmployeeByEmail(@Param("email") String email);

    void updateEmployeeClass(@Param("employee") Employee employee);

    void updateEmployeeTeacher(@Param("classId") Integer id, @Param("teacherId") Integer empId);
}

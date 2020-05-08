package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ClassDao;
import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;
import com.dgut.liukc.trainingsystem.service.AuthenticationService;
import com.dgut.liukc.trainingsystem.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements EmployeeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    @Qualifier("RegisterServiceImpl")
    private AuthenticationService authenticationService;

    @Override
    public String getTeacherNameById(Integer id) {
        return employeeDao.getTeacherNameById(id);
    }

    @Override
    public List<Employee> searchEmployeesByTeacherId(Integer id) {
        return employeeDao.searchEmployeesByTeacherId(id);
    }

    @Override
    public List<RespEmployee> searchAllEmployees() {
        List<Employee> employees = employeeDao.searchAllEmployees();
        List<RespEmployee> respEmployees = new ArrayList<>();
        for (Employee employee: employees){
            RespEmployee respEmployee = new RespEmployee();
            respEmployee.setId(employee.getId());
            respEmployee.setDepartment(employee.getDepartment());
            respEmployee.setName(employee.getName());
            respEmployee.setType(employee.getType());
            if (employee.getClassId() != null){
                Class aclass = classDao.searchClassById(employee.getClassId());
                respEmployee.setAClass(aclass);
            }

            respEmployees.add(respEmployee);
        }
        return respEmployees;
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeDao.deleteEmployeeById(id);
    }

    @Override
    public void updateEmployeeType(RespEmployee respEmployee) {
        employeeDao.updateEmployeeType(respEmployee);
    }

    @Override
    public RespEmployee addNewEmployee(RespEmployee respEmployee) {
        int result = authenticationService.register(respEmployee.getEmail(), respEmployee.getPassword(), "新员工");
        RespEmployee newRespEmp = null;
        if (result == 2000){
            newRespEmp = new RespEmployee();
            String name = respEmployee.getEmail().split("@")[0];
            Employee employee = employeeDao.searchEmployeeByEmail(respEmployee.getEmail());
            newRespEmp.setId(employee.getId());
            newRespEmp.setDepartment(employee.getDepartment());
            newRespEmp.setName(name);
            newRespEmp.setType(employee.getType());

            employeeDao.updateEmployeeName(name,employee.getId());
        }
        return newRespEmp;
    }
}

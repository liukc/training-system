package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.EmpLoginInfo;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 登录验证持久层，用于注册登录
 * @author liukc
 */
@Mapper
public interface AuthenticationDao {
    /**
     * 检查邮箱是否存在
     * @param email 邮箱
     * @return 具体员工id or null：不存在
     */
    Integer emailIsExist(@Param("email") String email);

    /**
     * 插入员工信息
     * 备注插入后员工id包含在employee.id
     * @param employee 员工相关信息
     * @return 插入记录数
     */
    int insertEmployee(@Param("employee")Employee employee);

    int insertEmpLoginInfo(@Param("empLoginInfo")EmpLoginInfo empLoginInfo);

    /**
     * 由员工id 获取密码
     * @param id 员工id
     * @return 员工密码
     */
    String getPasswordByEmpId(@Param("id") Integer id);

    int getDisableLevelByEmpId(@Param("id") Integer id);

    /**
     * 禁用账号
     * @param id 员工id
     * @param level 禁用等级
     * @return 更改记录数
     */
    int disableAccountByEmpId(@Param("id")Integer id, @Param("level")int level);

    /**
     * 启用账号
     * @param id 员工id
     * @return 更改记录数
     */
    int ableAccountByEmpId(@Param("id")Integer id);

    Employee getEmployeeById(@Param("id")Integer id);
}

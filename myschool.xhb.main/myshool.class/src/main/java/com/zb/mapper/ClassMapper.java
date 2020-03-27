package com.zb.mapper;

import com.zb.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级接口
 * @author
 */
@Mapper
public interface ClassMapper {
    /**
     * 根据班级编号查询班级的信息
     * @param class_number
     * @return
     */
    Class_add getClassBy(@Param("class_number")Integer class_number);

    /**
     * 创建班级
     * @param classAdd
     * @return
     */
    int addClass(Class_add classAdd);

    /**
     * 查询学科
     * @return
     */
    List<Class_Subject> getSubject();

    /**
     * 年纪
     * @return
     */
    List<Class_age> getAge();
    //查询学历
    List<Class_real> getReal();
    //根据学历的id查询所属的班级
    List<Class_age_real> getAgeReal(@Param("real_id")Integer real_id);
}

package abc.wuyan.demo.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import abc.wuyan.demo.model.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("INSERT INTO USER (name,password,sex,role,email) VALUE (#{name}, #{password}, #{sex}, #{role},#{email})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="uid", before=false, resultType=Integer.class)
    public int insert(User user);

    @Update("UPDATE USER SET SEX=#{sex},PASSWORD=#{password},ROLE=#{role} WHERE UID = #{uid}")
    public int update(User user);

    @Delete("DELETE FROM USER WHERE UID = #{uid}")
    public int delete(User user);

    @Select("SELECT * FROM USER")
    public List<User> findAllUsers();

    @Select("SELECT * FROM USER WHERE UID = #{uid}")
    public User findUserById(int uid);

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    public User findUserByName(String name);

    @Select("SELECT * FROM USER WHERE NAME=#{name} AND PASSWORD=#{password}")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="uid", before=false, resultType=Integer.class)
    public User findUserByParam(User user);

}

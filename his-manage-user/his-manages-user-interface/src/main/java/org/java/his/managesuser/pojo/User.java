package org.java.his.managesuser.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "manage_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * 我们在服务之间传递数据时，对象会被序列化后再传递
     * 但是，对象在进行序列化时，会将所有数据都进行封装传递
     * 一些敏感信息，如果也进行序列化，有可能导致敏感数据，泄露
     * 所以，一般在序列化对象，会忽略掉，对象中敏感信息，
     * 这些信息不会封装在序列化的对象中
     *
     * 此处的盐与密码都不应该序列化
     */

    @Id
    private String id; //用户编号

    @Length(min = 2,max = 8,message = "姓名的长度必须在2-8字符之间")
    private String username;//用户名称

    private String nickname;//用户昵称

    @JsonIgnore //对象进行序列化转换成json时，忽略该属性
    @Length(min = 3,max = 8,message = "密码的长度必须在3-8字符之间")
    private String password;//用户密码

    private Date created;//用户创建时间

    @JsonIgnore //对象进行序列化转换成json时，忽略该属性
    private String salt;//盐，用于对密码加密

    private String picture;//图片
}

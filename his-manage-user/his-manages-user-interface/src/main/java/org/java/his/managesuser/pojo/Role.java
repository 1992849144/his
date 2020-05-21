package org.java.his.managesuser.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String id; //角色编号

    @Length(min = 2,max = 8,message = "姓名的长度必须在2-8字符之间")
    private String name;//角色名称

    private List<User> users= new ArrayList<>(); //用户集合
}

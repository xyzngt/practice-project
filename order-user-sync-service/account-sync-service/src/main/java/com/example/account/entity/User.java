package com.example.account.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 *
 */
@Data
@Accessors(chain = true)
@Entity
public class User extends Model<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /** 用户名 **/
    private String name;
    /** 密码 **/
    private String passwordDigest;
    /** 联系方式 **/
    private String tel;
    /** 邮箱 **/
    private String email;
    /** 描述 **/
    private String userDescribe;
    /** 用户状态:S-正常，N-停用，D-删除 **/
    private String status;
    /** 用户类型 **/
    private Integer userType;
    /** 性别：0-男，1-女，2-未知 **/
    private Integer gender;
    /** 提交时间 **/
    private Date createdAt;
    /** 更新时间 **/
    private Date updatedAt;
}


package com.zlingchun.jpa.pojo;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/1
 * @description descrip
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cst_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long rid;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private List<Customer> customers;

    @CreatedBy
    String createdBy;
    @LastModifiedBy
    String modifiedBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate = new Date();
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP) //指定时间类型
    Date modifiedDate = new Date();

    private @Version Long version; // 指定版本号
}

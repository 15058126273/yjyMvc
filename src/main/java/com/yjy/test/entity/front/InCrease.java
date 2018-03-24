package com.yjy.test.entity.front;

import com.yjy.test.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.TABLE;

@Entity
@Table(name = "y_increase")
public class InCrease extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "remark", nullable = false)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

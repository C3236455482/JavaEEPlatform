package cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.po;

import cn.edu.xmu.javaee.Lab5_SpringDataJPA.dao.bo.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_product")
public class ProductJPAPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long goodsId;
    private String skuSn;
    private String name;
    private Long originalPrice;
    private Long weight;
    private String barcode;
    private String unit;
    private String originPlace;
    private Integer commissionRatio;
    private Long freeThreshold;
    private byte status;

    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
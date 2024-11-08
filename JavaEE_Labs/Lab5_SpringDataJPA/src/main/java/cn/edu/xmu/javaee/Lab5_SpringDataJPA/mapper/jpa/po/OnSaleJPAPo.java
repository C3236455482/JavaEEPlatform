package cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.po;

import cn.edu.xmu.javaee.Lab5_SpringDataJPA.dao.bo.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_onsale")
public class OnSaleJPAPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;

    private Long price;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Integer maxQuantity;

    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
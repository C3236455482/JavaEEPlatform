//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.javaee.Lab5_SpringDataJPA.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * 商品视图对象
 * @author Ming Qiu
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {

    private Long id;

    private String skuSn;

    private String name;

    private Long originalPrice;

    private Long weight;

    private Long price;

    private String barcode;

    private String unit;

    private String originPlace;

    private Integer quantity;

    private Integer maxQuantity;

    private List<ProductDto> otherProduct;
}

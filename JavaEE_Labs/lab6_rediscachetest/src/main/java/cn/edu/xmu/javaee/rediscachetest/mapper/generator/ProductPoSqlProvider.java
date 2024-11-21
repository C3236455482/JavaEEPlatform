package cn.edu.xmu.javaee.rediscachetest.mapper.generator;

import cn.edu.xmu.javaee.rediscachetest.mapper.generator.po.ProductPo;
import cn.edu.xmu.javaee.rediscachetest.mapper.generator.po.ProductPoExample;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ProductPoSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    public String insertSelective(ProductPo row) {
        SQL sql = new SQL();
        sql.INSERT_INTO("goods_product");
        
        if (row.getShopId() != null) {
            sql.VALUES("`shop_id`", "#{shopId,jdbcType=BIGINT}");
        }
        
        if (row.getGoodsId() != null) {
            sql.VALUES("`goods_id`", "#{goodsId,jdbcType=BIGINT}");
        }
        
        if (row.getCategoryId() != null) {
            sql.VALUES("`category_id`", "#{categoryId,jdbcType=BIGINT}");
        }
        
        if (row.getTemplateId() != null) {
            sql.VALUES("`template_id`", "#{templateId,jdbcType=BIGINT}");
        }
        
        if (row.getSkuSn() != null) {
            sql.VALUES("`sku_sn`", "#{skuSn,jdbcType=VARCHAR}");
        }
        
        if (row.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginalPrice() != null) {
            sql.VALUES("`original_price`", "#{originalPrice,jdbcType=BIGINT}");
        }
        
        if (row.getWeight() != null) {
            sql.VALUES("`weight`", "#{weight,jdbcType=BIGINT}");
        }
        
        if (row.getBarcode() != null) {
            sql.VALUES("`barcode`", "#{barcode,jdbcType=VARCHAR}");
        }
        
        if (row.getUnit() != null) {
            sql.VALUES("`unit`", "#{unit,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginPlace() != null) {
            sql.VALUES("`origin_place`", "#{originPlace,jdbcType=VARCHAR}");
        }
        
        if (row.getCreatorId() != null) {
            sql.VALUES("`creator_id`", "#{creatorId,jdbcType=BIGINT}");
        }
        
        if (row.getCreatorName() != null) {
            sql.VALUES("`creator_name`", "#{creatorName,jdbcType=VARCHAR}");
        }
        
        if (row.getModifierId() != null) {
            sql.VALUES("`modifier_id`", "#{modifierId,jdbcType=BIGINT}");
        }
        
        if (row.getModifierName() != null) {
            sql.VALUES("`modifier_name`", "#{modifierName,jdbcType=VARCHAR}");
        }
        
        if (row.getGmtCreate() != null) {
            sql.VALUES("`gmt_create`", "#{gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (row.getGmtModified() != null) {
            sql.VALUES("`gmt_modified`", "#{gmtModified,jdbcType=TIMESTAMP}");
        }
        
        if (row.getStatus() != null) {
            sql.VALUES("`status`", "#{status,jdbcType=TINYINT}");
        }
        
        if (row.getCommissionRatio() != null) {
            sql.VALUES("`commission_ratio`", "#{commissionRatio,jdbcType=INTEGER}");
        }
        
        if (row.getShopLogisticId() != null) {
            sql.VALUES("`shop_logistic_id`", "#{shopLogisticId,jdbcType=BIGINT}");
        }
        
        if (row.getFreeThreshold() != null) {
            sql.VALUES("`free_threshold`", "#{freeThreshold,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    public String selectByExample(ProductPoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("`id`");
        } else {
            sql.SELECT("`id`");
        }
        sql.SELECT("`shop_id`");
        sql.SELECT("`goods_id`");
        sql.SELECT("`category_id`");
        sql.SELECT("`template_id`");
        sql.SELECT("`sku_sn`");
        sql.SELECT("`name`");
        sql.SELECT("`original_price`");
        sql.SELECT("`weight`");
        sql.SELECT("`barcode`");
        sql.SELECT("`unit`");
        sql.SELECT("`origin_place`");
        sql.SELECT("`creator_id`");
        sql.SELECT("`creator_name`");
        sql.SELECT("`modifier_id`");
        sql.SELECT("`modifier_name`");
        sql.SELECT("`gmt_create`");
        sql.SELECT("`gmt_modified`");
        sql.SELECT("`status`");
        sql.SELECT("`commission_ratio`");
        sql.SELECT("`shop_logistic_id`");
        sql.SELECT("`free_threshold`");
        sql.FROM("goods_product");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ProductPo row = (ProductPo) parameter.get("row");
        ProductPoExample example = (ProductPoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("goods_product");
        
        if (row.getId() != null) {
            sql.SET("`id` = #{row.id,jdbcType=BIGINT}");
        }
        
        if (row.getShopId() != null) {
            sql.SET("`shop_id` = #{row.shopId,jdbcType=BIGINT}");
        }
        
        if (row.getGoodsId() != null) {
            sql.SET("`goods_id` = #{row.goodsId,jdbcType=BIGINT}");
        }
        
        if (row.getCategoryId() != null) {
            sql.SET("`category_id` = #{row.categoryId,jdbcType=BIGINT}");
        }
        
        if (row.getTemplateId() != null) {
            sql.SET("`template_id` = #{row.templateId,jdbcType=BIGINT}");
        }
        
        if (row.getSkuSn() != null) {
            sql.SET("`sku_sn` = #{row.skuSn,jdbcType=VARCHAR}");
        }
        
        if (row.getName() != null) {
            sql.SET("`name` = #{row.name,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginalPrice() != null) {
            sql.SET("`original_price` = #{row.originalPrice,jdbcType=BIGINT}");
        }
        
        if (row.getWeight() != null) {
            sql.SET("`weight` = #{row.weight,jdbcType=BIGINT}");
        }
        
        if (row.getBarcode() != null) {
            sql.SET("`barcode` = #{row.barcode,jdbcType=VARCHAR}");
        }
        
        if (row.getUnit() != null) {
            sql.SET("`unit` = #{row.unit,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginPlace() != null) {
            sql.SET("`origin_place` = #{row.originPlace,jdbcType=VARCHAR}");
        }
        
        if (row.getCreatorId() != null) {
            sql.SET("`creator_id` = #{row.creatorId,jdbcType=BIGINT}");
        }
        
        if (row.getCreatorName() != null) {
            sql.SET("`creator_name` = #{row.creatorName,jdbcType=VARCHAR}");
        }
        
        if (row.getModifierId() != null) {
            sql.SET("`modifier_id` = #{row.modifierId,jdbcType=BIGINT}");
        }
        
        if (row.getModifierName() != null) {
            sql.SET("`modifier_name` = #{row.modifierName,jdbcType=VARCHAR}");
        }
        
        if (row.getGmtCreate() != null) {
            sql.SET("`gmt_create` = #{row.gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (row.getGmtModified() != null) {
            sql.SET("`gmt_modified` = #{row.gmtModified,jdbcType=TIMESTAMP}");
        }
        
        if (row.getStatus() != null) {
            sql.SET("`status` = #{row.status,jdbcType=TINYINT}");
        }
        
        if (row.getCommissionRatio() != null) {
            sql.SET("`commission_ratio` = #{row.commissionRatio,jdbcType=INTEGER}");
        }
        
        if (row.getShopLogisticId() != null) {
            sql.SET("`shop_logistic_id` = #{row.shopLogisticId,jdbcType=BIGINT}");
        }
        
        if (row.getFreeThreshold() != null) {
            sql.SET("`free_threshold` = #{row.freeThreshold,jdbcType=BIGINT}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("goods_product");
        
        sql.SET("`id` = #{row.id,jdbcType=BIGINT}");
        sql.SET("`shop_id` = #{row.shopId,jdbcType=BIGINT}");
        sql.SET("`goods_id` = #{row.goodsId,jdbcType=BIGINT}");
        sql.SET("`category_id` = #{row.categoryId,jdbcType=BIGINT}");
        sql.SET("`template_id` = #{row.templateId,jdbcType=BIGINT}");
        sql.SET("`sku_sn` = #{row.skuSn,jdbcType=VARCHAR}");
        sql.SET("`name` = #{row.name,jdbcType=VARCHAR}");
        sql.SET("`original_price` = #{row.originalPrice,jdbcType=BIGINT}");
        sql.SET("`weight` = #{row.weight,jdbcType=BIGINT}");
        sql.SET("`barcode` = #{row.barcode,jdbcType=VARCHAR}");
        sql.SET("`unit` = #{row.unit,jdbcType=VARCHAR}");
        sql.SET("`origin_place` = #{row.originPlace,jdbcType=VARCHAR}");
        sql.SET("`creator_id` = #{row.creatorId,jdbcType=BIGINT}");
        sql.SET("`creator_name` = #{row.creatorName,jdbcType=VARCHAR}");
        sql.SET("`modifier_id` = #{row.modifierId,jdbcType=BIGINT}");
        sql.SET("`modifier_name` = #{row.modifierName,jdbcType=VARCHAR}");
        sql.SET("`gmt_create` = #{row.gmtCreate,jdbcType=TIMESTAMP}");
        sql.SET("`gmt_modified` = #{row.gmtModified,jdbcType=TIMESTAMP}");
        sql.SET("`status` = #{row.status,jdbcType=TINYINT}");
        sql.SET("`commission_ratio` = #{row.commissionRatio,jdbcType=INTEGER}");
        sql.SET("`shop_logistic_id` = #{row.shopLogisticId,jdbcType=BIGINT}");
        sql.SET("`free_threshold` = #{row.freeThreshold,jdbcType=BIGINT}");
        
        ProductPoExample example = (ProductPoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(ProductPo row) {
        SQL sql = new SQL();
        sql.UPDATE("goods_product");
        
        if (row.getShopId() != null) {
            sql.SET("`shop_id` = #{shopId,jdbcType=BIGINT}");
        }
        
        if (row.getGoodsId() != null) {
            sql.SET("`goods_id` = #{goodsId,jdbcType=BIGINT}");
        }
        
        if (row.getCategoryId() != null) {
            sql.SET("`category_id` = #{categoryId,jdbcType=BIGINT}");
        }
        
        if (row.getTemplateId() != null) {
            sql.SET("`template_id` = #{templateId,jdbcType=BIGINT}");
        }
        
        if (row.getSkuSn() != null) {
            sql.SET("`sku_sn` = #{skuSn,jdbcType=VARCHAR}");
        }
        
        if (row.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginalPrice() != null) {
            sql.SET("`original_price` = #{originalPrice,jdbcType=BIGINT}");
        }
        
        if (row.getWeight() != null) {
            sql.SET("`weight` = #{weight,jdbcType=BIGINT}");
        }
        
        if (row.getBarcode() != null) {
            sql.SET("`barcode` = #{barcode,jdbcType=VARCHAR}");
        }
        
        if (row.getUnit() != null) {
            sql.SET("`unit` = #{unit,jdbcType=VARCHAR}");
        }
        
        if (row.getOriginPlace() != null) {
            sql.SET("`origin_place` = #{originPlace,jdbcType=VARCHAR}");
        }
        
        if (row.getCreatorId() != null) {
            sql.SET("`creator_id` = #{creatorId,jdbcType=BIGINT}");
        }
        
        if (row.getCreatorName() != null) {
            sql.SET("`creator_name` = #{creatorName,jdbcType=VARCHAR}");
        }
        
        if (row.getModifierId() != null) {
            sql.SET("`modifier_id` = #{modifierId,jdbcType=BIGINT}");
        }
        
        if (row.getModifierName() != null) {
            sql.SET("`modifier_name` = #{modifierName,jdbcType=VARCHAR}");
        }
        
        if (row.getGmtCreate() != null) {
            sql.SET("`gmt_create` = #{gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (row.getGmtModified() != null) {
            sql.SET("`gmt_modified` = #{gmtModified,jdbcType=TIMESTAMP}");
        }
        
        if (row.getStatus() != null) {
            sql.SET("`status` = #{status,jdbcType=TINYINT}");
        }
        
        if (row.getCommissionRatio() != null) {
            sql.SET("`commission_ratio` = #{commissionRatio,jdbcType=INTEGER}");
        }
        
        if (row.getShopLogisticId() != null) {
            sql.SET("`shop_logistic_id` = #{shopLogisticId,jdbcType=BIGINT}");
        }
        
        if (row.getFreeThreshold() != null) {
            sql.SET("`free_threshold` = #{freeThreshold,jdbcType=BIGINT}");
        }
        
        sql.WHERE("`id` = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, ProductPoExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<ProductPoExample.Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            ProductPoExample.Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<ProductPoExample.Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    ProductPoExample.Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}
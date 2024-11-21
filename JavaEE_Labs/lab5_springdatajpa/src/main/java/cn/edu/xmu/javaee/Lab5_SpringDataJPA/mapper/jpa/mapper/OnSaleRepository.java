package cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.mapper;

import cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.po.OnSaleJPAPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OnSaleRepository extends JpaRepository<OnSaleJPAPo, Long> {

    @Query("SELECT o FROM OnSaleJPAPo o WHERE o.productId = :productId " +
            "AND o.beginTime <= CURRENT_TIMESTAMP " +
            "AND o.endTime >= CURRENT_TIMESTAMP " +
            "ORDER BY o.endTime DESC")
    List<OnSaleJPAPo> findLatestOnSaleByProductId(@Param("productId") Long productId);
}

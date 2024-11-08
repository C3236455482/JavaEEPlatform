package cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.mapper;

import cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa.po.OnSaleJPAPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OnSaleRepository extends JpaRepository<OnSaleJPAPo, Long> {
    List<OnSaleJPAPo> findByProductId(Long productId);
}

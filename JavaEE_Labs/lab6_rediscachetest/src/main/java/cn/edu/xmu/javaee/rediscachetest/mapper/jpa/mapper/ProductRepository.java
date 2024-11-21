package cn.edu.xmu.javaee.rediscachetest.mapper.jpa.mapper;

import cn.edu.xmu.javaee.rediscachetest.mapper.jpa.po.ProductJPAPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductJPAPo, Long> {
    List<ProductJPAPo> findByName(String name);

    List<ProductJPAPo> findByGoodsIdAndIdNot(Long goodsId, Long id);
}

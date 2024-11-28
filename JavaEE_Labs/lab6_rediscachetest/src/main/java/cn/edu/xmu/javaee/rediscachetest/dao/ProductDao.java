//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.javaee.rediscachetest.dao;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.rediscachetest.dao.bo.OnSale;
import cn.edu.xmu.javaee.rediscachetest.dao.bo.Product;
import cn.edu.xmu.javaee.rediscachetest.mapper.generator.ProductPoMapper;
import cn.edu.xmu.javaee.rediscachetest.mapper.generator.po.ProductPo;
import cn.edu.xmu.javaee.rediscachetest.mapper.generator.po.ProductPoExample;
import cn.edu.xmu.javaee.rediscachetest.mapper.jpa.mapper.OnSaleRepository;
import cn.edu.xmu.javaee.rediscachetest.mapper.jpa.mapper.ProductRepository;
import cn.edu.xmu.javaee.rediscachetest.mapper.jpa.po.OnSaleJPAPo;
import cn.edu.xmu.javaee.rediscachetest.mapper.jpa.po.ProductJPAPo;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.rediscachetest.dao.bo.User;
import cn.edu.xmu.javaee.rediscachetest.mapper.manual.ProductAllMapper;
import cn.edu.xmu.javaee.rediscachetest.mapper.manual.po.ProductAllPo;
import cn.edu.xmu.javaee.rediscachetest.util.CloneFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * @author Ming Qiu
 **/
@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final static Logger logger = LoggerFactory.getLogger(ProductDao.class);

    private final static String KEY = "P%d";

    private final static String OTHER_KEY = "PO%d";

    private static final String VALID_ONSALE_KEY = "OV%d";

    private final ProductPoMapper productPoMapper;

    private final OnSaleDao onSaleDao;

    private final ProductAllMapper productAllMapper;

    private final ProductRepository productRepository;

    private final OnSaleRepository onSaleRepository;

    private final RedisUtil redisUtil;

    public List<Product> retrieveProductByName(String name, boolean all) throws BusinessException {
        List<Product> productList = new ArrayList<>();
        ProductPoExample example = new ProductPoExample();
        ProductPoExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<ProductPo> productPoList = productPoMapper.selectByExample(example);
        for (ProductPo po : productPoList) {
            Product product = null;
            if (all) {
                product = this.retrieveFullProductWithRedis(po, false);
            } else {
                product = CloneFactory.copy(new Product(), po);
            }
            productList.add(product);
        }
        logger.debug("retrieveProductByName: productList = {}", productList);
        return productList;
    }

    /**
     * 用GoodsPo对象找Goods对象
     * @param  productId
     * @return  Goods对象列表，带关联的Product返回
     */
    public Product retrieveProductByID(Long productId, boolean all) throws BusinessException {
        Product product = null;
        ProductPo productPo = productPoMapper.selectByPrimaryKey(productId);
        if (null == productPo){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "产品id不存在");
        }
        if (all) {
            product = this.retrieveFullProduct(productPo);
        } else {
            product = CloneFactory.copy(new Product(), productPo);
        }

        logger.debug("retrieveProductByID: product = {}",  product);
        return product;
    }


    private Product retrieveFullProduct(ProductPo productPo) throws DataAccessException{
        assert productPo != null;
        Product product =  CloneFactory.copy(new Product(), productPo);
        List<OnSale> latestOnSale = onSaleDao.getLatestOnSale(productPo.getId());
        product.setOnSaleList(latestOnSale);

        List<Product> otherProduct = this.retrieveOtherProduct(productPo);
        product.setOtherProduct(otherProduct);

        return product;
    }

    public Product retrieveProductByIDWithRedis(Long productId, boolean all, boolean useRedis) throws BusinessException {
        logger.debug("retrieveProductByIDWithRedis: id = {}, all = {}, useRedis = {}", productId, all, useRedis);

        ProductPo productPo = null;

        // 如果使用Redis，首先尝试从Redis获取数据
        if (useRedis) {
            String productKey = String.format(KEY, productId);
            productPo = (ProductPo) redisUtil.get(productKey);
            if (productPo != null) {
                logger.info("Cache hit for product: {}", productKey);
                // 如果Redis缓存命中，且只需要基本信息，直接返回
                if (!all) {
                    return CloneFactory.copy(new Product(), productPo);
                }
            }
        }

        // 如果没有使用Redis，或者Redis缓存没有命中，查询数据库
        if (productPo == null) {
            productPo = productPoMapper.selectByPrimaryKey(productId);
            logger.info("Cache missed for product, retrieving from database: {}", productId);
        }

        if (productPo == null) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "产品id不存在");
        }

        // 将基本信息存入 Redis 缓存，如果需要（useRedis为true）
        if (useRedis) {
            String productKey = String.format(KEY, productId);
            redisUtil.set(productKey, productPo, 3600);
        }

        // 根据是否需要完整信息，决定是否调用 retrieveFullProductWithRedis
        Product product;
        if (all) {
            product = this.retrieveFullProductWithRedis(productPo, useRedis);
        } else {
            product = CloneFactory.copy(new Product(), productPo);
        }

        return product;
    }


    private Product retrieveFullProductWithRedis(ProductPo productPo, boolean useRedis) throws DataAccessException {
        assert productPo != null;

        // 1. 从 productPo 转换为 Product，保留基本信息
        Product product = CloneFactory.copy(new Product(), productPo);

        Long productId = productPo.getId();

        // Redis 缓存键
        String onSaleKey = String.format(VALID_ONSALE_KEY, productId);
        String otherProductKey = String.format(OTHER_KEY, productId);

        // 2. 获取 OnSale 信息
        List<OnSale> latestOnSale = null;
        if (useRedis) {
            latestOnSale = (List<OnSale>) redisUtil.get(onSaleKey);
            if (latestOnSale == null) {
                // 如果 OnSale 信息不在缓存中，从数据库查询并更新缓存
                latestOnSale = onSaleDao.getLatestOnSale(productId);
                logger.info("Cache missed for OnSale, retrieving from database: {}", onSaleKey);
                redisUtil.set(onSaleKey, (Serializable) latestOnSale, 3600);  // 缓存600秒
            } else {
                logger.info("Cache hit for OnSale: {}", onSaleKey);
            }
        } else {
            // 如果不使用Redis，直接从数据库查询
            latestOnSale = onSaleDao.getLatestOnSale(productId);
        }
        product.setOnSaleList(latestOnSale);

        // 3. 获取 OtherProduct 信息
        List<Product> otherProduct = null;
        if (useRedis) {
            otherProduct = (List<Product>) redisUtil.get(otherProductKey);
            if (otherProduct == null) {
                // 如果 OtherProduct 信息不在缓存中，从数据库查询并更新缓存
                otherProduct = this.retrieveOtherProduct(productPo);
                logger.info("Cache missed for OtherProduct, retrieving from database: {}", otherProductKey);
                redisUtil.set(otherProductKey, (Serializable) otherProduct, 3600);  // 缓存600秒
            } else {
                logger.info("Cache hit for OtherProduct: {}", otherProductKey);
            }
        } else {
            // 如果不使用Redis，直接从数据库查询
            otherProduct = this.retrieveOtherProduct(productPo);
        }
        product.setOtherProduct(otherProduct);

        return product;
    }


    private List<Product> retrieveOtherProduct(ProductPo productPo) throws DataAccessException {
        assert productPo != null;

        ProductPoExample example = new ProductPoExample();
        ProductPoExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(productPo.getGoodsId());
        criteria.andIdNotEqualTo(productPo.getId());
        List<ProductPo> productPoList = productPoMapper.selectByExample(example);
        return productPoList.stream().map(po -> CloneFactory.copy(new Product(), po)).collect(Collectors.toList());
    }

    /**
     * 创建Goods对象
     *
     * @param product 传入的Goods对象
     * @return 返回对象ReturnObj
     */
    public Product createProduct(Product product, User user) throws BusinessException {

        Product retObj = null;
        product.setCreator(user);
        product.setGmtCreate(LocalDateTime.now());
        ProductPo po = CloneFactory.copy(new ProductPo(), product);
        int ret = productPoMapper.insertSelective(po);
        retObj = CloneFactory.copy(new Product(), po);
        return retObj;
    }

    /**
     * 修改商品信息
     *
     * @param product 传入的product对象
     * @return void
     */
    public void modiProduct(Product product, User user) throws BusinessException {
        product.setGmtModified(LocalDateTime.now());
        product.setModifier(user);
        ProductPo po = CloneFactory.copy(new ProductPo(), product);
        int ret = productPoMapper.updateByPrimaryKeySelective(po);
        if (ret == 0) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
    }

    /**
     * 删除商品，连带规格
     *
     * @param id 商品id
     * @return
     */
    public void deleteProduct(Long id) throws BusinessException {
        int ret = productPoMapper.deleteByPrimaryKey(id);
        if (ret == 0) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
    }

    public List<Product> findProductByName_manual(String name) throws BusinessException {
        List<Product> productList;
        ProductPoExample example = new ProductPoExample();
        ProductPoExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<ProductAllPo> productPoList = productAllMapper.getProductWithAll(example);
        productList = productPoList.stream().map(o -> CloneFactory.copy(new Product(), o)).collect(Collectors.toList());
        logger.debug("findProductByName_manual: productList = {}", productList);
        return productList;
    }

    public List<Product> findProductByName_join(String name) {
        // 从数据库一次性获取查询结果，每行作为一个 Map<String, Object>
        List<Map<String, Object>> rawData = productAllMapper.getProductWithAllByJoinRawData(name);

        // 用于存储每个 Product 的唯一实例
        Map<Long, Product> productMap = new HashMap<>();

        // 遍历查询结果
        for (Map<String, Object> row : rawData) {
            Long productId = (Long) row.get("product_id");

            // 初始化 Product，如果 map 中尚未存在该 productId
            Product product = productMap.computeIfAbsent(productId, id -> {
                Product p = new Product();
                p.setId(id);
                p.setSkuSn((String) row.get("sku_sn"));
                p.setName((String) row.get("name"));

                System.out.println("original_price: " + row.get("original_price"));
                p.setOriginalPrice((Long) row.get("original_price"));

                p.setWeight((Long) row.get("weight"));
                p.setBarcode((String) row.get("barcode"));
                p.setUnit((String) row.get("unit"));
                p.setOriginPlace((String) row.get("origin_place"));
                p.setCommissionRatio((Integer) row.get("commission_ratio"));
                p.setFreeThreshold((Long) row.get("free_threshold"));

                // 将 status 字段设置为 Byte 类型
                Integer statusValue = (Integer) row.get("status");
                if (statusValue != null) {
                    p.setStatus(statusValue.byteValue());
                }

                p.setGmtCreate((LocalDateTime) row.get("gmt_create"));
                p.setGmtModified((LocalDateTime) row.get("gmt_modified"));

                // 设置 creator 和 modifier
                User creator = new User();
                creator.setId((Long) row.get("creator_id"));
                creator.setName((String) row.get("creator_name"));
                p.setCreator(creator);

                User modifier = new User();
                modifier.setId((Long) row.get("modifier_id"));
                modifier.setName((String) row.get("modifier_name"));
                p.setModifier(modifier);

                return p;
            });

            // 将 onSale 数据添加到 onSaleList 中
            if (row.get("onSale_id") != null) {
                OnSale onSale = new OnSale();
                onSale.setId((Long) row.get("onSale_id"));

                //System.out.println("onSale_price: " + row.get("onSale_price"));
                onSale.setPrice((Long) row.get("onSale_price"));
                //System.out.println("onSale_price: " + onSale.getPrice());

                onSale.setBeginTime((LocalDateTime) row.get("onSale_begin_time"));
                onSale.setEndTime((LocalDateTime) row.get("onSale_end_time"));
                onSale.setQuantity((Integer) row.get("onSale_quantity"));
                onSale.setMaxQuantity((Integer) row.get("onSale_max_quantity"));

                User onSaleCreator = new User();
                onSaleCreator.setId((Long) row.get("onSale_creator_id"));
                onSaleCreator.setName((String) row.get("onSale_creator_name"));
                onSale.setCreator(onSaleCreator);

                User onSaleModifier = new User();
                onSaleModifier.setId((Long) row.get("onSale_modifier_id"));
                onSaleModifier.setName((String) row.get("onSale_modifier_name"));
                onSale.setModifier(onSaleModifier);

                onSale.setGmtCreate((LocalDateTime) row.get("onSale_gmt_create"));
                onSale.setGmtModified((LocalDateTime) row.get("onSale_gmt_modified"));

                product.getOnSaleList().add(onSale);
            }

            // 将 otherProduct 数据添加到 otherProduct 列表中
            if (row.get("otherProduct_id") != null) {
                Product otherProduct = new Product();
                otherProduct.setId((Long) row.get("otherProduct_id"));
                otherProduct.setSkuSn((String) row.get("otherProduct_skuSn"));
                otherProduct.setName((String) row.get("otherProduct_name"));
                otherProduct.setOriginalPrice((Long) row.get("otherProduct_originalPrice"));
                otherProduct.setWeight((Long) row.get("otherProduct_weight"));
                otherProduct.setBarcode((String) row.get("otherProduct_barcode"));
                otherProduct.setUnit((String) row.get("otherProduct_unit"));
                otherProduct.setOriginPlace((String) row.get("otherProduct_originPlace"));

                product.getOtherProduct().add(otherProduct);
            }
        }

        // 返回包含所有 Product 对象的列表
        return new ArrayList<>(productMap.values());
    }

    /**
     * 用GoodsPo对象找Goods对象
     *
     * @param productId
     * @return Goods对象列表，带关联的Product返回
     */
    public Product findProductByID_manual(Long productId) throws BusinessException {
        Product product = null;
        ProductPoExample example = new ProductPoExample();
        ProductPoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(productId);
        List<ProductAllPo> productPoList = productAllMapper.getProductWithAll(example);

        if (productPoList.isEmpty()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "产品id不存在");
        }
        product = CloneFactory.copy(new Product(), productPoList.get(0));
        logger.debug("findProductByID_manual: product = {}", product);
        return product;
    }

    public List<Product> findProductByNameWithJPA(String name) {
        // 查询名称匹配的所有基本商品信息
        List<ProductJPAPo> productJPAPoList = productRepository.findByName(name);
        List<Product> products = new ArrayList<>();

        for (ProductJPAPo productJPAPo : productJPAPoList) {
            // 创建 Product 实体，并复制基本信息
            Product product = CloneFactory.copy(new Product(), productJPAPo);

            // 获取当前商品的 ID 和 Goods ID
            Long productId = productJPAPo.getId();
            Long goodsId = productJPAPo.getGoodsId();

            // 查询并设置关联的 onSale 信息
            List<OnSaleJPAPo> onSaleList = onSaleRepository.findLatestOnSaleByProductId(productId);
            List<OnSale> onSaleEntities = onSaleList.stream()
                    .map(onSaleJPAPo -> CloneFactory.copy(new OnSale(), onSaleJPAPo))
                    .collect(Collectors.toList());
            product.setOnSaleList(onSaleEntities);

            // 查询并设置关联的 otherProduct 信息
            List<ProductJPAPo> otherProductList = productRepository.findByGoodsIdAndIdNot(goodsId, productId);
            List<Product> otherProducts = otherProductList.stream()
                    .map(po -> CloneFactory.copy(new Product(), po))
                    .collect(Collectors.toList());
            product.setOtherProduct(otherProducts);

            // 将完整的 product 添加到结果列表
            products.add(product);
        }

        return products;
    }

}

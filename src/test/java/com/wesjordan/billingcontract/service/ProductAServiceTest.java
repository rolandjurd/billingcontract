package com.wesjordan.billingcontract.service;


import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ProductAServiceTest {

    @InjectMocks
    private ProductAService productAService;

    @Mock
    private ProductARepository productARepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProductAWithSpecificAccountId(){
        //given
        ProductA testProductA = getProductA1();
        given(this.productAService.getProductByAccountId(1L)).willReturn(testProductA);

        //when
        ProductA p = productAService.getProductByAccountId(1L);

        //then
        assertEquals(p.getAccountId(),testProductA.getAccountId());
        assertEquals(p.getContractLength(),testProductA.getContractLength());
        assertEquals(p.getCharge(),testProductA.getCharge());
    }

    @Test
    public void testGetAllProductA(){
        //given
        Iterable<ProductA> testProductAList = getProductAList();
        given(this.productAService.getAllProducts()).willReturn(testProductAList);

        //when
        Iterable<ProductA> productAIterable = productAService.getAllProducts();

        //then
        assertEquals(productAIterable, testProductAList);
    }

    @Test
    public void testAddProductA(){
        //given
        ProductA testProductA = getProductA1();
        given(this.productARepository.save(testProductA)).willReturn(testProductA);

        //when
        ProductA savedProductA = productAService.addProductA(testProductA);

        verify(productARepository, times(1)).save(testProductA);
        assertEquals(testProductA.getAccountId(),savedProductA.getAccountId());
    }

    private ProductA getProductA1(){
        return getProductA(1L, 3, BigDecimal.valueOf(3500), BigDecimal.valueOf(1400));
    }

    private ProductA getProductA2(){
        return getProductA(2L, 6, BigDecimal.valueOf(4500), BigDecimal.valueOf(2000));
    }

    private Iterable<ProductA> getProductAList(){
        List<ProductA> productAS = new ArrayList<>();
        productAS.add(getProductA1());
        productAS.add(getProductA2());

        return productAS;
    }

    private ProductA getProductA(long accountId, int contractLength, BigDecimal charge, BigDecimal setupCharge) {
        ProductA productA = new ProductA();
        productA.setAccountId(accountId);
        productA.setContractLength(contractLength);
        productA.setCharge(charge);
        productA.setSetupCharge(setupCharge);
        productA.setStartDate(new Date());
        return productA;
    }
}

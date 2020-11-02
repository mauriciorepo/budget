package com.finance.budget.model.repository;

import java.util.Arrays;

import static com.finance.budget.resource.OrderServiceControllerTest.newOrderServiceInstance;
import com.finance.budget.model.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class OrderServiceRepositoryTest {


    @Test
    @DisplayName("should persist and retrun a order service with itmes")
    public void persistOrderService(){

        OrderService orderService=newOrderServiceInstance();


    }

}

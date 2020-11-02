package com.finance.budget.resource;

import com.finance.budget.model.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = OrderServiceItemsController.class)
public class OrderServiceItemsControllerTest {

    /*@Test
    @DisplayName("should return order service items updated")
    public void updateOrderServiceItemsTest(){

    }*/

    static void createNewOrderServiceWithItems(){
        //return OrderService.builder()
    }
}

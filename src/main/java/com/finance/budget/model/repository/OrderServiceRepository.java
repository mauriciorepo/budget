package com.finance.budget.model.repository;

import java.util.List;
import com.finance.budget.model.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderServiceRepository extends JpaRepository<OrderService,Long> {

    @Query(value="Select o from OrderService as o  join o.company c where c.id= :id or c.name= :name")
    Page<OrderService> findOrderServiceByIdCompanyOrName(@Param("id") Long id, @Param("name") String name, Pageable pageable);

    @Query("Select o from OrderService as o  join o.company c where c.id=:id")
    List<OrderService> findOrderServiceByIdCompany(@Param("id") Long id);
}

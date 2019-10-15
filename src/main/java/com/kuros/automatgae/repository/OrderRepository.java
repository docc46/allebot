package com.kuros.automatgae.repository;

import com.kuros.automatgae.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Integer> {
    List<Order> findByOfferId(String offerId);
    List<Order> findAllByVouchers(String voucher);
    List<Order> findAllByEmail(String email);
    List<Order> findTop3ByOfferId(String offerId);
}

package com.kuros.automatgae.repository;

import com.kuros.automatgae.model.Voucher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoucherRepository extends CrudRepository<Voucher,Integer> {
    List<Voucher> findTop1BySentFalse();
    Voucher findFirstBySentFalseAndValue(String value);
}

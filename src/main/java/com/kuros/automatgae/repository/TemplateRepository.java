package com.kuros.automatgae.repository;

import com.kuros.automatgae.model.msg.MsgTemplate;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface TemplateRepository extends CrudRepository<MsgTemplate, BigInteger> {
    List<MsgTemplate> findAll();
}

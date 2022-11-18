package com.credit.webcredit.service;

import com.credit.webcredit.entity.Bank;
import com.credit.webcredit.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService{

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAllBank() {
        return bankRepository.findAll();
    }
}

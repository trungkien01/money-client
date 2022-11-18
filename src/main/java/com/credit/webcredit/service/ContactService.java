package com.credit.webcredit.service;

import com.credit.webcredit.dto.ContactDto;
import org.springframework.ui.Model;

public interface ContactService {
    String saveConteact(ContactDto contactDto, Model model);
}

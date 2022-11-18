package com.credit.webcredit.service;

import com.credit.webcredit.dto.ContactDto;
import com.credit.webcredit.entity.Contact;
import com.credit.webcredit.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;
    public  final UsersService usersService;
    public ContactServiceImpl(ContactRepository contactRepository, UsersService usersService) {
        this.contactRepository = contactRepository;
        this.usersService = usersService;
    }

    @Override
    public String saveConteact(ContactDto contactDto, Model model) {

        String VALID_NAME = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
                "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
                "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
        Pattern patternName = Pattern.compile(VALID_NAME);
        Matcher matcherName = patternName.matcher(contactDto.getFullName());
        if (contactDto.getFullName().isEmpty()){
            model.addAttribute("errFullName","Họ tên không được để trống!");
            return "contact";
        }else if (!matcherName.matches()){
            model.addAttribute("errFullName","Họ tên không úng định dạng!");
            return "contact";
        }
        String VALID_EMAIL = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
        Pattern pattern = Pattern.compile(VALID_EMAIL);
        Matcher matcher = pattern.matcher(contactDto.getEmail());
        if (contactDto.getEmail().isEmpty()){
            model.addAttribute("errEmail","Email không được để trống!");
            return "contact";
        }else if (!matcher.matches()){
            model.addAttribute("errEmail","Email không đúng định dạng!");
            return "contact";
        }
        String VALID_PHONE = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern patternPhone = Pattern.compile(VALID_PHONE);
        Matcher matcherPhone = patternPhone.matcher(contactDto.getPhone());
        if (contactDto.getPhone().isEmpty()){
            model.addAttribute("errPhone","Số điện thoại không được để trống!");
            return "contact";
        }else if (!matcherPhone.matches()){
            model.addAttribute("errPhone","Số điện thoại không đúng!");
            return "contact";
        }

        if (contactDto.getMessage().isEmpty()){
            model.addAttribute("errMessage","Nội dung không được để trống!");
            return "contact";
        }
        Contact contact = new Contact();
        Long userId = usersService.getUserId();
        contact.setFullName(contactDto.getFullName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setMessage(contactDto.getMessage());
        contact.setUserId(userId);
        contactRepository.save(contact);
        model.addAttribute("msg","Bạn đã gửi phản hồi thành công.");
        return "contact";
    }
}

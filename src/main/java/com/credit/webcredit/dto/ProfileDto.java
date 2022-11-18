package com.credit.webcredit.dto;

import com.credit.webcredit.enums.CardCitizen;
import com.credit.webcredit.enums.Gender;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ProfileDto {
    private Long id;

    private Long idUsers;

    private String imgCard1; // mặt trước thẻ cccd/cmnd

    private String imgCard2; // mặt sau thẻ cccd/cmnd

    private String imgUsers; // ảnh mặt khách hàng

    private String name;

    private String lastName;

    private String fullName;

    private String bankAccount; // tài khoản ngân hàng

    private String nameBankAccount; // tên tài khoản ngân hàng

    private String nameBankUsers; // tên khách hàng của tài khoản ngân hàng

    private String dateOfBirth;

    private Integer age;

    private String province;

    private String district;

    private String subDistrict;

    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String imgHousehold;

    @Enumerated(EnumType.STRING)
    private CardCitizen cccdCmnd;

    private String numberCccdCmnd;

    private String phone;

    private Double loanPrice; //Số tiền vay

    private Integer moneyMonth; //Số tháng vay

    private Double payMoneyMonth; //số tiền trả mỗi tháng

    private Double interestRate; // lãi suất
}

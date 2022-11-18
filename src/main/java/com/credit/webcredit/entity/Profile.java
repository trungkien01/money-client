package com.credit.webcredit.entity;

import com.credit.webcredit.enums.CardCitizen;
import com.credit.webcredit.enums.Gender;
import com.credit.webcredit.enums.TypeLoan;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "img_card1")
    private String imgCard1; // mặt trước thẻ cccd/cmnd

    @Column(name = "img_card2")
    private String imgCard2; // mặt sau thẻ cccd/cmnd

    @Column(name = "img_users")
    private String imgUsers; // ảnh mặt khách hàng

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "bank_account")
    private String bankAccount; // tài khoản ngân hàng

    @Column(name = "name_bank_account")
    private String nameBankAccount; // tên tài khoản ngân hàng

    @Column(name = "name_bank_users")
    private String nameBankUsers; // tên khách hàng của tài khoản ngân hàng

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "sub_district")
    private String subDistrict; // xã

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "img_household_left")
    private String imgHouseholdLeft; // ảnh chữ kí viết tay

    @Column(name = "card_citizen")
    @Enumerated(EnumType.STRING)
    private CardCitizen cardCitizen;

    @Column(name = "number_cccd_cmnd")
    private String numberCccdCmnd;

    @Column(name = "phone")
    private String phone;

    @Column(name = "loan_price")
    private Double loanPrice; //Số tiền vay

    @Column(name = "money_month")
    private Integer moneyMonth; //Số tháng vay

    @Column(name = "pay_money_month")
    private Double payMoneyMonth; // so tien hang thang phai tra

    @Column(name = "type_loan")
    @Enumerated(EnumType.ORDINAL)
    private TypeLoan typeLoan;

    @Column(name = "interest_rate")
    private Double interestRate;
}

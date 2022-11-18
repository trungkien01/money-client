package com.credit.webcredit.enums;

public enum TypeLoan {
    NOT_ACTIVE, // chưa đăng ký vay
    ACTIVE, // đã đăng ký vay
    ACCEPT, // phê duyệt
    BORROWING, // đang vay
    BAD_DEBT, // nợ xấu
    COMPLETE // hoàn thành trả hết
}

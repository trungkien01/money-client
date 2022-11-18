package com.credit.webcredit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transaction_history")
public class TransactionHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "loan_month") // số tháng vay
    private Integer loanMonth;

    @Column(name = "price_remaining") // số tiền còn lại phải đóng
    private Double priceRemaining;

    @Column(name = "price_monthly") // Số tền đóng hàng tháng
    private Double priceMonthly;

    @Column(name = "price_sum") // tổng số tiền phải đóng
    private Double priceSum;

    @Column(name = "user_id")
    private Long userId;
}

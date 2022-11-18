package com.credit.webcredit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "email")
public class EmailDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recipient")
    private String recipient;
    @Column(name = "smg_body")
    private String msgBody;
    @Column(name = "subject")
    private String subject;
    @Column(name = "attachment")
    private String attachment;

}

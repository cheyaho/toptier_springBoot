package com.toptier.web.entity;

import com.toptier.web.dto.FranchiseRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TBFranchise")
@EntityListeners(value = {AuditingEntityListener.class})
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="reqName", columnDefinition = "varchar(30)")
    private String reqName;

    @Column(name="reqPhoneNum", columnDefinition = "varchar(15)")
    private String reqPhoneNum;

    @Column(name="reqEmail", columnDefinition = "varchar(50)")
    private String reqEmail;

    @Column(name="reqAddr1", columnDefinition = "varchar(100)")
    private String reqAddr1;

    @Column(name="reqAddr2", columnDefinition = "varchar(100)")
    private String reqAddr2;

    @Column(name="isShop", columnDefinition = "varchar(1)")
    private String isShop;

    @Column(name="confirmYN", columnDefinition = "varchar(1)")
    private String confirmYN;

    @Column(name="shopSize")
    private int shopSize;

    @Column(name="operMethod", columnDefinition = "varchar(1)")
    private String operMethod;

    @Column(name="operExp", columnDefinition = "varchar(1)")
    private String operExp;

    @Column(name="etc", columnDefinition = "text")
    private String etc;

    @Column(name="hidden", columnDefinition = "varchar(1)")
    private String hidden;

    @Column(name="insDate")
    @CreatedDate
    private LocalDateTime insDate;

    @Column(name="updDate")
    @LastModifiedDate
    private LocalDateTime updDate;

    public Franchise(FranchiseRequest reqFranchise) {
        this.reqName = reqFranchise.reqName();
        this.reqPhoneNum = reqFranchise.reqPhoneNum1() + "-" + reqFranchise.reqPhoneNum2() + "-" + reqFranchise.reqPhoneNum3();
        this.reqEmail = reqFranchise.emailId() + "@" + reqFranchise.emailDomain();
        this.reqAddr1 = reqFranchise.reqAddr1();
        this.reqAddr2 = reqFranchise.reqAddr2();
        this.isShop = reqFranchise.isShop();
        this.confirmYN = reqFranchise.confirmYN();
        this.shopSize = reqFranchise.shopSize();
        this.operMethod = reqFranchise.operMethod();
        this.operExp = reqFranchise.operExp();
        this.etc = reqFranchise.etc();
        this.hidden = reqFranchise.hidden();
    }
}

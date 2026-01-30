package com.toptier.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "TBMenu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", referencedColumnName = "cate")
    private MenuCategory category;

    @Column(name="name_kr", columnDefinition = "varchar(100)")
    private String nameKr;

    @Column(name="name_en", columnDefinition = "varchar(100)")
    private String nameEn;

    @Column(name="imagePath", columnDefinition = "varchar(300)")
    private String imagePath;

    @Column(name="thumbPath", columnDefinition = "varchar(300)")
    private String thumbPath;

    @Column(name="description")
    private String description;

    @Column(name="signatureYN")
    private String signatureYN;

    @Column(name="bestYN")
    private String bestYN;

    @Column(name="newYN")
    private String newYN;

    @Column(name="status")
    private String status;

    @Column(name="insDate")
    @CreatedDate
    private LocalDateTime insDate;

    @Column(name="updDate")
    @LastModifiedDate
    private LocalDateTime upDate;
}

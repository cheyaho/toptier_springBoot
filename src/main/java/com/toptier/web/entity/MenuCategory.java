package com.toptier.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TBMenuCategory")
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="cate", columnDefinition = "char(2)")
    private String cate;

    @Column(name="parent", columnDefinition = "char(2)")
    private String parent;

    @Column(name="categoryName", columnDefinition = "varchar(50)")
    private String categoryName;
}

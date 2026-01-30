package com.toptier.web.entity;

import com.toptier.web.dto.ShopRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TBShop")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="shopName")
    private String shopName;

    @Column(name="addr")
    private String addr;

    @Column(name="addr2")
    private String addr2;

    @Column(name="shopTel")
    private String shopTel;

    @Column(name="operTime")
    private String operTime;

    @Column(name="lat")
    private String lat;

    @Column(name="lng")
    private String lng;

    @Column(name="hidden")
    private String hidden;

    @Column(name="insDate")
    @CreatedDate
    private LocalDateTime insDate;

    @Column(name="updDate")
    @LastModifiedDate
    private LocalDateTime updDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shop_amenity_id")
    private ShopAmenity amenity;

    public void update(ShopRequest reqShop) {
        this.shopName = reqShop.shopName();
        this.addr = reqShop.addr();
        this.addr2 = reqShop.addr2();
        this.shopTel = reqShop.shopTel();
        this.operTime = reqShop.operTime();
        this.lat = reqShop.lat();
        this.lng = reqShop.lng();
        this.hidden = reqShop.hidden();
        if(this.amenity != null) {
            this.amenity.update(reqShop);
        }else{
            this.amenity = ShopAmenity.builder()
                    .takeOutYN(reqShop.takeoutYN())
                    .seatYN(reqShop.seatYN())
                    .wifiYN(reqShop.wifiYN())
                    .deliveryYN(reqShop.deliveryYN())
                    .parkingYN(reqShop.parkingYN())
                    .desertYN(reqShop.desertYN())
                    .easypayYN(reqShop.easyPayYN())
                    .build();
        }
    }
}

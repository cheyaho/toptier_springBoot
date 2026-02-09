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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBShopAmenity")
@EntityListeners(value = {AuditingEntityListener.class})
@Builder
public class ShopAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="takeOutYN")
    private String takeOutYN;

    @Column(name="seatYN")
    private String seatYN;

    @Column(name="wifiYN")
    private String wifiYN;

    @Column(name="deliveryYN")
    private String deliveryYN;

    @Column(name="parkingYN")
    private String parkingYN;

    @Column(name="desertYN")
    private String desertYN;

    @Column(name="easypayYN")
    private String easypayYN;

    @Column(name="insDate")
    @CreatedDate
    private LocalDateTime insDate;

    @Column(name="updDate")
    @LastModifiedDate
    private LocalDateTime updDate;

    public void update(ShopRequest reqShop) {
        this.takeOutYN = reqShop.takeoutYN();
        this.seatYN = reqShop.seatYN();
        this.wifiYN = reqShop.wifiYN();
        this.deliveryYN = reqShop.deliveryYN();
        this.parkingYN = reqShop.parkingYN();
        this.desertYN = reqShop.desertYN();
        this.easypayYN = reqShop.easyPayYN();
    }
}

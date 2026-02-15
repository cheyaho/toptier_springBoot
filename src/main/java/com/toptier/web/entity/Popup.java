package com.toptier.web.entity;

import com.toptier.web.dto.PopupRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="TBPopup")
@EntityListeners(value = {AuditingEntityListener.class})
public class Popup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int width;
    private int height;
    private String useYn;
    private String cookie;
    private LocalDateTime insDate;
    private String insEmp;
    private LocalDateTime updDate;
    private String updEmp;

    public void update(PopupRequest popup) {
        this.title = popup.title();
        this.content = popup.content();
        this.startDate = popup.startDate();
        this.endDate = popup.endDate();
        this.width = popup.width();
        this.height = popup.height();
        this.useYn = popup.useYn();
        this.cookie = popup.cookie();
    }
}

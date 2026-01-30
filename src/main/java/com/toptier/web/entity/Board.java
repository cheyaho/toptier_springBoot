package com.toptier.web.entity;

import com.toptier.web.dto.AddBoardRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TBBoards")
@EntityListeners(value = {AuditingEntityListener.class})
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seq", updatable = false)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardType", referencedColumnName = "seq")
    private BoardType boardType;

    @Column(name="title", columnDefinition = "varchar(100)")
    private String title;

    @Column(name="content", columnDefinition = "text")
    private String content;

    @Column(name="writer", columnDefinition = "varchar(50)")
    private String writer;

    @Column(name="filePath", columnDefinition = "varchar(500)")
    private String filePath;

    @Column(name="hits")
    private int hits;

    @Column(name="hidden")
    private String hidden;

    @Column(name="insDate")
    @CreatedDate
    private LocalDateTime insDate;

    @Column(name="insEmp")
    private String insEmp;

    @Column(name="updDate")
    @LastModifiedDate
    private LocalDateTime updDate;

    @Column(name="updEmp")
    private String updEmp;

    public Board(AddBoardRequest request) {
        this.boardType = request.boardType();
        this.title = request.title();
        this.content = request.content();
        this.hits = 0;
        if(request.filePath() == null) this.filePath = "";
            else this.filePath = request.filePath();
        if(request.hidden() == null) this.hidden = "N";
            else this.hidden = request.hidden();
    }

    public void update(AddBoardRequest request) {
        this.title = request.title();
        this.content = request.content();
        if(request.filePath() == null) this.filePath = "";
            else this.filePath = request.filePath();
        this.hits = this.hits++;
    }
}

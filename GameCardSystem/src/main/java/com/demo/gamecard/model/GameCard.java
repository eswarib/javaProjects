package com.demo.gamecard.model;

import java.util.Date;


        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import com.vladmihalcea.hibernate.type.json.JsonStringType;

        import org.springframework.data.annotation.CreatedDate;
        import org.springframework.data.annotation.LastModifiedDate;
        import org.springframework.data.jpa.domain.support.AuditingEntityListener;

        import javax.persistence.*;
        import javax.validation.constraints.NotBlank;

        import java.util.Date;

        import org.hibernate.annotations.Type;
        import org.hibernate.annotations.TypeDef;
        import org.hibernate.annotations.TypeDefs;
        import org.springframework.boot.configurationprocessor.json.JSONObject;
        import org.springframework.boot.configurationprocessor.json.JSONStringer;


@Entity
@Table(name = "GameCard")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)

@TypeDef(name = "json", typeClass = JsonStringType.class)

//enum EventType{NotReady_Offline,Ready_Ready,NotReady_Wrapup,NotReady_Busy};
//enum ReportObjectType{Agent,Queue,Call,EMail,WhatsApp};

public class GameCard {


    @Id
    private Long CardNumber;

    private Long Balance;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date SwipeInTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date SwipeOutTime;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date CreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date UpdatedAt;

    public Long getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(Long cardNum) {
        this.CardNumber = cardNum;
    }

    public Long getBalance() {return Balance;}

    public void setBalance(Long bal) { this.Balance = bal;}

    public Date getSwipeInTime() { return SwipeInTime; }

    public void setSwipeInTime() { this.SwipeInTime = SwipeInTime; }

    public Date getSwipeOutTime() { return SwipeOutTime; }

    public void setSwipeOutTime() { this.SwipeOutTime = SwipeOutTime; }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.CreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.UpdatedAt = updatedAt;
    }
}



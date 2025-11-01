package br.com.cursosEBS.payment.entity;

import br.com.cursosEBS.payment.http.dto.SubscriptionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    private Long userId;
    @Column(columnDefinition = "CHAR(36)")
    private String subscriptionId;
    private double value;
    private String methodPayment;
    @Enumerated(EnumType.STRING)
    private StatusPayment status;
    private LocalDateTime createdAt;
    private String paidMarketId;

    public Payment(Long user ,double result, String defaultPaymentMethodId, StatusPayment statusPayment, String paidMarketId) {

        this.userId = user;
        this.value = result;
        this.methodPayment = defaultPaymentMethodId;
        this.status = statusPayment;
        this.createdAt = LocalDateTime.now();
        this.paidMarketId = paidMarketId;
    }

    public Payment(Payment paymentEntity) {

        this.userId = paymentEntity.getUserId();
        this.value = paymentEntity.getValue();
        this.methodPayment = paymentEntity.getMethodPayment();
        this.status = paymentEntity.getStatus();
        this.createdAt = LocalDateTime.now();
        this.paidMarketId = paymentEntity.getPaidMarketId();
    }
}

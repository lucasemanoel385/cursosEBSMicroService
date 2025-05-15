package br.com.cursosEBS.enrollments.entity;

import br.com.cursosEBS.enrollments.dto.RegisterSubscriptionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private StatusPayment status;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String paidMarketId;

    public Subscription(RegisterSubscriptionDTO dto) {
        this.userId = dto.userId();
        this.status = dto.status();
        this.expirationDate = dto.expirationsDate();
        this.paidMarketId = dto.paidMarketId();
    }
}

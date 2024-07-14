package com.sixplus.server.api.user.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user_action_logs", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
public class UserActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String description;
    private LocalDateTime timestamp;

    @Column(name = "user_id")
    private String userId;

    public UserActionLog(String action, String description, String userId) {
        this.action = action;
        this.description = description;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }
}
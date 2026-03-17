package com.dev.gym.memberships;

import com.dev.gym.memberships.domain.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "members")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    private boolean active = true;
}
package com.gdsc.illuwabang.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String sub;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Provider provider;

    @Column
    private String image;

    @Builder
    public User(String sub, String name, String email, Provider provider, String image) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.image = image;
    }

}

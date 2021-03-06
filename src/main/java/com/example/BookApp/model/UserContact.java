package com.example.BookApp.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_contact")
public class UserContact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name = "user_id")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('PHONE','EMAIL')")
    private ContactType type;

    @Column(nullable = false)
    private Byte approved;

    private String code;

    @Column(name = "code_trials")
    private Integer codeTrials;

    @Column(name = "code_time")
    private LocalDateTime codeTime;

    @Column(nullable = false)
    private String contact;
}
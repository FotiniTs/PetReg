package com.pet.registry.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pet.registry.models.enums.AnimalType;
import com.pet.registry.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(length = 32, columnDefinition = "varchar(32) default 'Cat'")
    @Enumerated(EnumType.STRING)
    AnimalType type;

    @Column(length = 32, columnDefinition = "varchar(32) default 'Male'")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date birth_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizen_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    User citizen;

    @OneToMany(mappedBy = "animal", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<MedicalCase> medical_cases;

    @Column(nullable = false)
    int microchip_code;

    @Column(columnDefinition = "boolean default false", nullable = false)
    boolean validated = false;

    @Column(columnDefinition = "boolean default false", nullable = false)
    boolean missing = false;
}

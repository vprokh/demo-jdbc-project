package com.example.hibernate.model;

import com.example.hibernate.converter.BirthDateConverter;
import com.example.jdbc.model.Role;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "my_users_combined_pk", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
public class MyUserCombinedPk {
    @EmbeddedId
    private CombinedMyUserPk combinedPk;

    @Column(name = "email", nullable = false)
    private String email;

    private String PhoneNumber;
    @Convert(converter = BirthDateConverter.class)
    private BirthDate birthDate;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    @AttributeOverride(name = "now", column = @Column(name = "createAt"))
    private Audit audit;
    @Transient
    private String itWillNotBeAddedToDb = "value";
}

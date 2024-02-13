package com.example.hibernate.model;

import com.example.hibernate.converter.BirthDateConverter;
import com.example.jdbc.model.Role;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "my_users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
@NoArgsConstructor
public class MyUser { // my_user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my-gen")
//    @SequenceGenerator(name = "my-gen", sequenceName = "my_users_id_seq", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table-gen")
//    @TableGenerator(name = "table-gen", table = "id_table", pkColumnName = "users_id", valueColumnName = "users_id_value")
    private Long id;
    @Column(name = "first_name", unique = true, nullable = true)
    private String name;
    @Column(name = "last_name", insertable = false, updatable = false)
    private String lastName;
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
}

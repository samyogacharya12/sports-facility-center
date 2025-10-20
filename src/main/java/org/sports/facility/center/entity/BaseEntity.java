package org.sports.facility.center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date", nullable = false)
    private String createdDate;

    @Column(name = "updated_date")
    private String updatedDate;


    @Column(name = "deleted", nullable = false)
    private Boolean deleted=false;

    @Column(name = "status", nullable = false)
    private Boolean status=false;
}

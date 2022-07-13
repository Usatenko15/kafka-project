package com.example.clientapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order1")
public class Order implements Serializable {
    private static final long serialVersionUID = -4551323276601557391l;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String status;

//    @CreationTimestamp
//    private LocalDateTime createdDate;

//    @UpdateTimestamp
//    private LocalDateTime updatedDate;

}

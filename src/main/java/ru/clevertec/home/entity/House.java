package ru.clevertec.home.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "area")
    private double area;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_owners_houses",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "person_owner_id")
    )
    private List<Person> owners;

    public void addOwners(Person person) {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        owners.add(person);
    }
}

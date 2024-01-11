package ru.clevertec.home.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
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

    @NaturalId
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "area")
    private double area;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "create_date")
    private LocalDateTime createDate;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "person_owners_houses",
//            joinColumns = @JoinColumn(name = "house_id"),
//            inverseJoinColumns = @JoinColumn(name = "person_owner_id")
//    )
//    private List<Person> owners;
//
//    public void addOwners(Person person) {
//        if (owners == null) {
//            owners = new ArrayList<>();
//        }
//        owners.add(person);
//    }
}

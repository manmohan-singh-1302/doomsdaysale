/*
* This is the user model */

package com.doomsdaysale.model;

import com.doomsdaysale.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

// This will create a user entity
@Entity
@Getter // This will provide all the getter methods for the class
@Setter // This will provide all the setter methods for the class
@AllArgsConstructor // This will provide a constructor with all the arguments or fields of the class
@NoArgsConstructor // This will provide a constructor with no arguments for the class
@EqualsAndHashCode // This will provide the equal and hashCode method for the object.
public class User {

    // This will create id as the primary key
    @Id
    // This will generate the value of id automatically
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    // This property will allow the entity to access the password incoming and to be converted into a java object (deserialization).
    // When we fetch user data password will not be fetched as serialization is not allowed.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String fullName;

    private String mobile;

    // we have created an enum for role which specifies the types of roles for the application use and by default setting it as customer
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    // This will allow the user to fetch the address and maintain a unique address in the database.
    @OneToMany // a user can have multiple addresses
    private Set<Address> addresses = new HashSet<>();

    // This will allow the application to keep track of the coupons used by the user.
    @ManyToMany // a user can have multiple coupons and a coupon can be used by many users.
    @JsonIgnore // this excludes the property while sending data to frontend.
    private Set<Coupon> usedCoupons = new HashSet<>();
}

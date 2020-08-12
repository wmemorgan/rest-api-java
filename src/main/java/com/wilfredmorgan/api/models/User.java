package com.wilfredmorgan.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table.
 */
@Entity
@Table(name = "users")
public class User extends Auditable {

    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User first name (String)
     */
    private String firstname;

    /**
     * User last name (String)
     */
    private String lastname;

    /**
     * Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String primaryemail;

    /**
     * The password (String) for this user. Cannot be null. Never get displayed
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    /**
     * Default constructor required by JPA
     */
    public User() {
    }

    /**
     * Given the parameters, create a new user object
     * <p>
     *
     * @param username
     * @param firstname
     * @param lastname
     * @param primaryemail
     * @param password
     */
    public User(String username, String firstname, String lastname, String primaryemail, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.primaryemail = primaryemail;
        this.password = password;
    }

    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */
    public long getUserid() {
        return userid;
    }

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public void setUserid(long userid) {
        this.userid = userid;
    }

    /**
     * Getter for username
     *
     * @return the username (String) lowercase
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username
     *
     * @param username the new username (String) converted to lowercase
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for first name
     *
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for first name
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for last name
     *
     * @return the last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for last name
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * getter for primary email
     *
     * @return the primary email (String) for the user converted to lowercase
     */
    public String getPrimaryemail() {
        return primaryemail;
    }

    /**
     * setter for primary email
     *
     * @param primaryemail the new primary email (String) for the user converted to lowercase
     */
    public void setPrimaryemail(String primaryemail) {
        this.primaryemail = primaryemail;
    }

    /**
     * Getter for the password
     *
     * @return the password (String) of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password to be used internally, after the password has been encrypted
     *
     * @param password the new password (String) for the user. Comes in encrypted
     */
    public void setPasswordNoEncrypt(String password) {
        this.password = password;
    }

    /**
     * Setter for password
     *
     * @param password the new password (String) for the user. Comes in plain text and goes out encrypted
     */
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    /**
     * Getter for user role combinations
     *
     * @return A list of user role combinations associated with this user
     */
    public Set<UserRoles> getRoles() {
        return roles;
    }

    /**
     * Setter for user role combinations
     *
     * @param roles Change the list of user role combinations associated with this user to this one
     */
    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles) {
            String myRole = "ROLE_" + r.getRole()
                    .getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}

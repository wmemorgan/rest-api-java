package com.wilfredmorgan.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
     * User first name (String)
     */
    private String firstname;

    /**
     * User last name (String)
     */
    private String lastname;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @Column(nullable = false, unique = true)
    private String username;

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
     * @param firstname
     * @param lastname
     * @param username
     * @param primaryemail
     * @param password
     */
    public User(String firstname, String lastname, String username, String primaryemail, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
     * Setter for password
     *
     * @param password the new password (String) for the user
     */
    public void setPassword(String password) {
        this.password = password;
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
}

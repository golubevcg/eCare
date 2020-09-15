package eCare.database.entities;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstname;

    @Column
    private String secondname;

    @Column(name="dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name="passportinfo")
    private int passportInfo;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String password;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="role_id")
    private Role role;

    public User() {
    }

    public void setContracts(Contract contract){

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPassportInfo() {
        return passportInfo;
    }

    public void setPassportInfo(int passportInfo) {
        this.passportInfo = passportInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

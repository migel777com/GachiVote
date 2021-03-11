package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "UserEntity")
@Table(name = "users")
public class User implements Serializable
{
    private long user_id;
    private String email;
    private String password;
    private Role role;
    private String first_name;
    private String second_name;
    private String group_name;
    private int age;
    private String interest;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public long getId()
    {
        return this.user_id;
    }
    public void setId(long user_id)
    {
        this.user_id = user_id;
    }

    @Column(name = "email")
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {this.email = email;}

    @Column(name = "password")
    public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    public Role getRole()
    {
        return role;
    }
    public void setRole(Role role)
    {
        this.role = role;
    }

    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Column(name = "second_name")
    public String getSecond_name() {
        return second_name;
    }
    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    @Column(name = "group_name")
    public String getGroup_name() {
        return group_name;
    }
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "interest")
    public String getInterest() {
        return interest;
    }
    public void setInterest(String interest) {
        this.interest = interest;
    }
}
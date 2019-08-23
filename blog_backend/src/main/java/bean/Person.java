package bean;

import java.io.Serializable;

public class Person implements Serializable {
    private Integer id;
    private Integer age;
    private String username;
    private String job;

    public Person() {
    }

    public Person(int id, int age, String name, String job) {
        this.id=id;
        this.age=age;
        this.username=name;
        this.job=job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "age:"+this.age+"name:"+this.username+"job"+this.job;
    }
}

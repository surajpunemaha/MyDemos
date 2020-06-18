package abhinav.com.addresslatlong.Bean;

public class Friend
{
    private String id, name, dob, age;

    public Friend(String id, String name, String dob, String age)
    {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

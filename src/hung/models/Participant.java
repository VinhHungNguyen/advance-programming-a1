package hung.models;

/**
 * Created by hungnguyen on 4/1/17.
 */
public abstract class Participant {

    public static final String TYPE_OFFICER = "officer";
    public static final String TYPE_SPRINTER = "sprinter";
    public static final String TYPE_SUPER = "super";
    public static final String TYPE_SWIMMER = "swimmer";
    public static final String TYPE_CYCLIST = "cyclist";

    protected String id;
    protected String name;
    protected String state;
    protected int age;

    public Participant(String id, String name, String state, int age) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getName()).append(" - ")
//        return new StringBuilder("#")
                .append(id).append(" - ")
                .append(name).append(" - ")
                .append(state).append(" - ")
                .append(age).toString();
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

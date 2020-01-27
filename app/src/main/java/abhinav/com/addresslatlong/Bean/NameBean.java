package abhinav.com.addresslatlong.Bean;

/**
 * Created by abhinav on 27/1/20.
 */

public class NameBean
{
    private String name;
    private boolean checked;

    public NameBean(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

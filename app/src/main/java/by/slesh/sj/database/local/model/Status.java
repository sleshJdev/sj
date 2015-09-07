package by.slesh.sj.database.local.model;

/**
 * Created by slesh on 06.09.2015.
 */
public class Status {
    private String name;
    private Integer graphic;

    public Status() {
    }

    public Status(String name, Integer graphic) {
        this.name = name;
        this.graphic = graphic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGraphic() {
        return graphic;
    }

    public void setGraphic(Integer graphic) {
        this.graphic = graphic;
    }

    @Override
    public String toString() {
        return "Status{" +
                "name='" + name + '\'' +
                ", graphic=" + graphic +
                '}';
    }
}

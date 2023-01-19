package by.mk_jd2_92_22.foodCounter.model;

import java.util.List;

public class JournalFoodList {

    private List<JournalFood> list;

    public JournalFoodList() {
    }

    public JournalFoodList(List<JournalFood> list) {
        this.list = list;
    }

    public List<JournalFood> getList() {
        return list;
    }

    public void setList(List<JournalFood> list) {
        this.list = list;
    }
}

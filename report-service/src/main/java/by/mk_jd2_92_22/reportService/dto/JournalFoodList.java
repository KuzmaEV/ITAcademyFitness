package by.mk_jd2_92_22.reportService.dto;

import by.mk_jd2_92_22.reportService.model.JournalFood;

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

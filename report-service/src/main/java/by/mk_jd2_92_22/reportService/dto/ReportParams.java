package by.mk_jd2_92_22.reportService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Embeddable
public class ReportParams {
    @JsonProperty("from")
    @PastOrPresent(message = "must be past tense")
    @NotNull(message = "param \"From\" is mandatory")
    private LocalDate dtFrom;

    @JsonProperty("to")
    @PastOrPresent(message = "must be past tense")
    @NotNull(message = "pram \"To\" is mandatory")
    private LocalDate dtTo;

    public LocalDate getDtFrom() {
        return dtFrom;
    }

    public void setDtFrom(LocalDate dtFrom) {
        this.dtFrom = dtFrom;
    }

    public LocalDate getDtTo() {
        return dtTo;
    }

    public void setDtTo(LocalDate dtTo) {
        this.dtTo = dtTo;
    }
}

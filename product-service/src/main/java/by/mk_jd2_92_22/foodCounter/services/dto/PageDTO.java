package by.mk_jd2_92_22.foodCounter.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageDTO<T>{

    private int number;
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_element")
    private int totalElements;
    private boolean first;
    @JsonProperty("number_of_element")
    private int numberOfElements;
    private boolean last;
    private List<T> content;

    public PageDTO() {
    }

    public PageDTO(int number,
                   int size,
                   int totalPages,
                   int totalElements,
                   boolean first,
                   int number_of_elements,
                   boolean last,
                   List<T> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = number_of_elements;
        this.last = last;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
    
    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }
    
    public void setLast(boolean last) {
        this.last = last;
    }
    
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}

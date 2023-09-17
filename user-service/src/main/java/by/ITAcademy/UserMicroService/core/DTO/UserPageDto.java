package by.ITAcademy.UserMicroService.core.DTO;

import java.util.Objects;

public class UserPageDto {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Integer totalElements;
    private boolean first;
    private Integer numberOfElements;
    private boolean last;


    public UserPageDto(Integer number, Integer size, Integer totalPages, Integer totalElements, boolean first, Integer numberOfElements, boolean last) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
    }

    public UserPageDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPageDto that = (UserPageDto) o;
        return first == that.first && last == that.last && Objects.equals(number, that.number) && Objects.equals(size, that.size) && Objects.equals(totalPages, that.totalPages) && Objects.equals(totalElements, that.totalElements) && Objects.equals(numberOfElements, that.numberOfElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size, totalPages, totalElements, first, numberOfElements, last);
    }

    @Override
    public String toString() {
        return "UserPageDto{" +
                "number=" + number +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", first=" + first +
                ", numberOfElements=" + numberOfElements +
                ", last=" + last +
                '}';
    }
}

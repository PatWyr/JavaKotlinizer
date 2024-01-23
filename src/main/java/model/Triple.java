package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Triple<F, S, T> {
    F first;
    S second;
    T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triple)) return false;

        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;

        if (getFirst() != null ? !getFirst().equals(triple.getFirst()) : triple.getFirst() != null) return false;
        if (getSecond() != null ? !getSecond().equals(triple.getSecond()) : triple.getSecond() != null) return false;
        if (getThird() != null ? !getThird().equals(triple.getThird()) : triple.getThird() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getFirst() != null ? getFirst().hashCode() : 0;
        result = 31 * result + (getSecond() != null ? getSecond().hashCode() : 0);
        result = 31 * result + (getThird() != null ? getThird().hashCode() : 0);
        return result;
    }
}

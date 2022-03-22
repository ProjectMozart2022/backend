package model;

import java.util.List;

public record Profile(long id, String name, int length, List<Integer> classRange, boolean itn) {
}

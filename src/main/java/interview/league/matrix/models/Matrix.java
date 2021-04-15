package interview.league.matrix.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {
    private List<List<Long>> elements;

    public Matrix(List<List<Long>> elements) {
        this.elements = elements;
    }

    public List<List<Long>> getElements() {
        return elements;
    }

    public void setElements(List<List<Long>> elements) {
        this.elements = elements;
    }

    public String echo() {
        return elements.stream()
                .map(row -> row.stream()
                        .map(num -> num.toString())
                        .collect(Collectors.joining(","))+"\n")
                .collect(Collectors.joining());
    }

    public String invert() {
        int size = elements.size();
        List<List<Long>> invert = new ArrayList<>(size);
        for (int i=0; i<size; i++) {
            invert.add(new ArrayList<>(size));
        }
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++){
                invert.get(j).add(elements.get(i).get(j));
            }
        }
        Matrix mx = new Matrix(invert);
        return mx.echo();
    }

    public String flatten() {
        return elements.stream()
                .map(row -> row.stream()
                        .map(num -> num.toString())
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining(","))+"\n";
    }

    public String sum() {
        BigInteger result = BigInteger.valueOf(0);
        for (List<Long> row : elements) {
            for (Long num : row) {
                result = result.add(BigInteger.valueOf(num));
            }
        }
        return String.valueOf(result)+"\n";
    }

    public String multiply() {
        BigInteger result = BigInteger.valueOf(1);
        for (List<Long> row : elements) {
            for (Long num : row) {
                result = result.multiply(BigInteger.valueOf(num));
            }
        }
        return String.valueOf(result)+"\n";
    }

    public String all() {
        return  "Echo:\n" + echo()
                + "Invert:\n" + invert()
                + "Flatten:\n" + flatten()
                + "Sum:\n" + sum()
                + "Multiply:\n" + multiply();
    }
}

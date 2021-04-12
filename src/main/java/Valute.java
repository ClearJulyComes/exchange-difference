public class Valute {

    private String name;
    private double difference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public Valute(String name, double difference) {
        this.name = name;
        this.difference = difference;
    }
}

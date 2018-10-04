public class Car {

    private String name;
    private int move;

    public Car(String name, int move) {
        this.name = name;
        this.move = move;
    }

    public Car move(MoveStrategy moveStrategy) {
        return moveStrategy.isMovable() ? new Car(name, ++move) : new Car(name, move);
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", move=" + move +
                '}';
    }
}

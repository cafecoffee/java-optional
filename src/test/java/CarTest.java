import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    @Test
    public void 이동() {
        Car car = new Car("test", 0);
        Car actual = car.move(new MoveStrategy() {
            @Override
            public boolean isMovable() {
                return true;
            }
        });
        assertEquals(new Car("test", 1).toString(), actual.toString());
    }

    @Test
    public void 정지() {
        Car car = new Car("test", 0);
        Car actual = car.move(new MoveStrategy() {
            @Override
            public boolean isMovable() {
                return false;
            }
        });
        assertEquals(new Car("test", 0).toString(), actual.toString());
    }

    @Test
    public void 람다_이동() {
        Car car = new Car("test", 0);
        Car actual = car.move(() -> true);
        assertEquals(new Car("test", 1).toString(), actual.toString());
    }

    @Test
    public void 람다_정지() {
        Car car = new Car("test", 0);
        Car actual = car.move(() -> false);
        assertEquals(new Car("test", 0).toString(), actual.toString());
    }

}

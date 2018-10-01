import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalTest {

    @Test
    public void Optional_Empty_isPresent() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void Optional_isPresent() {
        String test = "test";
        Optional<String> testOpt = Optional.of(test);
        assertTrue(testOpt.isPresent());
    }

    @Test
    public void givenNonNull() {
        String test = "test";
        Optional<String> testOpt = Optional.of(test);
        assertEquals("Optional[test]", testOpt.toString());
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_of() {
        String test = null;
        Optional<String> testOpt = Optional.of(test);
    }

    @Test
    public void givenNonNull_ofNullable() {
        String test = "test";
        Optional<String> testOpt = Optional.ofNullable(test);
        assertEquals("Optional[test]", testOpt.toString());
    }

    @Test
    public void givenNull_ofNullable() {
        String test = null;
        Optional<String> testOpt = Optional.ofNullable(test);
        assertEquals("Optional[test]", testOpt.toString());
    }

    @Test
    public void givenOptional_ofNullable_isPresent() {
        Optional<String> opt = Optional.of("test");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    @Test
    public void givenOptional_ofNullable_ifPresent_if() {
        Optional<String> opt = Optional.of("test");

        opt.ifPresent(name -> System.out.println(name.length()));
    }

    @Test
    public void whenOrElseWorks() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("test");
        assertEquals("test", name);
    }

}

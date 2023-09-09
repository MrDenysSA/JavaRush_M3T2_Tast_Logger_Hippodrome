import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\t", "\t\t", "\t \t"})
    public void constructor_EmptyNameParamPassed_ThrowsIllegalArgumentException(String name) {
        String expectedMessage = "Name cannot be blank.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Speed cannot be negative.";
        String name = "TestName";
        double speed = -2;
        double distance = 2;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Distance cannot be negative.";
        String name = "TestName";
        double speed = 2;
        double distance = -2;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        String name = "TestName";
        double speed = 9;
        double distance = 2;
        Horse horse = new Horse(name, speed, distance);

        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        String name = "TestName";
        double speed = 3;
        double distance = 6;
        Horse horse = new Horse(name, speed, distance);

        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistance_ReturnsCorrectDistance() {
        String name = "TestName";
        double speed = 1;
        double distance = 8;
        Horse horse = new Horse(name, speed, distance);

        assertEquals(distance, horse.getDistance());
    }

    @Test
    void move() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 1, 2);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.8, 15, 0, 155})
    void move_UsedFormulaIsCorrect(double fakeRandomValue) {
        String name = "testName";
        double min = 0.2;
        double max = 0.9;
        double speed = 2.5;
        double distans = 250;
        Horse horse = new Horse(name, speed, distans);
        double expectedDistance = distans + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(()-> Horse.getRandomDouble(min,max)).thenReturn(fakeRandomValue);
            horse.move();
        }
        
        assertEquals(expectedDistance, horse.getDistance());
    }
}
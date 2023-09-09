import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HippodromeTest {
    @Test
    public void constructor_NullListArgument_ThrowsIllegalArgumentException() {
        List<Horse> horseList = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horseList));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void constructor_EmptyListArgument_ThrowsIllegalArgumentException() {
        List<Horse> horseList = new ArrayList<>();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horseList));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        assertNotNull(hippodrome.getHorses());
        assertEquals(30, hippodrome.getHorses().size());
        for (int i = 0; i < 30; i++) {
            assertEquals("Horse" + i, hippodrome.getHorses().get(i).getName());
        }
    }

    @Test
    void move_CallsMoveMethodForAllHorses() {
            List<Horse> horseList = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                horseList.add(Mockito.mock(Horse.class));
            }
            Hippodrome hippodrome = new Hippodrome(horseList);

            hippodrome.move();
           for (Horse hors : horseList) {
               Mockito.verify(hors, Mockito.times(1)).move();
           }
    }

    @Test
    void getWinner_ReturnCorrectWinner() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("Horse0", 1 ,1),
                new Horse("Horse1",1,2),
                new Horse("Horse2",1,10),
                new Horse("Horse3",1,3)
        ));

        assertEquals("Horse2",hippodrome.getWinner().getName());
    }
}
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
// MISC는 "Miscellaneous"의 약자로, 일반적으로 "잡다한", "여러 가지의", 또는 "기타"라는 의미를 갖습니다.
// 주로 분류되지 않는 여러 종류의 항목들을 묶을 때 사용됩니다.
public class MiscTest {

    @BeforeAll
    public static void setup() {
        System.out.println("before all tests in the current test class");
    }

    @AfterAll
    public static void destory() {
        System.out.println("after all tests in the current test class");
    }

    @BeforeEach
    public void init() {
        System.out.println("before each @Test");
    }

    @Test
    public void testHashSetContainsNonDuplicatedValue() {

        // Given
        Integer value = Integer.valueOf(1);
        Set<Integer> set = new HashSet<>();

        // When
        set.add(value);
        set.add(value);
        set.add(value);

        // Then
        Assertions.assertEquals(1, set.size());
        Assertions.assertTrue(set.contains(value));
    }

    @Test
    public void testDummy() {
        Assertions.assertTrue(Boolean.TRUE);
    }

    @AfterEach
    public void cleanup() {
        System.out.println("after each @Test");
    }
}

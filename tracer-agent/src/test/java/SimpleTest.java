import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class SimpleTest {

    @Test
    public void test() {
        append((Object) null);
        append(1);
    }

    public void append(Object inst) {
        System.out.println(inst);
    }

}

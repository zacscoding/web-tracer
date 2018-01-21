import com.zaccoding.tracer.util.StringInvokeUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class StringInvokeUtilTest {
    @Test
    public void check() {
        assertTrue(StringInvokeUtil.isMatches("org.zaccoding", "org.zaccoding.test", StringInvokeUtil.InvokeType.startsWith, false));
        assertFalse(StringInvokeUtil.isMatches(null, "org.zaccoding.test", StringInvokeUtil.InvokeType.startsWith, true));
        assertFalse(StringInvokeUtil.isMatches( "org.zaccoding.test", null,  StringInvokeUtil.InvokeType.startsWith, false));
        assertTrue(StringInvokeUtil.isMatches("org.zaccoding.test", "test.org.zaccoding.test.app", StringInvokeUtil.InvokeType.contains, false));
    }



}



import com.google.gson.GsonBuilder;
import com.zaccoding.tracer.agent.ProxyConfigurer;
import com.zaccoding.tracer.util.StringInvokeUtil;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class YamlConfigTest {
    @Test
    public void test() throws Exception {
        ProxyConfigurer.MethodProxy m1 = new ProxyConfigurer.MethodProxy("index", StringInvokeUtil.InvokeType.contains, false);
        ProxyConfigurer.MethodProxy m2 = new ProxyConfigurer.MethodProxy("proxy", StringInvokeUtil.InvokeType.startsWith, false);
        ProxyConfigurer.ClassProxy c1 = new ProxyConfigurer.ClassProxy("com/zaccoding", StringInvokeUtil.InvokeType.startsWith, true, Arrays.asList(m1,m2));

        ProxyConfigurer config = new ProxyConfigurer();
        config.setClasses(Arrays.asList(c1));
        Yaml yaml = new Yaml();
        System.out.println(yaml.dump(config));
    }

    @Test
    public void read() throws Exception {
        String fileName = "test.yaml";
        Yaml yaml = new Yaml();
        ProxyConfigurer readConfig = (ProxyConfigurer)yaml.load(YamlConfigTest.class.getResourceAsStream(fileName));
        System.out.println(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(readConfig));
    }

}

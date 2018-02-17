import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.zaccoding.tracer.agent.ProxyConfigurer;
import org.junit.Test;

import java.io.FileReader;
import java.lang.reflect.Type;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class JsonConfigTest {

    @Test
    public void read() throws Exception {
        String fileName = "test.json";
        final Type configType = new TypeToken<ProxyConfigurer>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(JsonConfigTest.class.getResource(fileName).getFile()));
        ProxyConfigurer readConfig = gson.fromJson(reader, configType);
        System.out.println(new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create().toJson(readConfig));
    }
}

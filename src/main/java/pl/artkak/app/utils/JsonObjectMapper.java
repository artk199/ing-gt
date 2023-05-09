package pl.artkak.app.utils;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.runtime.Settings;
import io.vertx.rxjava3.core.buffer.Buffer;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class JsonObjectMapper {

    private static final DslJson<Object> json = new DslJson<>(Settings.withRuntime()
            .allowArrayFormat(true)
            .includeServiceLoader());

    @SneakyThrows
    public static <T> T deserialize(Class<T> clazz, Buffer buffer) {
        byte[] bytes = buffer.getBytes();
        return json.deserialize(clazz, bytes, bytes.length);
    }

    @SneakyThrows
    public static <T> List<T> deserializeList(Class<T> clazz, Buffer buffer) {
        byte[] bytes = buffer.getBytes();
        return json.deserializeList(clazz, bytes, bytes.length);
    }

    @SneakyThrows
    public static Buffer serialize(Object o) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        json.serialize(o, os);
        return Buffer.buffer(os.toByteArray());
    }

}

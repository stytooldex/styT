package dump.g;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by z on 2016/1/26.
 */
public class GsonType<T> {
    public Type type;

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
       // return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
		return null;
	}

    public GsonType() {
        type = getSuperclassTypeParameter(getClass());
    }

}

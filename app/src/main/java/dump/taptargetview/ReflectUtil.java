package dump.taptargetview;

import java.lang.reflect.Field;

class ReflectUtil {
    ReflectUtil() {
    }

    /**
     * Returns the value of the given private field from the source object
     **/
    static Object getPrivateField(Object source, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        final Field objectField = source.getClass().getDeclaredField(fieldName);
        objectField.setAccessible(true);
        return objectField.get(source);
    }
}

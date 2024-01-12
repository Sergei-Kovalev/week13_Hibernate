package ru.clevertec.home.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Class<?> clazz, Object field) {
        return new EntityNotFoundException("Not found " + clazz.getSimpleName() + " with uuid = " + field.toString());
    }
}

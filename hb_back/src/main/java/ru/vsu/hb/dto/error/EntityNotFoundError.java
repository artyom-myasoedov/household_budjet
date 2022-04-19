package ru.vsu.hb.dto.error;

public class EntityNotFoundError extends HBError{
    public EntityNotFoundError(String message) {
        super("not_found", message);
    }
}

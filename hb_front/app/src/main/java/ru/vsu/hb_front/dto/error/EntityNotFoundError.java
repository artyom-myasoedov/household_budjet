package ru.vsu.hb_front.dto.error;

public class EntityNotFoundError extends HBError{
    public EntityNotFoundError(String message) {
        super("not_found", message);
    }
}

package mikeogezi.daos;

import mikeogezi.exceptions.NotImplemented;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    default void update(T t, String[] params)
        throws NotImplemented {

        throw new NotImplemented();
    }

    default void delete(T t)
        throws NotImplemented {

        throw new NotImplemented();
    }
}
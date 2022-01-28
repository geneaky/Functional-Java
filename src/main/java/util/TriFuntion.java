package util;

@FunctionalInterface
public interface TriFuntion<T, U, V, R> {
  R apply(T t, U u, V v);
}

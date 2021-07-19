package com.instamojo.android.network;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public class Resource<T> {

    public static final int SUCCESS = 0;

    public static final int ERROR = 1;

    public static final int LOADING = 2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource<?> resource = (Resource<?>) o;
        return status == resource.status &&
                Objects.equals(message, resource.message) &&
                Objects.equals(data, resource.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, data, status);
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {SUCCESS, ERROR, LOADING})
    public @interface STATUS {
    }

    private String message;

    private T data;

    private @STATUS int status;

    private Resource(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(data, SUCCESS, null);
    }

    public static <T> Resource<T> error(String message, T data) {
        return new Resource<>(data, ERROR, message);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(null, LOADING, null);
    }

}

package top.atstudy.framework.core;

public class Interceptors {
    protected Interceptor head;
    protected Interceptor tail;

    public Interceptors() {
    }

    public Interceptors add(Interceptor interceptor) {
        if (this.head == null) {
            this.head = interceptor;
        }

        interceptor.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = interceptor;
        }

        this.tail = interceptor;
        return this;
    }
}

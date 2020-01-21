import java.util.Date;

/**
 * @Author zhouyang
 * @Date 2020/1/21 10:13 下午
 * @Version 1.0
 * @Description
 */
public class UnixTime {
    private final long value;

    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}

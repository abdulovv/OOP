package finance;


import lombok.Getter;
import lombok.Setter;
import users.Client;

@Getter
@Setter
public abstract class Applications {
    protected String info;
    protected long remainingSum;
    protected int duration;
    protected Client client;
    protected boolean isActive;


    public Applications(long remainingSum, int duration, String info) {
        this.remainingSum = remainingSum;
        this.info = info;
        this.duration = duration;
        isActive = false;
    }
}

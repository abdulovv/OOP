package finance;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import users.Client;

@Getter
@Setter
@ToString
public abstract class Applications {
    protected String info;
    protected long sum;
    protected long remainingSum;
    protected long commission;
    protected int duration;
    protected Client client;
    protected boolean isActive;

    public Applications(String info, long sum, int duration, int commission, Client client, boolean isActive) {
        this.info = info;
        this.sum = sum;
        this.remainingSum = sum;
        this.client = client;
        this.isActive = isActive;
    }

    public Applications(long sum, int duration, String info) {
        this.sum = sum;
        this.info = info;
        this.duration = duration;
        isActive = false;
    }

    public void decreaseDuration() {
        duration--;
    }

    public void decreaseRemainingSum(long amount) {
        remainingSum -= amount;
    }

}

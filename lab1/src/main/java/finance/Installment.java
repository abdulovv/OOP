package finance;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Installment extends Applications {
    public Installment(long sum, int duration, String info) {
        super(sum, duration, info);
    }
}

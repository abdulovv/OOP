package finance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Loan extends Applications {
    public Loan(long sum, int duration, String info) {
        super(sum, duration, info);
    }

}

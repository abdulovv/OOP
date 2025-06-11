package finance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Transaction {
    protected String info;

    public Transaction() {

    }
}

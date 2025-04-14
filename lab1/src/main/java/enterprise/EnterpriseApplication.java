package enterprise;

import lombok.Getter;
import lombok.Setter;
import users.Client;

@Getter
@Setter
public class EnterpriseApplication {
    private Client client;
    private Enterprise enterprise;
    private String info;


    public EnterpriseApplication(Client client, Enterprise enterprise) {
        this.client = client;
        this.enterprise = enterprise;
    }
}

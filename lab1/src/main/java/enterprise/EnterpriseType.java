package enterprise;

import lombok.Getter;

@Getter
public enum EnterpriseType {
    IndividualEntrepreneur("Индивидуальный предприниматель "),
    LimitedLiabilityCompany("Общество с ограниченной ответственностью"),
    JointStockCompany("Закрытое акционерное общество");

    private final String description;

    EnterpriseType(String description) {
        this.description = description;
    }

}

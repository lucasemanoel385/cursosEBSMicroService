package br.com.cursosEBS.gateway.enums;

public enum Role {

    ROLE_RENEW(1),
    ROLE_STUDENTS(2),
    ROLE_INSTRUCTOR(3),
    ROLE_ADMIN(4);

    private final int valueRole;

    Role(int valueRole) {
        this.valueRole = valueRole;
    }

    public int getValueOfRole() {
        return valueRole;

    }
}

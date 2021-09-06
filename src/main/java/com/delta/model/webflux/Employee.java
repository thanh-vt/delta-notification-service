package com.delta.model.webflux;

/**
 * @author thanhvt
 * @created 15/08/2021 - 5:50 CH
 * @project vengeance
 * @since 1.0
 **/
public class Employee {
    private String id;
    private String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

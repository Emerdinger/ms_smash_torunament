package com.emerdinger.smashtorunament.helpers.persistency;

public interface IdEntity<ID> {

    public interface Attributes {
        String ID = String.valueOf("id");
    }

    ID getId();
    void setId(ID id);

}

package com.emerdinger.smashtorunament.helpers.persistency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model<ID> {
    private ID id;
}

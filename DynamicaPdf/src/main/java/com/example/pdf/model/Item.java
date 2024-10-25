package com.example.pdf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	 private String name;
	    private String quantity;
	    private Double rate;
	    private Double amount;

}

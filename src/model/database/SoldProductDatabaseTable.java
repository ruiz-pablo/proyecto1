package model.database;

import java.util.ArrayList;
import java.util.Collection;

import model.SoldProduct;

public class SoldProductDatabaseTable extends AbstractDatabaseTable<SoldProduct>{
	
	public Collection<SoldProduct> selectByBillId(int billId) {
		ArrayList<SoldProduct> result = new ArrayList<SoldProduct>();
		
		for (SoldProduct soldProduct : data) {
			if (soldProduct.getBillId() == billId)
				result.add(soldProduct);
		}
		
		return result;
	}
}

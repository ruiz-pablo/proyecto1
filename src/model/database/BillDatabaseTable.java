package model.database;

import java.util.ArrayList;
import java.util.Collection;

import model.Bill;

public class BillDatabaseTable extends AbstractDatabaseTable<Bill> {
	
	public Collection<Bill> selectByClientId(int id) {
		ArrayList<Bill> result = new ArrayList<Bill>();
		
		for (Bill bill : data) { 
			if (bill.getClientId() == id)
				result.add(bill);
		}
		return result;
	}
}

package com.hp.inventory.audit.parser.model;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * GeneralProduct
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class GeneralProduct extends AbstractProduct {

	@Override
	public void initNewEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgradeEntityFrom(IProduct from) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

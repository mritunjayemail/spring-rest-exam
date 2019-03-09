package com.database.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.database.ExportsIPBean;
import com.database.ExportsOPBean;
import com.database.core.GDException;
import com.database.core.GDJDBCAbstractBusinessService;

@Service
public class ExportServiceImpl extends GDJDBCAbstractBusinessService implements ExportService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	public ExportsOPBean getLoginList(ExportsIPBean exportsInputValueBean) throws GDException {
		ExportsOPBean exportOutputValueBean = null;
		try {
			exportsInputValueBean.setUniqueId("1");
			exportOutputValueBean = (ExportsOPBean) getJDBCDataService("loginJDBCListDS", exportsInputValueBean);
			LOG.info("login list : {}",exportOutputValueBean.toString());
			return exportOutputValueBean;
		} catch (Throwable e) {
			throw new GDException(e);
		}
	}
}
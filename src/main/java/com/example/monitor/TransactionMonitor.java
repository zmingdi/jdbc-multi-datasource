package com.example.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.datasource.DataSourceManager;

@Aspect
@Component
@Scope("singleton")
public class TransactionMonitor {
	
	@Autowired
	DataSourceManager dxMgt;
	
	@Pointcut("execution(* com.example.service.*Service.*(..))")
	public void servicePointCut() {
		
	}
	@Around("@annotation(TestTransactional)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionTemplate txTemplate = dxMgt.getTxTemplate();
        Object[] args = joinPoint.getArgs();
        return txTemplate.execute(status->{
        	try {
        		Object result = joinPoint.proceed(args);
				return result;
			} catch (Throwable e) {
				e.printStackTrace();
				status.setRollbackOnly();
				return null;
			}
        });
    }
}

package com.liteworkflow.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class WorkflowApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(WorkflowApplication.class, args);
	}
}

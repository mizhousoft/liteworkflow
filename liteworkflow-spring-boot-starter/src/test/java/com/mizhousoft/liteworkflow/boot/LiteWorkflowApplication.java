package com.mizhousoft.liteworkflow.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class LiteWorkflowApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(LiteWorkflowApplication.class, args);
	}
}

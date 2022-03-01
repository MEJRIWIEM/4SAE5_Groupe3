package tn.esprit.spring.wecare.Configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;



import tn.esprit.spring.wecare.Entities.EmployeeList.EmlpoyeeFieldSetMapper;
import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;

@Configuration
public class BatchConfiguration {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	
	@Bean
	@StepScope
	public FlatFileItemReader<EmployeeList> personItemReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
		
		FlatFileItemReader<EmployeeList> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new PathResource(pathToFile));

		DefaultLineMapper<EmployeeList> customerLineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id","email"});

		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new EmlpoyeeFieldSetMapper());
		customerLineMapper.afterPropertiesSet();
		reader.setLineMapper(customerLineMapper);
		return reader;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public JdbcBatchItemWriter<EmployeeList> personItemWriter() {
		JdbcBatchItemWriter<EmployeeList> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO emplyeelist VALUES (:id, :email)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}
	

	@Bean
	@JobScope

	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<EmployeeList, EmployeeList>chunk(10)
				.reader(personItemReader(null))
				.writer(personItemWriter())
				.build();
	}
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
	
}



package tn.esprit.spring.wecare.Entities.EmployeeList;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmlpoyeeFieldSetMapper implements FieldSetMapper<EmployeeList> {

	

	@Override
	public EmployeeList mapFieldSet(FieldSet fieldSet) throws BindException {
		return new EmployeeList(fieldSet.readLong("id"),
				fieldSet.readString("email"));
	
	}
	}
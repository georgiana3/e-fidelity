package ro.efidelity.model.domain.efidelity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Table(name = "ecommerce:simple_entity")
public class TestEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AWB_ID")
	@SequenceGenerator(allocationSize = 20, name = "SEQ_AWB_ID", sequenceName = "ecommerce:seq_awb_id")
	Long id;

	String attr;

	public TestEntity() {
		attr = RandomStringUtils.randomAlphabetic(8);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}

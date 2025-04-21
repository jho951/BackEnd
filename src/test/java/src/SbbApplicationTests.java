package src;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import src.domain.sample.entity.Sample;
import src.domain.sample.repository.SampleRepository;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private SampleRepository sampleRepository;

	@Test
	void testHello(){
		Sample sample = new Sample();
		Sample.builder()
			.name("이장호")
			.build();
		sampleRepository.save(sample);
	}

	@Test
	void testFindSize() {
		List<Sample> sampleList = sampleRepository.findAll();
		assertEquals(1, sampleList.size());
	}
}

package ru.newdv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import ru.newdv.entity.source.SourceTable;
import ru.newdv.entity.targett.TargetTable;
import ru.newdv.repo.source.SourceRepository;
import ru.newdv.repo.targett.TargetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired DataInitializer initializer;

	@Autowired
	private SourceRepository sourceRepository;

	@Autowired
	private TargetRepository targetRepository;

	@Test
	public void runMany() {
		final int testSize = 10;
		String l = "http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775-";
		String f = "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры %s прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает %s издание Hurriyet со ссылкой на источники в компаниях.";
		List<SourceTable> sourceList = new ArrayList<SourceTable>();
		IntStream.range(0, testSize).forEach(j -> {
			IntStream.range(0, 1000).forEach(i -> {
				StringBuilder lsb = new StringBuilder(l).append(j).append('-').append(i).append('-');
				String s = String.format(f, new StringBuilder(lsb).append('a').toString(), new StringBuilder(lsb).append('b').toString());
				SourceTable st = new SourceTable();
				st.setSourceID((long) ((j * 100000) + i));
				st.setSourceText(s);
				sourceList.add(st);
			});
		});

		// use batch
		int BATCH = 10000;
		IntStream.range(0, (sourceList.size()+BATCH-1)/BATCH)
				.mapToObj(i -> sourceList.subList(i*BATCH, Math.min(sourceList.size(), (i+1)*BATCH)))
				.forEach(batch -> {
					sourceRepository.saveAll(batch);
					sourceRepository.flush();
				});
		assertEquals("Wrong source table size", testSize * 1000, sourceRepository.count());

		// Link with 0-0-b already exist
		TargetTable t = new TargetTable();
		t.setTargetLink(l + "0-0-b");
		targetRepository.save(t);
		targetRepository.flush();

		StopWatch sw = new StopWatch();
		sw.start();
		initializer.run();
		sw.stop();
		assertTrue("Bad execution time = " + sw.getLastTaskTimeMillis() + "ms", sw.getLastTaskTimeMillis() < 60000l);

		assertEquals("Wrong target table size", testSize * 1000 * 2, targetRepository.count());
	}

}

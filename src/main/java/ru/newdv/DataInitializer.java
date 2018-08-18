package ru.newdv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.newdv.entity.source.SourceTable;
import ru.newdv.entity.targett.TargetTable;
import ru.newdv.repo.source.SourceRepository;
import ru.newdv.repo.targett.TargetRepository;
import ru.newdv.util.StringLinkExtractor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataInitializer {

    private final Logger loger = LogManager.getLogger(DataInitializer.class.getName());

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private StringLinkExtractor stringLinkExtractor;

    public void run() {
        List<SourceTable> sourceList = sourceRepository.findAll();
        loger.debug("Source table has " + sourceList.size() + " rows");
        if (!CollectionUtils.isEmpty(sourceList)) {

            Set<String> links = new HashSet<String>();
            for (SourceTable s: sourceList) {
                links.addAll(stringLinkExtractor.parse(s.getSourceText()));
            }

            List<TargetTable> targetList = links.stream().map(l -> {TargetTable t = new TargetTable(); t.setTargetLink(l); return t;}).collect(Collectors.toList());
            // Use batch to save
            int BATCH = 10000;
            IntStream.range(0, (targetList.size()+BATCH-1)/BATCH)
                    .mapToObj(i -> targetList.subList(i*BATCH, Math.min(targetList.size(), (i+1)*BATCH)))
                    .forEach(batch -> {
                        targetRepository.saveAll(batch);
                        targetRepository.flush();
                    });

        } else {
            loger.debug("Source table is empty!");
        }

    }
}

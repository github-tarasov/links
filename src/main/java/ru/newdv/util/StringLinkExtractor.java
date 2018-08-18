package ru.newdv.util;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class StringLinkExtractor {

    LinkExtractor linkExtractor;

    public StringLinkExtractor() {
        linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
                .build();
    }

    public List<String> parse(String s) {
        List<String> links = new ArrayList<String>();

        if (!StringUtils.isEmpty(s)) {
            linkExtractor.extractLinks(s).forEach(ls -> links.add(s.substring(ls.getBeginIndex(), ls.getEndIndex())));
        }

        return links;
    }
}

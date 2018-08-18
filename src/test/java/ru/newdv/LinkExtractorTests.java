package ru.newdv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.newdv.util.StringLinkExtractor;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkExtractorTests {

    @Autowired
    StringLinkExtractor stringLinkExtractor;

    @Test
    public void zero() {
        String s = "Накануне президент Турции Реджеп Тайип Эрдоган заявил о планах ответа на «экономическую войну», которую, по его словам, развязали Соединенные Штаты. В их числе он назвал и бойкот американской электроники. «Мы будем производить и продавать продукты, которые будут лучше тех, что мы покупаем за иностранную валюту. И мы будем бойкотировать американскую электронику», — заявил глава государства.";
        List<String> list = stringLinkExtractor.parse(s);

        Assert.assertEquals("List of links size must be 0", 0, list.size());
    }

    @Test
    public void one() {
        String s = "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.";
        List<String> list = stringLinkExtractor.parse(s);

        Assert.assertEquals("List of links size must be 1", 1, list.size());
        Assert.assertEquals("Bad link", "http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775", list.get(0));
    }

    @Test
    public void many() {
        String s = "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.\n" +
                        "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.\n" +
                        "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.\n" +
                        "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.\n" +
                        "После заявления президента Турции Реджепа Тайипа Эрдогана о бойкоте американской электроники крупнейшие турецкие ретейлеры прекратил прием заказов на смартфоны iPhone и другую американскую электронику. Об этом сообщает http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775 издание Hurriyet со ссылкой на источники в компаниях.";
        List<String> list = stringLinkExtractor.parse(s);

        Assert.assertEquals("List of links size must be 5", 5, list.size());
        list.forEach(l -> Assert.assertEquals("Bad link", "http://www.hurriyet.com.tr/ekonomi/erdogan-cagri-yapmisti-tek-tek-iptal-ettiler-40929775", l));
    }

}

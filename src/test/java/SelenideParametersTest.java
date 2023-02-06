import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class SelenideParametersTest {

    @BeforeAll
    public static void openAndPreparePage(){
        open("https://www.rzd.ru");
        Configuration.browserSize="1920x1080";
    }

    static Stream<Arguments> selenideLocaleDataProvider() {
            return Stream.of(
                    Arguments.of(Language.Eng, List.of(
                            "Passengers",
                            "Freight",
                            "The Company",
                            "Investor Relations",
                            "Contacts"
                    )),
                    Arguments.of(Language.Rus, List.of(
                                    "Пассажирам",
                                    "Грузовые перевозки",
                                    "Компания",
                                    "Работа в РЖД",
                                    "Контакты"
                            ))
            );
        }

        @MethodSource("selenideLocaleDataProvider")
        @ParameterizedTest(name = "Для локали {0} отображаются кнопки меню {1}")
        @Tag("BLOCKER")
        void siteShouldContainAllOfButtonsForGivenLocale(
                Language language,
                List<String> buttons
        ) {
            $(".icon-locale").click();
            $$x("//div[@class='locale-switch']//a").find(text(language.name())).click();
            $$x("//nav[@class='header_site']//li")
                    .filter(visible)
                    .shouldHave(texts(buttons));
        }


    @CsvFileSource(resources = "/testData.csv")
    @ParameterizedTest(name = "Text {1} should be in the query {0}")

    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void productSiteShouldBePresentInResultsOfSearchByNameQuery(
            String productName,
            String productUrl
    ) {
        $x("//li[@class='icon icon-search magn_glass j-search-open']").click();
        $("[name=search_pattern]").setValue(productName).pressEnter();
        $x("//div[@class='j-search_container']").shouldHave(text(productUrl));
    }


    @ValueSource(
            strings = {"животные", "Туры для школьников"}
    )
    @ParameterizedTest(name = "Address {1} should be in the query {0}")

    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void searchResultsCountTest(String productName) {
        $x("//li[@class='icon icon-search magn_glass j-search-open']").click();
        $("[name=search_pattern]").setValue(productName).pressEnter();
        $x("//div[@class='pager ']").scrollIntoView(true);
        $$x("//li[@class='search-results__item']").
                shouldHave(CollectionCondition.sizeGreaterThan(2));

    }


}

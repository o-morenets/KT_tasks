package polymorphism;

import exception.ExerciseNotCompletedException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Use Static Polymorphism and implement methods that will provide amount of words in 'text' parameter. <br><br>
 * <p>
 * You can run {@link polymorphism.FunctionalStaticPolymorphismTest} to check if you have done it correctly. <br><br>
 *
 * <b>When you implement all methods, revise the implementations and perform deduplication in the code.</b>
 */
@UtilityClass
public class FunctionalStaticPolymorphism {

    /**
     * Provides amount of words in 'text' parameter. <br><br>
     * <pre>
     *     text = "My Money Mom Mine Model my mom"
     *     result = "my=2, money=1, mom=2, mine=1, model=1"
     * </pre>
     * <b>Hint: to collect words you can take a look at the {@link Collectors#summingInt(ToIntFunction)}</b>
     *
     * @return map of words and their amount
     */
    public static Map<String, Integer> wordsAmount(String text) {
        return wordsAmount(text, s -> true);
    }

    /**
     * <b>Use Static Polymorphism</b><br>
     * Provides amount of words in 'text' parameter with 'minLength' and 'maxLength' parameters. <br>
     * <pre>
     *     text = "My Money Mom Mine Model my mom"
     *     minLength = 3
     *     maxLength = 4
     *     result = "mom=2, mine=1"
     * </pre>
     *
     * @param text      text to analyze
     * @param minLength minimum length of word
     * @param maxLength maximum length of word
     * @return map of words and their amount
     */
    public static Map<String, Integer> wordsAmount(String text, int b, int c) {
        return wordsAmount(
                text,
                s -> s.length() >= b,
                s -> s.length() <= c
        );
    }

    /**
     * <b>Use Static Polymorphism</b><br>
     * Provides amount of words in 'text' parameter with minLength, maxLength and startWith parameters. <br>
     * <pre>
     *     text = "My Money Mom Mine Model my mom"
     *     minLength = 3
     *     maxLength = 4
     *     startWith = "mi"
     *     result = "mine=1"
     * </pre>
     *
     * @param text      text to analyze
     * @param minLength minimum length of word
     * @param maxLength maximum length of word
     * @param startWith word should start with this letter
     * @return map of words and their amount
     */
    public static Map<String, Integer> wordsAmount(String text, int b, int c, String d) {
        return wordsAmount(
                text,
                s -> s.length() >= (int) b,
                s -> s.length() <= (int) c,
                d != null ? (Predicate<String>) s -> s.startsWith(d.toLowerCase()) : null
        );
    }

    /**
     * <b>Use Static Polymorphism</b><br>
     * Provides amount of words in 'text' parameter by Predicates. <br>
     * <pre>
     *     text = "My Money Mom Mine Model my mom"
     *     predicates = [s -> s.startsWith("m"), s -> s.length() > 3]
     *     result = "money=1, mine=1, model=1"
     * </pre>
     *
     * @param text       text to analyze
     * @param predicates predicates to filter words
     * @return map of words and their amount
     */
    public static Map<String, Integer> wordsAmount(String text, Predicate<String>... predicates) {
        Predicate<String> stringPredicate = s -> StringUtils.isNotEmpty(s);

        if (Objects.nonNull(predicates)) {
            for (Predicate<String> predicate : predicates) {
                if (Objects.nonNull(predicate)) {
                    stringPredicate = stringPredicate.and(predicate);
                }
            }
        }

        return Arrays.stream(text.split(" "))
                .map(String::toLowerCase)
                .filter(stringPredicate)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(s -> 1)));
    }

}

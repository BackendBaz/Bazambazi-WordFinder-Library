package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FinderTest {

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the input is null")
    public void getLetters_WhenInputIsNull_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() -> new Finder(null).getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Input is null or blank");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the input is blank or empty")
    public void getLetters_WhenInputIsBlank_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() -> new Finder("").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Input is null or blank");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the letters array's length is invalid")
    public void getLetters_WhenLettersArrayLengthIsInvalid_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the letters array is invalid")
    public void getLetters_WhenLettersArrayIsInvalid_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا ب ف ظ a ل ذ ل ب س ش ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the Persian uppercase 'a' letter exists in the letters array")
    public void getLetters_WhenPersianUppercaseAExistsInTheLettersArray_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا ب ف ظ آ ل ذ ل ب س ش ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Returns 2D array of letters")
    public void getLetters_WhenArrayOfLettersIsValid_ShouldReturns2DArrayOfLetters() {
        assertThat(new Finder("س ع ق ف ا ب ف ظ ه ل ذ ل ب س ش ا").getLetters())
                .isEqualTo(new String[][]{
                        {"س", "ع", "ق", "ف"},
                        {"ا", "ب", "ف", "ظ"},
                        {"ه", "ل", "ذ", "ل"},
                        {"ب", "س", "ش", "ا"}
                });
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns false if word parameter has less than 2 characters")
    public void exists_WhenWordHasLessThan2Characters_ShouldReturnsFalse() {
        Finder finder = new Finder("س ع ق ف ا ب ف ظ ه ل ذ ل ب س ش ا");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "ب")).isFalse();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns false if word parameter has greater than grid cells")
    public void exists_WhenWordHasGreaterThanGridCells_ShouldReturnsFalse() {
        Finder finder = new Finder("س ع ق ف ا ب ف ظ ه ل ذ ل ب س ش ا");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "بنیتیتنبالمقتلمنتل")).isFalse();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns false if word parameter has an invalid character")
    public void exists_WhenWordHasInvalidCharacter_ShouldReturnsFalse() {
        Finder finder = new Finder("س ع ق ف ا ب ف ظ ه ل ذ ل ب س ش ا");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "سaب")).isFalse();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns true if word parameter exists in the grid - 1")
    public void exists_WhenWordParamExistsInTheGrid_N1_ShouldReturnsTrue() {
        Finder finder = new Finder("س ا ع ت ا ب ف ظ ه ل ذ ل ب س ش ا");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "ساعت")).isTrue();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns true if word parameter exists in the grid - 2")
    public void exists_WhenWordParamExistsInTheGrid_N2_ShouldReturnsTrue() {
        Finder finder = new Finder("س م ع ت ا ت ش ن ی ن ا ش ب س ش ا");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "اتشنشانی")).isTrue();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns true if word parameter exists in the grid - 3")
    public void exists_WhenWordParamExistsInTheGrid_N3_ShouldReturnsTrue() {
        Finder finder = new Finder("س م ع ت ا ت ش ن ی ن ا ف ش ر ب ت");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "شربت")).isTrue();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns true if word parameter exists in the grid - 4")
    public void exists_WhenWordParamExistsInTheGrid_N4_ShouldReturnsTrue() {
        Finder finder = new Finder("ع ف ق ر س م ش ه ظ ط ا ی ث ه خ د");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "عمادی")).isTrue();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns true if word parameter exists in the grid - 5")
    public void exists_WhenWordParamExistsInTheGrid_N5_ShouldReturnsTrue() {
        Finder finder = new Finder("ع م ا د س م ش ی ظ ط ا ی ث ه خ د");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "عمادی")).isTrue();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns false if word parameter not exists in the grid - 1")
    public void exists_WhenWordParamNotExistsInTheGrid_N1_ShouldReturnsFalse() {
        Finder finder = new Finder("س م ع ت ا ت ش ن ی ن ا ف ش ر ب ت");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "شربتی")).isFalse();
    }

    @Test
    @Tag("Finder:exists")
    @DisplayName("Returns false if word parameter not exists in the grid - 2")
    public void exists_WhenWordParamNotExistsInTheGrid_N2_ShouldReturnsFalse() {
        Finder finder = new Finder("س پ س س س س س س د س س س س س س ر");
        String[][] grid = finder.getLetters();
        assertThat(finder.exists(grid, "پدر")).isFalse();
    }

    @Test
    @Tag("Finder:IsPersianWord")
    @DisplayName("Returns false if word parameter is null")
    public void IsPersianWord_WhenWordParamIsNull_ShouldReturnsFalse() {
        assertThat(Finder.IsPersianWord(null)).isFalse();
    }

    @Test
    @Tag("Finder:IsPersianWord")
    @DisplayName("Returns false if word parameter is blank or empty")
    public void IsPersianWord_WhenWordParamIsBlank_ShouldReturnsFalse() {
        assertThat(Finder.IsPersianWord("")).isFalse();
    }

    @Test
    @Tag("Finder:IsPersianWord")
    @DisplayName("Returns false if word parameter has a non-Persian character")
    public void IsPersianWord_WhenWordParamHasNonPersianChar_ShouldReturnsFalse() {
        assertThat(Finder.IsPersianWord("عmادی")).isFalse();
    }

    @Test
    @Tag("Finder:IsPersianWord")
    @DisplayName("Returns false if word parameter has a whitespace character")
    public void IsPersianWord_WhenWordParamHasWhitespaceChar_ShouldReturnsFalse() {
        assertThat(Finder.IsPersianWord("ع ادی")).isFalse();
    }

    @Test
    @Tag("Finder:IsPersianWord")
    @DisplayName("Returns true if word parameter is a Persian word")
    public void IsPersianWord_WhenWordParamIsPersianWord_ShouldReturnsTrue() {
        assertThat(Finder.IsPersianWord("عمادی")).isTrue();
    }

}

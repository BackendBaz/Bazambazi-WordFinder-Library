module io.github.backendbaz.bazambazi.wordfinder {

    // وابستگی‌های اصلی
    requires java.base;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.annotation;

    // پکیج‌های عمومی
    exports io.github.backendbaz.core;
    exports io.github.backendbaz.exceptions;
    exports io.github.backendbaz.models;

    // باز کردن پکیج‌ها برای Jackson (برای استفاده از reflection در سریالایزیشن)
    opens io.github.backendbaz.dto to com.fasterxml.jackson.databind;
}

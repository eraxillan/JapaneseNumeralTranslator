# Kotlin/Native + GNU gettext demo

This program demonstrates how to use **GNU gettext** package for Kotlin/Native program internationalization and localization.
Supported locales: English, Russian.
Supported platform: Linux only for now, but porting to Windows/macOS is possible.
What program do: translate user-specified natural number to cardinal numeral in Japanese language. Input can be specified as command line argument or through **stdin**.
![GNOME Terminal screenshot](https://i.ibb.co/jVRMhVh/JNT-Screenshot-Linux.png)

# Translations scripts

To create or update translations you can use two scripts in *scripts* subdirectory:

    ./create_localization.sh
    ./update_localization.sh

Localized strings stored in *po/en_US/jnt.po* and *po/ru_RU/jnt.po* files.
Those files can be edit with any text editor like Sublime Text or with some specialized po-file editor.
When edit is complete mo-files should be generated and properly deployed (done by update script).


## Build

Just open the project in IntelliJ IDEA, either community or paid edition.
Or use Gradle *build* task:

    ./gradlew build

## Run program

Locale can be specified using *LANG* environment variable.
Program can take natural number as command line argument:

    LANG=en_US.UTF-8 build/bin/native/debugExecutable/JapaneseNumeralTranslator.kexe 120
    LANG=ru_RU.UTF-8 build/bin/native/debugExecutable/JapaneseNumeralTranslator.kexe 120

Or it will be requested from **stdin** if no argument was specified:

    LANG=ru_RU.UTF-8 build/bin/native/debugExecutable/JapaneseNumeralTranslator.kexe
    LANG=en_US.UTF-8 build/bin/native/debugExecutable/JapaneseNumeralTranslator.kexe

## Run unit tests

Use Gradle *check* task:

    ./gradlew check


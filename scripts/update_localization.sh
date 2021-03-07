#!/usr/bin/env bash
set -x

if [ ! -f /usr/bin/xgettext ]; then
    echo "gettext package not installed!"
    exit 1
fi

# Find all Kotlin source files names and save them to temponary file
find src/ -name '*.kt' > KT_FILES

# Extract all tr() wrapped strings to po/jnt.pot file
# --omit-header -s
xgettext --keyword=tr --language=java \
    --add-comments --sort-output \
    --copyright-holder='Alexander Kamyshnikov <axill777@gmail.com>' \
    --package-name='Japanese numeral translator' \
    --package-version='1.0' \
    --msgid-bugs-address='axill777@gmail.com' \
    -o po/jnt.pot --files-from=KT_FILES

# Remove temponary file
rm KT_FILES

# NOTE: xgettext have no argument to specify encoding
sed -i 's/charset=CHARSET/charset=UTF-8/g' po/jnt.pot

# Remove POT-Creation-Date line to avoid fake file change
sed -i '/POT-Creation-Date/d' po/jnt.pot

# Update locale sources
msgmerge --update po/en_US/jnt.po po/jnt.pot
msgmerge --update po/ru_RU/jnt.po po/jnt.pot

# Generate locale binary files
msgfmt --output-file=po/en_US/jnt.mo po/en_US/jnt.po
msgfmt --output-file=po/ru_RU/jnt.mo po/ru_RU/jnt.po

# Debug build: copy locale binary file to executable directory
# English
if [ ! -d build/bin/native/debugExecutable/en_US/LC_MESSAGES ]; then
    mkdir -p build/bin/native/debugExecutable/en_US/LC_MESSAGES
fi
if [ ! -d build/bin/native/releaseExecutable/en_US/LC_MESSAGES ]; then
    mkdir -p build/bin/native/releaseExecutable/en_US/LC_MESSAGES
fi
if [ ! -d build/bin/native/debugTest/en_US/LC_MESSAGES ]; then
    mkdir -p build/bin/native/debugTest/en_US/LC_MESSAGES
fi
if [ ! -d build/bin/native/releaseTest/en_US/LC_MESSAGES ]; then
    mkdir -p build/bin/native/releaseTest/en_US/LC_MESSAGES
fi
# Russian
if [ ! -d build/bin/native/debugExecutable/ru_RU/LC_MESSAGES ]; then
    mkdir -p build/bin/native/debugExecutable/ru_RU/LC_MESSAGES
fi
if [ ! -d build/bin/native/releaseExecutable/ru_RU/LC_MESSAGES ]; then
    mkdir -p build/bin/native/releaseExecutable/ru_RU/LC_MESSAGES
fi
if [ ! -d build/bin/native/debugTest/ru_RU/LC_MESSAGES ]; then
    mkdir -p build/bin/native/debugTest/ru_RU/LC_MESSAGES
fi
if [ ! -d build/bin/native/releaseTest/ru_RU/LC_MESSAGES ]; then
    mkdir -p build/bin/native/releaseTest/ru_RU/LC_MESSAGES
fi
#
cp -v po/en_US/jnt.mo build/bin/native/debugExecutable/en_US/LC_MESSAGES
cp -v po/en_US/jnt.mo build/bin/native/releaseExecutable/en_US/LC_MESSAGES
cp -v po/en_US/jnt.mo build/bin/native/debugTest/en_US/LC_MESSAGES
cp -v po/en_US/jnt.mo build/bin/native/releaseTest/en_US/LC_MESSAGES
#
cp -v po/ru_RU/jnt.mo build/bin/native/debugExecutable/ru_RU/LC_MESSAGES
cp -v po/ru_RU/jnt.mo build/bin/native/releaseExecutable/ru_RU/LC_MESSAGES
cp -v po/ru_RU/jnt.mo build/bin/native/debugTest/ru_RU/LC_MESSAGES
cp -v po/ru_RU/jnt.mo build/bin/native/releaseTest/ru_RU/LC_MESSAGES

exit 0

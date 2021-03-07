#!/usr/bin/env bash
set -x

if [ ! -f /usr/bin/xgettext ]; then
    echo "gettext package not installed!"
    exit 1
fi

# Create po/ subdirectories if not exists
if [ ! -d po/en_US ]; then
    mkdir -p po/en_US
fi
if [ ! -d po/ru_RU ]; then
    mkdir -p po/ru_RU
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

# Generate locale sources
# NOTE: --no-translator option is a workaround to supress email input request
msginit --no-translator --input=po/jnt.pot --locale=en_US.UTF-8 --output po/en_US/jnt.po
msginit --no-translator --input=po/jnt.pot --locale=ru_RU.UTF-8 --output po/ru_RU/jnt.po

# Generate locale binary files
msgfmt --output-file=po/en_US/jnt.mo po/en_US/jnt.po
msgfmt --output-file=po/ru_RU/jnt.mo po/ru_RU/jnt.po

exit 0

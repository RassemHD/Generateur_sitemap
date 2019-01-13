#!/bin/sh

sed -i 's/<script src="script.js">\s*<\/script>/<script src="jquery-1.11.1.min.js"><\/script>\n    <script src="jquery.mobile.custom.min.js"><\/script>\n    <script src="script.js"><\/script>/' *.html

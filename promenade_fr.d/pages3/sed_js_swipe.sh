#!/bin/sh

sed -i 's/<script src="..\/pages1\/script.js">\s*<\/script>/<script src="..\/pages1\/jquery-1.11.1.min.js"><\/script>\n    <script src="..\/pages1\/jquery.mobile.custom.min.js"><\/script>\n    <script src="..\/pages1\/script.js"><\/script>/' *.html
sed -i 's/<script src="script.js">\s*<\/script>/<script src="..\/pages1\/jquery-1.11.1.min.js"><\/script>\n    <script src="..\/pages1\/jquery.mobile.custom.min.js"><\/script>\n    <script src="..\/pages1\/script.js"><\/script>/' *.html

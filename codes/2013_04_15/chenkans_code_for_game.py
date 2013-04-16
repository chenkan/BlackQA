# -*- coding: UTF-8 -*-
import urllib
import re


def getWebPageContent(url):
    f = urllib.urlopen(url)
    data = f.read()
    f.close()
    return data

url = 'https://github.com/diamondfang/test/wiki'
content = getWebPageContent(url)

statements = 0
missing = 0

# 注意空格匹配
# 注意贪婪模式
pattern = '/opt/stack/nova.+?\s+(\d+)\s+(\d+)\s+\d+\s+\d+%'
for match in re.findall(pattern, content):
    statements += int(match[0])
    missing += int(match[1])

print "statements: " + str(statements)
print "missing: " + str(missing)
print "percent: " + str(100 * (statements - missing) / statements) + "%"

# Notes
# Python2.x与3.x差别很大

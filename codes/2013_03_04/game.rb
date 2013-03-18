=begin
游戏规则
phase     task
1         获取一个指定URL（www.163.com）的页面内容（须处理好中文字符）
2         提取页面上所有形如'xxx.163.com'字符串（不包括'xxx.yyy.163.com'）
3         对这些字符串进行去重、排序、格式化输出
4         提交源码（不包括依赖的库文件）及一份格式化输出至Github（https://github.com/chenkan/BlackQA）

0 - 3g.163.com
1 - adgeo.163.com
2 - analytics.163.com
3 - auto.163.com
4 - baby.163.com
5 - baojian.163.com
6 - baoxian.163.com
7 - bbs.163.com
8 - biz.163.com
9 - blog.163.com
10 - book.163.com
11 - caipiao.163.com
12 - cimg.163.com
13 - comment.163.com
14 - corp.163.com
15 - d.163.com
16 - data.163.com
17 - daxue.163.com
18 - digi.163.com
19 - discovery.163.com
20 - dt2.163.com
21 - ecard.163.com
22 - edu.163.com
23 - email.163.com
24 - ent.163.com
25 - epay.163.com
26 - fashion.163.com
27 - fax.163.com
28 - ff.163.com
29 - fm.163.com
30 - focus.163.com
31 - fund.163.com
32 - fushi.163.com
33 - g.163.com
34 - game.163.com
35 - gongyi.163.com
36 - gov.163.com
37 - hea.163.com
38 - help.163.com
39 - home.163.com
40 - house.163.com
41 - huodong.163.com
42 - istyle.163.com
43 - kids.163.com
44 - lady.163.com
45 - love.163.com
46 - m.163.com
47 - mail.163.com
48 - mall.163.com
49 - mobile.163.com
50 - money.163.com
51 - news.163.com
52 - nie.163.com
53 - open.163.com
54 - pay.163.com
55 - photo.163.com
56 - piao.163.com
57 - pk.163.com
58 - popo.163.com
59 - pp.163.com
60 - pr.163.com
61 - qiye.163.com
62 - qn.163.com
63 - quan.163.com
64 - reg.163.com
65 - s.163.com
66 - shop.163.com
67 - sitemap.163.com
68 - sports.163.com
69 - survey2.163.com
70 - t.163.com
71 - tag.163.com
72 - tech.163.com
73 - temp.163.com
74 - tie.163.com
75 - travel.163.com
76 - tuan.163.com
77 - tx3.163.com
78 - uu.163.com
79 - v.163.com
80 - view.163.com
81 - vip.163.com
82 - vipmail.163.com
83 - w.163.com
84 - war.163.com
85 - wh.163.com
86 - www.163.com
87 - xy2.163.com
88 - xy3.163.com
89 - xyq.163.com
90 - yc.163.com
91 - yuedu.163.com
92 - yuehui.163.com
93 - yxp.163.com
94 - zh.163.com

=end











require 'open-uri'
require 'iconv'

conv = Iconv.new("UTF-8", "GBK")
open('http://www.163.com') do |f|
  content = conv.iconv f.read
  domains = content.scan(/[0-9a-z]+\.163\.com/).uniq!.sort!
  domains.each_index {|x| puts "#{x} - #{domains[x]}"}
end


=begin
Coding Game - 汉语词汇提取
phase       task
1           读入一个文本，进行粗加工（剔除所有非汉字）
2           统计单字节汉字的出现概率（出现次数/文本字符数）使用Hash作为字典
3           统计双字节汉字的出现概率（出现次数/文本字符数）使用Hash作为词典
4           统计双字节汉字的凝聚度，并排序及格式化输出
5           根据结果，讨论算法可改进点

参考文献
http://www.matrix67.com/blog/archives/5044
<<数学之美>>
=end


# Phase 1
text_str = File.new("text.txt").read
text_array = text_str.split('').select { |p| p=~ /[\u4e00-\u9fa5]/ }
#puts text_array


# Phase 2
single_char_dict = {}
text_array.each do |x|
  if single_char_dict[x] == nil
    single_char_dict[x] = 1
  else
    single_char_dict[x] += 1
  end
end
single_char_dict.each_key {|key| single_char_dict[key] = single_char_dict[key].to_f/text_array.size}
#puts single_char_dict


# Phase 3
double_char_dict = {}
for i in 1..(text_array.size-1)
  if double_char_dict[text_array[i -1] + text_array[i]]  == nil
    double_char_dict[text_array[i - 1] + text_array[i]] = 1
  else
    double_char_dict[text_array[i-1] + text_array[i]] += 1
  end
end
double_char_dict.each_key {|key| double_char_dict[key] = double_char_dict[key].to_f/text_array.size}
#puts double_char_dict


# Phase 4
double_char_dict.each_key { |key| double_char_dict[key] = double_char_dict[key]/single_char_dict[key.split('')[0]]/single_char_dict[key.split('')[1]] }
double_char_dict = double_char_dict.sort { |a, b| a[1] <=>b[1] }
puts double_char_dict
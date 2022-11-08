# 工程简介

这用redis简单的实现了一个分布式锁

1.利用redis setnx的排他性来创建锁
2.给锁添加过期时间，防止死锁
3.防止锁的误删，利用lua脚本将查询、删除操作结合，保证原子性

通过jmeter压测:rocket:

待解决问题：
1.可以重入锁
2.锁自动续期
# 延伸阅读


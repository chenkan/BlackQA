from threading import Thread
from Queue import Queue
import subprocess
# import os

num_threads = 250
queue = Queue()

on = 0
off = 0
on_ip = ''


def ping(i, queue):
    print "Thread " + str(i) + " start"
    global on
    global off
    global on_ip
    while True:
        ip = queue.get()
        ret = subprocess.call("ping -n 1 -w 5000 %s " % ip)
        # ret = subprocess.Popen("ping -n 1 -w 5000 %s " % ip)
        # ret = os.system("ping -n 1 -w 5000 %s " % ip)
        if ret == 0:
            print "%s: is alive" % ip
            on += 1
            on_ip += ip
            on_ip += "\n"
        else:
            print "%s is down" % ip
            off += 1
        queue.task_done()
    print "Thread " + str(i) + " end"

for i in range(num_threads):
    worker = Thread(target=ping, args=(i, queue))
    worker.setDaemon(True)
    worker.start()


ip_prefix = "192.168.176."
for i in range(1, 255):
    ip = ip_prefix + str(i)
    queue.put(ip)


print "-- Start --"
queue.join()
print on_ip
print on
print off
print "-- Done --"

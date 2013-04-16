import httplib
import re
import logging
import sys
 

if __name__ == '__main__':
    logging.basicConfig(format='%(asctime)-15s %(levelname)s %(message)s', level=logging.DEBUG)
    
    logging.info('connecting to server github.com')
    conn = httplib.HTTPSConnection('github.com')
    conn.connect()
    logging.info('server github.com connected')
    
    logging.info('getting coverage data')
    conn.request('GET', '/diamondfang/test/wiki', '', {})
    resp = conn.getresponse()
    if resp.status != 200:
        logging.error('getting coverage data FAILED: status %d, reason %s' % (resp.status, resp.reason))
        conn.close()
        sys.exit(1)
    
    data = resp.read()
    conn.close()
    logging.debug('the coverage data: %s' % data)
    logging.info('coverage data got')
    
    c_re = re.compile(r'/opt/stack/nova.+?\s+(\d+)\s+(\d+)\s+\d+\s+\d+%')
    nova_data = c_re.findall(data)
	logging.debug('the nova data: %s' % nova_data)
	
    total_line = sum(int(x[0]) for x in nova_data)
    uncovered_line = sum(int(x[1]) for x in nova_data)
    
    logging.info('Total  %d  %d  %d%%' % (total_line, uncovered_line,
                                          (total_line-uncovered_line)*100/total_line))


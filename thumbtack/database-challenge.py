#!/usr/bin/env python

# Thumbtack challenge solution : Simple Database
# Michael Shao
# Modified: 02/08/2015
# Version: 0.0.0.0

import sys

class Database(object):
	# Constructor
	def __init__(self):
		super(Database, self).__init__();
		
		self.counter = {}
		self.values = {}
		self.unset = []
		
	# sets variable name <key> to <value>
	# REMEMBER: Keys / Values will not have spaces.
	def set(self, key, value):
		# Check to see if there exists a current counter value
		curval = self.get_curval(key)
		
		if curval:
			self.minus_one(curval)
		
		# Assign a new value
		self.values[key] = value;
			
		# Increment counter (since it exists)
		if value in self.counter.keys():
			self.counter[value] += 1
		else:
			# Add counter, if it doesn't exist
			self.counter[value] = 1
		
	# retrieves and prints key stored, or "NULL" otherwise	
	def get(self, key):
		if self.get_curval(key) != None:
			print self.get_curval(key)
		else:
			print "NULL"
	
	# unset (dissociate) key from value
	def unset(self, key):
		curval = self.get_curval(key)
		
		# First things first: Pop the key off of the values set.
		if key in self.values:
			self.values.pop(key);
		
		# Add the current key to the unset stack
		self.unset.append(key);
		
		self.minus_one(curval)
		
	# Print out the counter of vars that have val. If none exist, print 0.
	def numequalto(self, value):
		print self.get_curcount(value);

	# Return the current count of the variables equalling "value"
	def get_curcount(self, value):
		# If we found it, return (simple).
		if value in self.counter.keys():
			return self.counter[value];
		# otherwise, counter is 0 (return).
		return 0

	# Increases the current counter value by one
	def plus_one(self, value):
		self.counter[value] = self.get_curcount(value) + 1;
		
	# Decreases the current counter value by one
	def minus_one(self, value):
		self.counter[value] = self.get_curcount(value) - 1;
				
	# Return the corresponding key to value associated.
	def get_curval(self, key):
		# If we unset it, we can't return it.
		if key in self.unset:
			return None
		# If it exists in our keys, return it.
		if key in self.values.keys():
			return self.values[key]
		# If we don't have that value, return None.
		return None

if __name__ == "__main__":
	data = Database()
	parse_line = sys.stdin.readline().strip()
	while parse_line != 'END':
		# Split arguments by single whitespace character (' ')
		args = parse_line.split(' ')
		# Debugging purposes ( comment out before submitting )
		#print args
		legalCmds = ['BEGIN', 'SET', 'GET', 'UNSET', 'NUMEQUALTO']
		if args[0] in legalCmds:
			f = getattr(Database, args[0].lower());
			f(data,*args[1:])
		else:
			print "Invalid command / arguments: %s" % args
			
		parse_line = sys.stdin.readline().strip()

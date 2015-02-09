#!/usr/bin/env python

# Thumbtack : Simple Database Challenge
# Author: Michael Shao
# Modified: 02/09/2015 (Time elapsed: 3 hours 25 minutes)
# Version: 1.1.0.1

import sys

class Database(object):
	# Constructor
	def __init__(self, parent = None):
		self.parent = parent
		
		# For committing things (test for "no transaction")
		self.commits = []
		# Key-val pairs (base-level storage)
		self.values = {}
		# In case we unset things
		self.unset = []
		
	# sets variable name <key> to <value>
	# REMEMBER: Keys / Values will not have spaces.
	def set(self, key, value):
		# Assign a new value
		self.values[key] = value
		self.commits.append(key)
		# Remove key from the unset stack, assuming it was unset.
		if key in self.unset:
			self.unset.pop(key)
		
	# retrieves and prints key stored, or "NULL" otherwise	
	def get(self, key):
		if self.get_valfromkey(key) != None:
			print self.get_valfromkey(key)
		else:
			print "NULL"
	
	# unset (dissociate) key from value
	# Runtime: O(1) (normalized)
	def unset(self, key):		
		# First things first: Pop the key off of the values set.
		if key in self.values:
			self.values.pop(key)
		
		if key in self.commits:
			self.commits.remove(key)
		
		# Add the current key to the unset stack
		self.unset.append(key)
		self.commits.append(key)
		
	# Print out the counter of vars that have val. If none exist, print 0.
	def numequalto(self, value):
		print self.values.values().count(value)
				
	# Return the corresponding key to value associated.
	def get_valfromkey(self, key):
		# If we unset it, we can't return it.
		if key in self.unset:
			return None
		# If it exists in our keys, return it.
		if key in self.values.keys():
			return self.values[key]
		elif self.parent:
			return self.parent.get_valfromkey(key);
		# If we don't have that value, return None.
		return None
		
	def commit(self):
		if self.parent == None:
			self.parent = Database()		
		# commit current transactions to super! 
		for key in self.values:
			self.parent.values[key] = self.values[key]
		for key in self.unset:
			self.parent.values.pop(key)
			
	def rollback(self):
		# rollback current transactions from super! 
		for key in self.parent.values:
			self.values[key] = self.parent.values[key]
		for key in self.parent.unset:
			#print key
			self.values.pop(key)
			
	def begin(self):
		if self.parent == None:
			self.parent = Database()
		# begin another set of transactions (set parent!)
		for key in self.values:
			self.parent.values[key] = self.values[key] 

if __name__ == "__main__":
	main_db = Database()
	cur_db = Database(main_db)
	parse_line = sys.stdin.readline().strip()
	while parse_line != 'END':
		# Split arguments by single whitespace character (' ')
		args = parse_line.split(' ')
		# Debugging purposes ( comment out before submitting )
		#print args
		legalCmds = ['SET', 'GET', 'UNSET', 'NUMEQUALTO', 'BEGIN', 'ROLLBACK', 'COMMIT']
		if args[0] in legalCmds:
			if args[0] == 'ROLLBACK':
				#print "ROLLBACK"
				#print "cur_db.commits: ", cur_db.commits
				if cur_db.commits == []:
					print "NO TRANSACTION"
				else:
					cur_db.rollback()
					# Store old record in a temporary variable
					temp_db = cur_db.parent
					#print "temp_db: ", temp_db
					# Kill current DB - we're rolling back!
					del cur_db
					# Now set our current DB to the temp var
					cur_db = temp_db
					#print "cur_db: ", cur_db.values
				#print "cur_db: ", cur_db.values
				#print "main_db: ", main_db.values
				#print "new_db: ", new_db.values 
			elif args[0] == 'BEGIN':
				cur_db.begin()
				# Create new DB with current DB as the "parent" (if it exists)
				new_db = Database(cur_db)
				# Create a pointer to the old DB
				cur_db = main_db
				#print "BEGIN"
				#print "cur_db: ", cur_db.values
				#print "main_db: ", main_db.values
				#print "new_db: ", new_db.values 
			elif args[0] == 'COMMIT':
				#print "COMMIT"
				if cur_db.values == {}:
					print "NO TRANSACTION"
				else:
					# Commit current (we're going to sanitize things)
					cur_db.commit()
					# create a temp
					temp_db = cur_db
					# set parent of current to current
					cur_db = cur_db.parent
					# delete the temp
					del temp_db
					cur_db.commits = []
			#elif args[0] == 'SET':
				#cur_db.set(args[1], args[2])
			#elif args[0] == 'UNSET':
				#cur_db.unset(args[1])
			#elif args[0] == 'GET':
				#cur_db.get(args[1])
			#elif args[0] == 'NUMEQUALTO':
				#cur_db.numequalto(args[1])
			else: 
				fn = getattr(Database, args[0].lower())
				fn(cur_db,*args[1:])
				#print "argument", args[0] , "params: ", args[1:]
				#print "cur_db: ", cur_db.values
				#print "main_db: ", main_db.values
				#print "Database?? ", Database
		else:
			print "Invalid command / arguments: %s" % args
			
		parse_line = sys.stdin.readline().strip()

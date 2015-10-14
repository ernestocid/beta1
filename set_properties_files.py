import shutil
import os

print 'Putting config.properties file in the right folders ...'

# Creating necessary directories 

if not os.path.exists('br.ufrn.forall.betacommon/build/libs/'):
	os.makedirs('br.ufrn.forall.betacommon/build/libs/')

if not os.path.exists('br.ufrn.forall.betacommon/build/classes/'):
	os.makedirs('br.ufrn.forall.betacommon/build/classes/')

if not os.path.exists('br.ufrn.forall.betagui/build/resources/main/'):
	os.makedirs('br.ufrn.forall.betagui/build/resources/main/')

# Copying properties files to the right places

shutil.copyfile('config.properties', 'br.ufrn.forall.betacommon/config.properties')
shutil.copyfile('config.properties', 'br.ufrn.forall.betacommon/build/libs/config.properties')
shutil.copyfile('config.properties', 'br.ufrn.forall.betacommon/build/classes/config.properties')
shutil.copyfile('config.properties', 'br.ufrn.forall.betagui/build/resources/main/config.properties')

print 'Done!'
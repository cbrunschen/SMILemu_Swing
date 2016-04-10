#
# Build file for creating DMG files.
#
# The DMG packager looks for a template.dmg.bz2 for using as its 
# DMG template. If it doesn't find one, it generates a clean one.
#
# If you create a DMG template, you should make one containing all
# the files listed in $(SOURCE_FILES) below, and arrange everything to suit
# your style. The contents of the files themselves does not matter, so
# they can be empty (they will be overwritten later). 
#
# Remko Troncon <remko.troncon@cs.kuleuven.ac.be>
#


################################################################################
# Customizable variables
################################################################################

NAME=SMILemu
VERSION=1.3

DOC=SMILemu-$(VERSION)-doc.zip
DOC_SRCDIR=../resources/com/brunschen/christian/smil/Documentation/
DOC_TRGDIR=`pwd`

SKEL_DMG=skel-$(VERSION).dmg
SKEL_DIR=skel

APP_JAR=$(NAME)-$(VERSION).jar
APP_JAR_SRC=/tmp/$(APP_JAR)
APP_BUNDLE=SMILemu.app

SOURCE_DIR=.
SOURCE_FILES=gpl.txt $(APP_BUNDLE)
TARGET_ALSOS=$(APP_JAR) $(DOC) index.html applet.html
LOCAL_DIR=$(HOME)/Sites/SMILemu
MID_DIR=/Volumes/cb/Sites/SMILemu
REMOTE_DIR=/Volumes/cbrunschen/Sites/SMILemu

TEMPLATE_DMG=template.dmg



################################################################################
# DMG building. No editing should be needed beyond this point.
################################################################################

MASTER_DMG=$(NAME)-$(VERSION).dmg
WC_DMG=wc.dmg
WC_DIR=wc

.PHONY: all
all: $(MASTER_DMG) $(TARGET_ALSOS)

$(TEMPLATE_DMG): $(TEMPLATE_DMG).bz2
	bunzip2 -k $<

$(TEMPLATE_DMG).bz2: 
	@echo
	@echo --------------------- Generating empty template --------------------
	mkdir template
	hdiutil create -size 10m "$(TEMPLATE_DMG)" -srcfolder template -format UDRW -volname "$(NAME)" -quiet
	rmdir template
	bzip2 "$(TEMPLATE_DMG)"
	@echo

$(WC_DMG): $(TEMPLATE_DMG)
	cp $< $@

$(MASTER_DMG): $(WC_DMG) $(addprefix $(SOURCE_DIR)/,$(SOURCE_FILES))
	@echo
	@echo --------------------- Creating Disk Image --------------------
	mkdir -p $(WC_DIR)
	hdiutil attach "$(WC_DMG)" -noautoopen -quiet -mountpoint "$(WC_DIR)"
	for i in $(SOURCE_FILES); do  \
		rm -rf "$(WC_DIR)/$$i"; \
		ditto -rsrc "$(SOURCE_DIR)/$$i" "$(WC_DIR)/$$i"; \
	done
	#rm -f "$@"
	#hdiutil create -srcfolder "$(WC_DIR)" -format UDZO -imagekey zlib-level=9 "$@" -volname "$(NAME) $(VERSION)" -scrub -quiet
	WC_DEV=`hdiutil info | grep "$(WC_DIR)" | grep "Apple_HFS" | awk '{print $$1}'` && \
	hdiutil detach $$WC_DEV -quiet -force
	rm -f "$(MASTER_DMG)"
	hdiutil convert "$(WC_DMG)" -quiet -format UDZO -imagekey zlib-level=9 -o "$@"
	-rm -rf $(WC_DIR)
	-rm -f $(WC_DMG)
	@echo

$(APP_JAR): $(APP_JAR_SRC)
	cp $(APP_JAR_SRC) $(APP_JAR)

$(APP_BUNDLE): $(APP_JAR)
	mkdir -p $(SKEL_DIR)
	hdiutil attach "$(SKEL_DMG)" -noautoopen -quiet -mountpoint "$(SKEL_DIR)"
	ditto -rsrc $(SKEL_DIR)/$(APP_BUNDLE) $(APP_BUNDLE)
	SKEL_DEV=`hdiutil info | grep "$(SKEL_DIR)" | grep "Apple_HFS" | awk '{print $$1}'` && \
	hdiutil detach $$SKEL_DEV -quiet -force
	ditto -rsrc $(APP_JAR) $(APP_BUNDLE)/Contents/MacOS/$(APP_JAR)

.PHONY: $(DOC)
$(DOC):
	(\
		export TRGDIR=$$(pwd);\
		echo TRGDIR=$$TRGDIR;\
		cd $(DOC_SRCDIR);\
		zip -R $$TRGDIR/$(DOC) '*';\
	)

.PHONY: clean
clean:
	-rm -rf $(TEMPLATE_DMG) $(TEMPLATE_DMG).bz2 $(MASTER_DMG) $(WC_DMG) $(DOC) $(APP_BUNDLE) $(SKEL_DIR)

.PHONY: install-local
install-local: all
	-cp $(MASTER_DMG) $(TARGET_ALSOS) $(LOCAL_DIR)
	(\
		cd $(LOCAL_DIR);\
		unzip -o $(DOC);\
	)

.PHONY: install-mid
install-mid: all
	-cp $(MASTER_DMG) $(TARGET_ALSOS) $(MID_DIR)
	(\
		cd $(MID_DIR);\
		unzip -o $(DOC);\
	)

.PHONY: install-remote
install-remote: all
	-cp $(MASTER_DMG) $(TARGET_ALSOS) $(REMOTE_DIR)
	(\
		cd $(REMOTE_DIR);\
		unzip -o $(DOC);\
	)

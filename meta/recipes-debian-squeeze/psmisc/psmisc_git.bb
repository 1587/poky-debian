#
# recipes-extended/psmisc/psmisc_22.13.bb
#

require psmisc.inc
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"
#PR = "r1"

#SRC_URI[md5sum] = "e2c339e6b65b730042084023784a729e"
#SRC_URI[sha256sum] = "06d25e8ebb4722dbcede98a787c39a9ed341f8e58fde10c0b2d6b35990b35daa"

#
# debian-squeeze
# 

inherit debian-squeeze

PR = "r0"

#
# tar_1.26.bb
#

require tar.inc

LICENSE = "GPLv3"
#LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

#PR = "r0"

#SRC_URI[md5sum] = "2cee42a2ff4f1cd4f9298eeeb2264519"
#SRC_URI[sha256sum] = "5a5369f464502a598e938029c310d4b3abd51e6bb8dfd045663e61c8ea9f6d41"

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"

PR = "r0"

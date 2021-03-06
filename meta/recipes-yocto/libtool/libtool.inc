#
# libtool.inc
#

SUMMARY = "Generic library support script"
DESCRIPTION = "This is GNU libtool, a generic library support script. \
Libtool hides the complexity of generating special library types \
(such as shared libraries) behind a consistent interface."
HOMEPAGE = "http://www.gnu.org/software/libtool/libtool.html"
SECTION = "devel"
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
    file://libltdl/COPYING.LIB;md5=e3eda01d9815f8d24aae2dbd89b68b06"

#SRC_URI = "${GNU_MIRROR}/libtool/libtool-${PV}.tar.gz \
#           file://trailingslash.patch \
#           file://prefix-manpage-fix.patch \
#           file://rename-with-sysroot.patch \
#           file://resolve-sysroot.patch \
#           file://use-sysroot-in-libpath.patch \
#           file://fix-final-rpath.patch \
#           file://avoid_absolute_paths_for_general_utils.patch "

do_compile_prepend () {
	# Sometimes this file doesn't get rebuilt, force the issue
	rm -f ${S}/libltdl/config/ltmain.sh
	make libltdl/config/ltmain.sh
}

inherit autotools
EXTRA_AUTORECONF = "--exclude=libtoolize"

#
# debian-squeeze
#

inherit debian-squeeze-yocto

SRC_URI = " \
file://trailingslash.patch \
file://prefix-manpage-fix.patch \
file://rename-with-sysroot.patch;apply=no \
file://resolve-sysroot.patch \
file://use-sysroot-in-libpath.patch \
file://fix-final-rpath.patch \
file://avoid_absolute_paths_for_general_utils.patch \
"

# Git based source directory is required to run "bootstrap"
# before do configure. "rename-with-sysroot.patch" must to
# be patched after "bootstrap" because it modifies "configure"
do_configure_prepend() {
	run_bootstrap
	patch -p1 < ../rename-with-sysroot.patch
}

run_bootstrap() {
	cd ${S}
	echo "Running bootstrap..."
	./bootstrap
}

PKG_SRC_VERSION = "2.4"

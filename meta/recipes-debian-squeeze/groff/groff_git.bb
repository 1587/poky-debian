#
# HIDDEN/recipes-extended/groff/groff_1.20.1.bb
#
SUMMARY = "GNU Troff software"
DESCRIPTION = "The groff (GNU troff) software is a typesetting package which reads plain text mixed with \
formatting commands and produces formatted output."
SECTION = "base"
HOMEPAGE = "ftp://ftp.gnu.org/gnu/groff/"
LICENSE = "GPLv2"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

#SRC_URI = "${GNU_MIRROR}/groff/groff-${PV}.tar.gz"

#SRC_URI[md5sum] = "48fa768dd6fdeb7968041dd5ae8e2b02"
#SRC_URI[sha256sum] = "b645878135cb620c6c417c5601bfe96172245af12045540d7344938b4c2cd805"

DEPENDS = "groff-native"
DEPENDS_virtclass-native = ""

inherit autotools

EXTRA_OECONF = "--without-x"
PARALLEL_MAKE = ""

do_configure_prepend() {
	if [ "${BUILD_SYS}" != "${HOST_SYS}" ]; then
		sed -i \
		    -e '/^GROFFBIN=/s:=.*:=${STAGING_BINDIR_NATIVE}/groff:' \
		    -e '/^TROFFBIN=/s:=.*:=${STAGING_BINDIR_NATIVE}/troff:' \
		    -e '/^GROFF_BIN_PATH=/s:=.*:=${STAGING_BINDIR_NATIVE}:' \
		    -e '/^GROFF_BIN_DIR=/s:=.*:=${STAGING_BINDIR_NATIVE}:' \
		    ${S}/contrib/*/Makefile.sub \
		    ${S}/doc/Makefile.in \
		    ${S}/doc/Makefile.sub
	fi
}

do_install_append_virtclass-native() {
	create_cmdline_wrapper ${D}/${bindir}/groff \
		-F${STAGING_DIR_NATIVE}${datadir_native}/groff/${PV}/font \
		-M${STAGING_DIR_NATIVE}${datadir_native}/groff/${PV}/tmac
}


BBCLASSEXTEND = "native"
#
# debian-squeeze
#
inherit debian-squeeze

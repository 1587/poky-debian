#
# sysstat.inc
#

SUMMARY = "System performance tools"
DESCRIPTION = "The sysstat utilities are a collection of performance monitoring tools for Linux."
HOMEPAGE = "http://sebastien.godard.pagesperso-orange.fr/"
LICENSE = "GPLv2+"
SECTION = "console/utils"

#SRC_URI = "http://pagesperso-orange.fr/sebastien.godard/sysstat-${PV}.tar.gz \
#		   file://99_sysstat"

inherit autotools gettext

EXTRA_OEMAKE += 'LFLAGS=""'

do_install() {
	autotools_do_install
	# some scripts expect sar.sysstat instead of sar (see debian/rules)
	mv ${D}${bindir}/sar ${D}${bindir}/sar.sysstat
	ln -s sar.sysstat ${D}${bindir}/sar

	# NOTE: poky-debian doesn't provide populate-volatile.sh,
	# so don't remove /var/log/sa and don't install volatiles-related files

	# don't install /var/log/sa when populating rootfs. Do it through volatile
	#rm -rf ${D}/var
	#install -d ${D}/etc/default/volatiles
	#install -m 0644 ${WORKDIR}/99_sysstat ${D}/etc/default/volatiles
}

#pkg_postinst_${PN} () {
#        /etc/init.d/populate-volatile.sh update
#}


FILES_${PN} += "${libdir}/sa"
FILES_${PN}-dbg += "${libdir}/sa/.debug/"

TARGET_CC_ARCH += "${LDFLAGS}"
LDFLAGS_append_libc-uclibc = " -lintl"

#
# debian-squeeze
#

inherit debian-squeeze

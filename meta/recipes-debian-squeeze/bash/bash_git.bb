#
# bash_4.1.bb
#

require bash.inc

#PR = "r2"

#SRC_URI = "${GNU_MIRROR}/bash/${BPN}-${PV}.tar.gz;name=tarball \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-001;apply=yes;striplevel=0;name=patch001 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-002;apply=yes;striplevel=0;name=patch002 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-003;apply=yes;striplevel=0;name=patch003 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-004;apply=yes;striplevel=0;name=patch004 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-005;apply=yes;striplevel=0;name=patch005 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-006;apply=yes;striplevel=0;name=patch006 \
#           ${GNU_MIRROR}/bash/bash-4.1-patches/bash41-007;apply=yes;striplevel=0;name=patch007 \
#           "

#SRC_URI[tarball.md5sum] = "9800d8724815fd84994d9be65ab5e7b8"
#SRC_URI[tarball.sha256sum] = "3f627124a83c6d34db503a923e20710d370573a29dd5d11d6f116d1aee7be1da"

#SRC_URI[patch001.md5sum] = "582dea5671b557f783e18629c2f77b68"
#SRC_URI[patch001.sha256sum] = "a6e47fa108f853d0f0999520509c11680d37c8b7823b92b96d431766dd620278"

#SRC_URI[patch002.md5sum] = "118d465095d4a4706eb1d34696a2666a"
#SRC_URI[patch002.sha256sum] = "322e229de186b3bd87dedabfbad8386716f103e87ff00cd1b2db844e0dff78f8"

#SRC_URI[patch003.md5sum] = "120f7cf039a40d35fe375e59d6f17adc"
#SRC_URI[patch003.sha256sum] = "91763dddbbb98c3ff7deb3faea3b3ad6e861e7bfd2e46c045c0d1d85d1b3256d"

#SRC_URI[patch004.md5sum] = "336ee037fc2cc1e2350b05097fbdc87c"
#SRC_URI[patch004.sha256sum] = "78c063ba34c1f390a5bf2e5727624ca2e253bbef49ce187cabb240eee7f4ff9e"

#SRC_URI[patch005.md5sum] = "9471e666797f0b03eb2175ed752a9550"
#SRC_URI[patch005.sha256sum] = "519639d8d1664be74d7ec15879d16337fe8c71af8d648b02f84ccdd4fb739c1a"

#SRC_URI[patch006.md5sum] = "fb80ccd58cb1e34940f3adf4ce6e4a1e"
#SRC_URI[patch006.sha256sum] = "5986abcf33c0b087bd5670f1ae6a6400a8ce6ab7e7c4de18b9826d0ee10f2c49"

#SRC_URI[patch007.md5sum] = "192a8b161d419a1d0d211169f1d1046e"
#SRC_URI[patch007.sha256sum] = "74012a2c28ba4fb532c3eb69155ac870aac8d53990fa4e1e52cdc173d4c205a7"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

S = "${DEBIAN_SQUEEZE_UNPACKDIR}/bash"

# orig.tar.gz is doubly-compressed
do_unpack_srcpkg_append() {
	cd ${DEBIAN_SQUEEZE_UNPACKDIR}
	tar xJvf bash-*.tar.xz
	rm bash-*.tar.xz
	mv bash-* ${S}
}

PRINT_RULES_VARS = \
"${MAKE} -p -n -f ${DEBIAN_SQUEEZE_UNPACKDIR}/debian/rules patch-bash bash_src=bash"

# Apply shell formatted dpatches according to debian/rules.
# debian/rules applies only dpatches defined in "debian_patches" variable,
# and other dpatches in debian/patches are not applied.
do_patch_srcpkg() {
	# confirm PRINT_RULES_VARS command works correctly
	if ! ${PRINT_RULES_VARS} > /dev/null; then
		echo "failed to print database in debian/rules"
		exit 1
	fi
	DPATCHES=$(${PRINT_RULES_VARS} | \
		grep "^debian_patches = " | sed "s@^debian_patches = @@")
	if [ -z "${DPATCHES}" ]; then
		echo "failed to parse patch files from debian/rules"
		exit 1
	fi

	for dpatch in ${DPATCHES}; do
		echo "applying ${dpatch}.dpatch..."
		sh -e ${DEBIAN_SQUEEZE_UNPACKDIR}/debian/patches/${dpatch}.dpatch \
			-patch -d ${S}
	done
}

# Drop bashbug, which is for reporting bugs via email from target system.
# This scripts should not be used in embedded system, and it includes
# some build environment paths (e.g. sysroot), so should be removed.
do_install_append() {
	rm -f ${D}${base_bindir}/bashbug
}

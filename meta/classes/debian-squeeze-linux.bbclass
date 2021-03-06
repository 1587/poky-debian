inherit kernel debian-squeeze-linux-checkout

do_defconfig[dirs] = "${WORKDIR}"
do_defconfig() {
	# use LINUX_CONF as defconfig
	rm -f ${S}/defconfig
	[ -n "${LINUX_CONF}" ] || die "ERROR: LINUX_CONF is null, please fix"
	if echo "${LINUX_CONF}" | grep -q "^/"; then
		# absolute path
		DEFCONFIG="${LINUX_CONF}"
	else
		# relative path from ${S}
		DEFCONFIG="${S}/${LINUX_CONF}"
	fi
	[ -f $DEFCONFIG ] || die "ERROR: $DEFCONFIG not found"
	echo "NOTE: use $DEFCONFIG as ${S}/defconfig"
	cp $DEFCONFIG ${S}/defconfig

	# clean & make defconfig
	rm -rf ${B}
	mkdir -p ${B}
	cd ${B}
	oe_runmake \
		ARCH=${ARCH} CROSS_COMPILE=${TARGET_PREFIX} O=${B} -C ${S} \
		KBUILD_DEFCONFIG=../../../defconfig defconfig || \
		die "ERROR: make defconfig failed"
}
addtask defconfig after do_patch before do_configure

# Required to make 'KERNEL_IMAGETYPE = "vmlinux"' available
do_compile_append() {
	mkdir -p ${B}/arch/${ARCH}/boot
	install -m 0755 ${B}/vmlinux ${B}/arch/${ARCH}/boot
}

DESCRIPTION = "Real-Time preemption testcases"
HOMEPAGE = "https://rt.wiki.kernel.org/index.php/Cyclictest"
SECTION = "misc"
DEPENDS = "linux-libc-headers eglibc"
LICENSE = "GPLv2 & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://src/cyclictest/cyclictest.c;beginline=7;endline=9;md5=ce162fe491d19d2ec67dff6dbc938d50 \
                    file://src/pi_tests/pi_stress.c;beginline=6;endline=19;md5=bd426a634a43ec612e9fbf125dfcc949"
# Version v0.73
# SRCREV = "81da016fb0f6ab0511fbec81fc8ba1a50398a20d"
# PV = "git${SRCPV}"
# PR = "r0"

# SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/clrkwllms/rt-tests.git;protocol=git"

# S = "${WORKDIR}/git"
S=${WORKDIR}

inherit autotools
inherit debian-squeeze

CFLAGS += "-I${S}/src/include -D_GNU_SOURCE -Wall -Wno-nonnull"

do_install() {
       oe_runmake install DESTDIR=${D} SBINDIR=${sbindir} MANDIR=${mandir} \
                          INCLUDEDIR=${includedir}
}

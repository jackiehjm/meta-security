SUMMARY = "interface to seccomp filtering mechanism"
DESCRIPTION = "The libseccomp library provides and easy to use, platform independent,interface to the Linux Kernel's syscall filtering mechanism: seccomp."
SECTION = "security"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=0;endline=1;md5=8eac08d22113880357ceb8e7c37f989f"

SRCREV = "2331d104bc0cbde5f6c54e504a038e52bfe8e12d"

PV = "2.3.2+git${SRCPV}"

SRC_URI = "git://github.com/seccomp/libseccomp.git;branch=release-2.3 \
           file://run-ptest \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig ptest

PACKAGECONFIG ??= ""
PACKAGECONFIG[python] = "--enable-python, --disable-python, python"

do_compile_ptest() {
    oe_runmake -C tests check-build
}

do_install_ptest() {
    install -d ${D}${PTEST_PATH}/tests
    install -d ${D}${PTEST_PATH}/tools
    for file in $(find tests/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${PTEST_PATH}/tests
    done
    for file in $(find tests/*.tests -type f); do
        install -m 744 ${S}/${file} ${D}/${PTEST_PATH}/tests
    done
    for file in $(find tools/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${PTEST_PATH}/tools
    done
}

FILES_${PN} = "${bindir} ${libdir}/${PN}.so*"
FILES_${PN}-dbg += "${libdir}/${PN}/tests/.debug/* ${libdir}/${PN}/tools/.debug"

RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-ptest = "bash"

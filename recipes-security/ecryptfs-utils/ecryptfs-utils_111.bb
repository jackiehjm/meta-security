SUMMARY = "The eCryptfs mount helper and support libraries"
DESCRIPTION = "eCryptfs is a stacked cryptographic filesystem \
    that ships in Linux kernel versions 2.6.19 and above. This \
    package provides the mount helper and supporting libraries \
    to perform key management and mount functions."
HOMEPAGE = "https://launchpad.net/ecryptfs"
SECTION = "base"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "keyutils libgcrypt libpam nss intltool-native glib-2.0-native"

inherit autotools pkgconfig

SRC_URI = "\
    https://launchpad.net/ecryptfs/trunk/${PV}/+download/${BPN}_${PV}.orig.tar.gz \
    file://ecryptfs-utils-CVE-2016-6224.patch \
    "

SRC_URI[md5sum] = "83513228984f671930752c3518cac6fd"
SRC_URI[sha256sum] = "112cb3e37e81a1ecd8e39516725dec0ce55c5f3df6284e0f4cc0f118750a987f"

PARALLEL_MAKEINST=""

EXTRA_OECONF = "\
    --libdir=${base_libdir} \
    --disable-pywrap \
    --disable-nls \
    --enable-openssl=no \
    "

do_configure_prepend() {
    export NSS_CFLAGS="-I${STAGING_INCDIR}/nspr4 -I${STAGING_INCDIR}/nss3"
    export NSS_LIBS="-L${STAGING_BASELIBDIR} -lssl3 -lsmime3 -lnss3 -lsoftokn3 -lnssutil3"
    export KEYUTILS_CFLAGS="-I${STAGING_INCDIR}"
    export KEYUTILS_LIBS="-L${STAGING_LIBDIR} -lkeyutils"
}

do_install_append() {
    chmod 4755 ${D}${base_sbindir}/mount.ecryptfs_private
    mkdir -p ${D}/${libdir}
    mv ${D}/${base_libdir}/pkgconfig ${D}/${libdir}
    sed -i -e 's:-I${STAGING_INCDIR}::' \
           -e 's:-L${STAGING_LIBDIR}::' ${D}/${libdir}/pkgconfig/libecryptfs.pc
    sed -i -e "s: ${base_sbindir}/cryptsetup: ${sbindir}/cryptsetup:" ${D}${bindir}/ecryptfs-setup-swap
}

FILES_${PN} += "${base_libdir}/security/* ${base_libdir}/ecryptfs/*"

RDEPENDS_${PN} += "cryptsetup"
RRECOMMENDS_${PN} = "gettext-runtime"

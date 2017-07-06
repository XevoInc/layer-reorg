require ../../include/rcar-gen2-modules-common.inc

LICENSE = "CLOSED"
DEPENDS = "mmngr-kernel-module"
PN = "mmngr-user-module"
S = "${WORKDIR}/mmngr"
SRC_URI = "file://mmngr.tar.bz2"

do_compile() {
    # Build shared library
    cd ${S}/if
    rm -rf ${S}/if/libmmngr.so*
    make all ARCH=arm
    # Copy shared library into shared folder
    cp -P ${S}/if/libmmngr.so* ${LIBSHARED}
}

do_install() {
    mkdir -p ${D}${RENESAS_DATADIR}/lib/ ${D}${RENESAS_DATADIR}/include

    # Copy shared library
    cp -P ${S}/if/libmmngr.so* ${D}${RENESAS_DATADIR}/lib/
    cd ${D}${RENESAS_DATADIR}/lib/
    # Copy shared header files
    cp -f ${BUILDDIR}/include/mmngr_user_public.h ${D}${RENESAS_DATADIR}/include
    cp -f ${BUILDDIR}/include/mmngr_user_private.h ${D}${RENESAS_DATADIR}/include
}

# Append function to clean extract source
do_cleansstate_prepend() {
        bb.build.exec_func('do_clean_source', d)
}

do_clean_source() {
    rm -f ${LIBSHARED}/libmmngr.so*
    rm -Rf ${BUILDDIR}/include/mmngr_user_public.h
    rm -Rf ${BUILDDIR}/include/mmngr_user_private.h
}

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
"

FILES_${PN} = " \
    ${RENESAS_DATADIR}/lib/libmmngr.so.* \
"

FILES_${PN}-dev = " \
    ${RENESAS_DATADIR}/include \
    ${RENESAS_DATADIR}/include/*.h \
    ${RENESAS_DATADIR}/lib \
    ${RENESAS_DATADIR}/lib/libmmngr.so \
"

RPROVIDES_${PN} += "mmngr-user-module"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP_${PN} += "libdir"
INSANE_SKIP_${PN}-dev += "libdir"

do_configure[noexec] = "1"

python do_package_ipk_prepend () {
    d.setVar('ALLOW_EMPTY', '1')
}

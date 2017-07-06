require ../../include/rcar-gen2-modules-common.inc

LICENSE = "CLOSED"
DEPENDS = "vspm-user-module mmngr-user-module"
SRC_URI = "file://vspm-tp-user.tar.bz2"
S = "${WORKDIR}"

do_compile() {
    cd ${S}/vspm/
    make all ARCH=arm
}

do_install() {
    # Create destination folder
    mkdir -p ${D}${RENESAS_DATADIR}/bin/

    # Copy user test program
    cp ${S}/vspm/vspm_tp ${D}${RENESAS_DATADIR}/bin/
}

PACKAGES = "\
    ${PN} \
"
FILES_${PN} = " \
    ${RENESAS_DATADIR}/bin/vspm_tp \
"

RPROVIDES_${PN} += "vspm-tp-user-module"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

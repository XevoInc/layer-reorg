require ../../include/rcar-gen2-modules-common.inc

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://drv/GPL-COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://drv/MIT-COPYING;md5=fea016ce2bdf2ec10080f69e9381d378"

DEPENDS = "linux-renesas mmngr-kernel-module"
PN = "fdpm-kernel-module"
PR = "r0"
SRC_URI = "file://fdpm-kernel.tar.bz2"
S = "${WORKDIR}/fdpm"

FDPM_CFG_r8a7790 = "H2CONFIG"
FDPM_CFG_r8a7791 = "M2CONFIG"
FDPM_CFG_r8a7793 = "M2CONFIG"
FDPM_CFG_r8a7794 = "E2CONFIG"

KERNEL_HEADER_PATH = "${KERNELSRC}/include/linux"
FDPM_INATALL_HEADERS="fdpm_drv.h fdpm_public.h fdpm_api.h"

do_compile() {
    # Build kernel module
    export FDPM_CONFIG=${FDPM_CFG}
    export FDPM_MMNGRDIR=${KERNELSRC}/include
    cd ${S}/drv
    make all ARCH=arm
}

do_install() {
    # Create destination folder
    mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/extra ${D}/usr/src/kernel/include

    # Copy driver and header files
    cp -f ${S}/drv/fdpm.ko ${D}/lib/modules/${KERNEL_VERSION}/extra
    cp ${S}/drv/Module.symvers ${KERNELSRC}/include/fdpm.symvers

    for f in ${FDPM_INATALL_HEADERS} ; do
        cp -f ${KERNEL_HEADER_PATH}/${f} ${KERNELSRC}/include
    done

    # Copy header files to destination
    for f in ${FDPM_INATALL_HEADERS} ; do
        cp -f ${KERNEL_HEADER_PATH}/${f} ${D}/usr/src/kernel/include
    done
    cp -f ${S}/drv/Module.symvers ${D}/usr/src/kernel/include/fdpm.symvers
}

# Append function to clean extract source
do_cleansstate_prepend() {
        bb.build.exec_func('do_clean_source', d)
}

do_clean_source() {
	if [ -d ${KERNELSRC} ] ; then
		cd ${KERNELSRC}/include/linux/
		rm -f fdpm_drv.h ${FDPM_INATALL_HEADERS}

		cd  ${KERNELSRC}/include/
		rm -f fdpm.symvers ${FDPM_INATALL_HEADERS}
	fi
}

PACKAGES = " \
    ${PN} \
    ${PN}-dev \
"

FILES_${PN} = " \
    /lib/modules/${KERNEL_VERSION}/extra/fdpm.ko \
"

FILES_${PN}-dev = " \
    /usr/src/kernel/include/*.h \
    /usr/src/kernel/include/fdpm.symvers \
"

RPROVIDES_${PN} += "fdpm-kernel-module kernel-module-fdpm"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

ALLOW_EMPTY_kernel-module-fdpm = "1"

do_configure[noexec] = "1"

python do_package_ipk_prepend () {
    d.setVar('ALLOW_EMPTY', '1')
}

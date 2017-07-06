FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# adding most supported CAN devices
SRC_URI_append = " file://can-bus.cfg"
KERNEL_CONFIG_FRAGMENTS_append = " ${WORKDIR}/can-bus.cfg"
 
# adding most supported USB Bluetooth, Wifiand Ehternet devices
SRC_URI_append = " file://usb-devices.cfg"
 
# adding support for other graphic cards to work on more PC HW
KERNEL_CONFIG_FRAGMENTS_append = " ${WORKDIR}/usb-devices.cfg"
SRC_URI_append = " file://extra-graphic-devices.cfg"
KERNEL_CONFIG_FRAGMENTS_append = " ${WORKDIR}/extra-graphic-devices.cfg"

# Configurations for Joule
LINUX_VERSION_INTEL_COMMON_forcevariable = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', '4.4.36', '4.4.26', d)}"
KBRANCH_corei7-64-intel-common_forcevariable = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', 'standard/intel/bxt-rebase;rebaseable=1', 'standard/intel/base', d)}"
SRCREV_machine_corei7-64-intel-common = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', '5ec33015d3f01a8059f16715dd5bb34fac24c50c', '${SRCREV_MACHINE_INTEL_COMMON}', d)}"
SRCREV_meta_corei7-64-intel-common = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', 'b846fc6436aa5d4c747d620e83dfda969854d10c', '${SRCREV_META_INTEL_COMMON}', d)}"

SRC_URI_prepend_intel-corei7-64 = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', 'file://fix_branch.scc ', '', d)}"
KERNEL_FEATURES_remove_corei7-64-intel-common = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', 'features/amt/mei/mei.scc', '', d)}"
KERNEL_FEATURES_append_corei7-64-intel-common = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', ' features/mei/mei-spd.scc', '', d)}"
SRC_URI_append = "${@bb.utils.contains('INTEL_MACHINE_SUBTYPE', 'broxton-m', ' file://security-tpm.cfg', '', d)}"

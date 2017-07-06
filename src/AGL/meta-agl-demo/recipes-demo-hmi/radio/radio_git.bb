SUMMARY     = "Radio for CES2017 AGL Demonstration"
DESCRIPTION = "AGL HMI Application for demonstrating Radio on AGL Distribution"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/radio"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/radio;protocol=http \
           file://presets-ALS.conf \
           file://presets-CES.conf \
           file://presets-FOSDEM.conf \
"
SRCREV  = "${AUTOREV}"

PV = "1.0+git${SRCPV}"
S  = "${WORKDIR}/git"

# build-time dependencies
DEPENDS = "qtquickcontrols2 rtl-sdr glib-2.0 pulseaudio alsa-lib"

inherit qmake5 aglwgt

# ALS, CES, FOSDEM available
AGL_RADIO_PRESETS_LOCALE ?= "CES"

do_install_append() {
    install -d ${D}/home/root/app-data/radio
    install -m 0644 ${WORKDIR}/presets-CES.conf ${D}/home/root/app-data/radio/
    install -m 0644 ${WORKDIR}/presets-ALS.conf ${D}/home/root/app-data/radio/
    install -m 0644 ${WORKDIR}/presets-FOSDEM.conf ${D}/home/root/app-data/radio/
    install -m 0644 ${WORKDIR}/presets-${AGL_RADIO_PRESETS_LOCALE}.conf ${D}/home/root/app-data/radio/presets.conf
}

FILES_${PN} += " \
	/home/root/app-data/radio/presets-*.conf \
	/home/root/app-data/radio/presets.conf \
"

SUMMARY = "The software for DEMO platform of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-demo-platform \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    packagegroup-agl-image-ivi \
    "

RDEPENDS_${PN} += "\
    packagegroup-agl-demo \
    "

MOST_DRIVERS = " "
MOST_DRIVERS_append = " \
    mocca-usb \
    most \
    "

# HVAC dependencies depend on drivers above
MOST_HVAC = " "
MOST_HVAC_append = " \
    ${MOST_DRIVERS} \
    unicens \
    vod-server \
    "

# can-lin is a binary and only for porter :(
MOST_HVAC_append_porter = " \
    can-lin \
    "

# mapviewer and mapviewer-demo requires AGL CES2017 demo mock-up
MAPVIEWER = " "
MAPVIEWER_append_porter = " \
    mapviewer \
    mapviewer-demo \
    "

AGL_APPS = " \
    dashboard \
    hvac \
    mediaplayer \
    mixer \
    navigation \
    phone \
    poiapp \
    radio \
    settings \
    "

RDEPENDS_${PN}_append = " \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
    linux-firmware-ralink \
    ${MAPVIEWER} \
    ${MOST_HVAC} \
    ${AGL_APPS} \
    "



inherit allarch useradd

SUMMARY = "AGL Users Seed"
DESCRIPTION = "This is a core framework component that\
 defines how users are managed and who are the default users."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM_${PN} = " --system display ; --system weston-launch"

USERADD_PARAM_${PN} = "\
  -g users -G display -d /home/agl-driver -m -K PASS_MAX_DAYS=-1 agl-driver ; \
  -g users -G display -d /home/agl-passenger -m -K PASS_MAX_DAYS=-1 agl-passenger ; \
  --gid display --groups weston-launch,video,input --home-dir /run/platform/display --shell /bin/false --comment \"Display daemon\" --key PASS_MAX_DAYS=-1 display \
"

DESCRIPTION = "Security packagegroup for TPM i2c support"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES = "packagegroup-security-tpm-i2c"

SUMMARY_packagegroup-security-tpm-i2c = "Security TPM i2c support"
RDEPENDS_packagegroup-security-tpm-i2c = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm', 'packagegroup-security-tpm', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm2', 'packagegroup-security-tpm2, '', d)} \
    kernel-module-tpm-i2c-atmel \
    kernel-module-tpm-i2c-infineon \
    kernel-module-tpm-i2c-nuvoton \
    kernel-module-tpm-st33zp24 \
    kernel-module-tpm-st33zp24-i2c \
    "

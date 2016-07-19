LOCAL_PATH := $(call my-dir)

BUILD_IN_NDK := yes

ifeq ($(strip $(BUILD_IN_NDK)),yes)

include $(CLEAR_VARS)
LOCAL_MODULE := libutils
LOCAL_SRC_FILES := lib/libutils.so
LOCAL_EXPORT_C_INCLUDES := include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libcutils
LOCAL_SRC_FILES := lib/libcutils.so
LOCAL_EXPORT_C_INCLUDES := include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libbinder
LOCAL_SRC_FILES := lib/libbinder.so
LOCAL_EXPORT_C_INCLUDES := include
include $(PREBUILT_SHARED_LIBRARY)

endif